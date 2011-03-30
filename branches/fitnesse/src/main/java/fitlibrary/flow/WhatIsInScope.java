package fitlibrary.flow;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import fit.Fixture;
import fitlibrary.annotation.ActionType;
import fitlibrary.annotation.AnAction;
import fitlibrary.annotation.CompoundAction;
import fitlibrary.annotation.NullaryAction;
import fitlibrary.annotation.ShowSelectedActions;
import fitlibrary.annotation.SimpleAction;
import fitlibrary.traverse.workflow.DoTraverse;
import fitlibrary.traverse.workflow.caller.TwoStageSpecial;
import fitlibrary.typed.TypedObject;

public class WhatIsInScope {
	public static String what(IScope scope, String initialPattern) {
		String pattern = initialPattern.replace("<", "&lt;");
		StringBuilder s = new StringBuilder();
		s.append("These are all the actions that are available at this point in the storytest.</br></br>");
		s.append("Special actions are shown in <b>bold</b> (with '''...'''). Other actions are shown in <i>italics</i> (with ''...'').</br></br>");
		s.append("Special actions act on a (non-special) action, as shown by \"|action...|\"</br></br>");
		s.append("Move the mouse over an action (or header) for further information, when it's available.</br></br>");
		s.append("<table>");
		s.append("<tr><td><h3 style='margin-top:5px; margin-bottom:5px'>Action</h3></td><td><h3 style='margin-top:5px; margin-bottom:5px'>");
		span(s, "Returns", 0, "The Java type of the returned object.");
		s.append("</h3></td></tr>\n");
		for (TypedObject object : scope.objectsForLookup()) {
			Class<? extends Object> aClass = object.classType();
			ShowSelectedActions showAnnotation = aClass.getAnnotation(ShowSelectedActions.class);
			s.append("<tr><td colspan=2 style='background-color:#d0d0d0;'><h4 style='margin-top:3px; margin-bottom:3px;'>Actions for class "
					+ aClass.getSimpleName() + ":</h4></td></tr>\n");
			addActions(s, aClass, pattern, showAnnotation != null, 3);
		}
		s.append("</table>");
		return s.toString();
	}

	private static void addActions(StringBuilder s, Class<? extends Object> aClass, String pattern, boolean selective,
			int margin) {
		boolean matchAll = (pattern == null || pattern.equals(""));
		Method[] methods = aClass.getMethods();
		Arrays.sort(methods, new Comparator<Method>() {
			public int compare(Method m1, Method m2) {
				return m1.getName().compareToIgnoreCase(m2.getName());
			}
		});
		for (Method method : methods) {
			if (!ignoreMethod(aClass, method)) {
				boolean locallySelective = selective
						|| method.getDeclaringClass().getAnnotation(ShowSelectedActions.class) != null;
				ActionInfo actionInfo = decodeAnnotation(method, locallySelective);
				if (!actionInfo.ignore && (matchAll || actionInfo.matches(pattern))) {
					s.append("<tr><td>");
					spanStart(s, margin); // <span
											// style='margin-left:"+margin+"px'>");
					actionInfo.display(s);
					s.append("</span></td><td><code>");
					s.append(returnTypeDisplay(method));
					s.append("</code></td></tr>");
					Class<?> returnType = method.getReturnType();
					if (actionInfo.compound && !ignoreType(returnType)) {
						s.append("<tr><td colspan=2>");
						String simpleName = returnType.getSimpleName();
						span(s,
								"Actions for class <code>" + simpleName + "</code> in same table:",
								margin + 50,
								"These actions are for the "
										+ simpleName
										+ " produced by the action above.\n"
										+ "They can be used in the following rows of the table that contains the above action.");
						s.append("</td></tr>");
						addActions(s, returnType, "", true, margin + 50);
					}
				}
			}
		}
	}

	private static void span(StringBuilder s, String contents, int margin, String title) {
		s.append("<span title='" + title + "' style='margin-left:" + margin + "px'>" + contents + "</span>");
	}

	private static void spanStart(StringBuilder s, int margin) {
		s.append("<span style='margin-left:" + margin + "px'>");
	}

	private static boolean ignoreMethod(Class<? extends Object> aClass, Method method) {
		Class<?> declaringClass = method.getDeclaringClass();
		return declaringClass == Object.class || declaringClass == Fixture.class
				|| (declaringClass == DoTraverse.class && aClass != DoTraverse.class)
				|| method.getName().equals("getSystemUnderTest");
	}

	static class ActionInfo {
		public final String name;
		public final String tooltip;
		public final boolean ignore;
		public final boolean compound;

