package fitlibrary.domainAdapter;

import fitlibrary.annotation.ShowSelectedActions;
import fitlibrary.annotation.SimpleAction;
import fitlibrary.traverse.DomainAdapter;

@ShowSelectedActions
public class StringAdapter implements DomainAdapter {
	private final String subject;

	public StringAdapter(String subject) {
		this.subject = subject;
	}

	public String[] split(String separator) {
		if (separator == null || "".equals(separator))
			return subject.split(" ");
		if (separator.equals("\\n"))
			return subject.split("\n");
		return subject.split(separator);
	}

	@Override
	public String toString() {
		return subject.toString();
	}

	// @Override
	public Object getSystemUnderTest() {
		return subject;
	}

	@SimpleAction(wiki = "|''<i>index of</i>''|text|", tooltip = "What is the offset of the text in the string?")
	public int indexOf(String pattern) { // As the underlying method is
											// overloaded
		return subject.indexOf(pattern);
	}

	@SimpleAction(wiki = "|''<i>index of</i>''|text|''<i>from</i>''|start|", tooltip = "What is the offset of the text in the string, starting at start?")
	public int indexOf(String pattern, int from) { // As the underlying method
													// is overloaded
		return subject.indexOf(pattern, from);
	}

	@SimpleAction(wiki = "|''<i>last index of</i>''|text|", tooltip = "What is the last offset of the text in the string?")
	public int lastIndexOf(String pattern) { // As the underlying method is
												// overloaded
		return subject.lastIndexOf(pattern);
	}

	@SimpleAction(wiki = "|''<i>compare to</i>''|text|", tooltip = "What is the result of comparing the text to the string?")
	public int compareTo(String pattern) { // As the underlying method is
											// overloaded
		return subject.compareTo(pattern);
	}

	@SimpleAction(wiki = "|''<i>is equals</i>''|text|", tooltip = "Returns whether the text is the same as the string.")
	public boolean isEquals(String pattern) { // As the underlying method takes
												// an Object
		return subject.equals(pattern);
	}

	@SimpleAction(wiki = "|''<i>contains</i>''|text|", tooltip = "Returns whether the string contains the text.")
	public boolean contains(String pattern) { // As the underlying method takes
												// a CharSequence
		return subject.contains(pattern);
	}

	@SimpleAction(wiki = "|''<i>replace</i>''|pattern|replacement|", tooltip = "Returns the result of replacing the pattern by the replacement in the string.")
	public String replace(String pattern, String replacement) { // As the
																// underlying
																// method takes
																// a
																// CharSequence
		return subject.replace(pattern, replacement);
	}
}
