package fitlibrary.utility;

import fit.Fixture;

public class HtmlUtils {
	public static String tag(String tag, String content) {
		return tag(tag, content, "");
	}

	public static String tag(String tag, String content, String attributes) {
		return "<" + tag + " " + attributes + ">" + content + "</" + tag + ">";
	}

	public static String pre(String text) {
		return tag("pre", escape(text));
	}

	public static String div(String text, String cssClass) {
		return tag("div", text, "class=" + cssClass);
	}

	public static String escape(String text) {
		return Fixture.escape(text).replaceAll("\r?\n", "<br />");
	}

	public static String escapeHtml(String s) {
		if (s == null)
			return "";
		return s.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	public static String buildExpectedNotActualText(String actual, String expected) {
		return HtmlUtils.pre(expected) + Fixture.label("expected") + divider() + HtmlUtils.pre(actual)
				+ Fixture.label("actual");
	}

	public static String divider() {
		return "<hr/>";
	}

	public static String exception(String eOriginal) {
		String e = eOriginal.trim();
		if (!e.startsWith("at "))
			e = "[wrapped] " + e;
		return div(escape(e).replaceAll("\\[wrapped\\] ([^<]+)<br />", tag("b", "### $1 ###") + "<br />"),
				"fit_stacktrace");
	}
}