		public ActionInfo(String name, String tooltip, boolean compound, boolean hasParameters) {
			this.name = name;
			if (tooltip == null || "".equals(tooltip))
				if (hasParameters)
					this.tooltip = "Action in sequence form, where the method name is followed by the types of each of the arguments.\n\n"
							+ "This has been determined automatically from the underlying method.\n\n"
							+ "If you want better documentation, which shows how to mix keywords and arguments, ask the developer who wrote the fixturing code to provide it. "
							+ "(See .FitLibrary.FitLibrary.SpecifiCations.GlobalActionsProvided.WhatIsInScope for how to do this.)";
				else
					this.tooltip = "Action name has been determined automatically from the name of the underlying method.";
			else
				this.tooltip = tooltip.replace("\"", "'");
			this.compound = compound;
			this.ignore = false;
		}

		public ActionInfo() {
			this.name = "";
			this.tooltip = "";
			this.compound = false;
			this.ignore = true;
		}

		public boolean matches(String pattern) {
			return tooltip.contains(pattern) || nameWithoutTags().contains(pattern);
		}

		public String nameWithoutTags() {
			return name.replaceAll("<i>", "").replaceAll("</i>", "").replaceAll("<b>", "").replaceAll("</b>", "");
		}

		public void display(StringBuilder s) {
			s.append("<span style='background-color: #ffffcc' title=\"" + tooltip + "\">" + name + "</span>");
		}

		public static ActionInfo ignore() {
			return new ActionInfo();
		}
	}

	private static String returnTypeDisplay(Method method) {
		Class<?> returnType = method.getReturnType();
		if (returnType == Void.TYPE || returnType == TwoStageSpecial.class)
			return "";
		return returnType.getSimpleName();
	}

	private static ActionInfo decodeAnnotation(Method method, boolean selective) {
		boolean hasParameters = method.getParameterTypes().length > 0;
		NullaryAction nullaryAction = method.getAnnotation(NullaryAction.class);
		if (nullaryAction != null)
			return new ActionInfo(unCamel(method.getName(), ActionType.SIMPLE), nullaryAction.tooltip(), false,
					hasParameters);
		SimpleAction simpleAction = method.getAnnotation(SimpleAction.class);
		if (simpleAction != null)
			return new ActionInfo(simpleAction.wiki(), simpleAction.tooltip(), false, hasParameters);
		CompoundAction compoundAction = method.getAnnotation(CompoundAction.class);
		if (compoundAction != null) {
			String name = compoundAction.wiki();
			if (name == null || "".equals(name))
				name = unCamel(method.getName(), ActionType.SIMPLE);
			return new ActionInfo(name, compoundAction.tooltip(), true, hasParameters);
		}
		AnAction anAction = method.getAnnotation(AnAction.class);
		if (anAction != null) {
			if (anAction.actionType() == ActionType.IGNORE)
				return ActionInfo.ignore();
			String name = anAction.wiki();
			if (name == null || "".equals(name))
				name = unCamel(method.getName(), anAction.actionType());
			if (anAction.actionType() == ActionType.PREFIX)
				name += "action...|";
			return new ActionInfo(name, anAction.tooltip(), anAction.isCompound(), hasParameters);
		}
		if (selective)
			return ActionInfo.ignore();
		return new ActionInfo(methodName(method), "", false, hasParameters);
	}

	private static boolean ignoreType(Class<?> type) {
		return type.isPrimitive() || type.isEnum() || type.isArray() || type == String.class || type == Date.class
				|| Number.class.isAssignableFrom(type) || type == Character.class;
	}

	private static String methodName(Method method) {
		return unCamel(method.getName(), ActionType.SIMPLE) + parameters(method.getParameterTypes());
	}

	private static String parameters(Class<?>[] parameterTypes) {
		String s = "";
		for (Class<?> aClass : parameterTypes) {
			s += " ";
			s += aClass.getSimpleName();
			s += " |";
		}
		return s;
	}

	private static String unCamel(String name, ActionType actionType) {
		String quotes = "''";
		if (actionType == ActionType.PREFIX || actionType == ActionType.SUFFIX)
			quotes = "'''";
		String format = "i";
		if (actionType == ActionType.PREFIX || actionType == ActionType.SUFFIX)
			format = "b";
		StringBuilder s = new StringBuilder();
		s.append("|" + quotes + "<" + format + ">");
		for (char ch : name.toCharArray())
			if (Character.isUpperCase(ch))
				s.append(" " + Character.toLowerCase(ch));
			else
				s.append("" + ch);
		s.append(quotes + "</" + format + ">|");
		return s.toString();
	}

}
