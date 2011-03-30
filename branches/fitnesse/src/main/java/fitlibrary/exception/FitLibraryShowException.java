package fitlibrary.exception;

public class FitLibraryShowException extends FitLibraryException {
	private static final long serialVersionUID = 1L;
	private Show show;

	public FitLibraryShowException(Show show) {
		super("show");
		this.show = show;
	}

	public Show getResult() {
		return show;
	}

	public static FitLibraryShowException show(String htmlString) {
		return new FitLibraryShowException(new Show(htmlString));
	}

	public static class Show {
		protected String htmlString;

		public Show(String htmlString) {
			this.htmlString = htmlString;
		}

		public String getHtmlString() {
			return htmlString;
		}

		@Override
		public String toString() {
			return htmlString;
		}
	}
}
