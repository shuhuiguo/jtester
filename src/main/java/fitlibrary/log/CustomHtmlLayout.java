/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.log;

import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.helpers.Transform;
import org.apache.log4j.spi.LoggingEvent;

public class CustomHtmlLayout extends Layout {
	public static int MAX_LOGGED_SIZE = 400; // Must be > 30
	protected final int BUF_SIZE = 256;
	protected final int MAX_CAPACITY = 1024;
	static String TRACE_PREFIX = "<br>&nbsp;&nbsp;&nbsp;&nbsp;";
	private StringBuffer sbuf = new StringBuffer(BUF_SIZE);

	@Override
	public String getContentType() {
		return "text/html";
	}

	// @Override
	public void activateOptions() {
		// None to activate
	}

	@Override
	public String format(LoggingEvent event) {
		if (sbuf.capacity() > MAX_CAPACITY) {
			sbuf = new StringBuffer(BUF_SIZE);
		} else {
			sbuf.setLength(0);
		}

		sbuf.append(Layout.LINE_SEP + "<tr>" + Layout.LINE_SEP);

		sbuf.append("<td>");
		sbuf.append(event.timeStamp - LoggingEvent.getStartTime());
		sbuf.append("</td>" + Layout.LINE_SEP);

		String escapedThread = Transform.escapeTags(event.getThreadName());
		sbuf.append("<td style=\"font-size : xx-small;\" title=\"" + escapedThread + " thread\">");
		if (!escapedThread.equals("main"))
			sbuf.append(escapedThread);
		sbuf.append("</td>" + Layout.LINE_SEP);

		sbuf.append("<td style=\"font-size : x-small;\" title=\"Level\">");
		if (event.getLevel().equals(Level.DEBUG)) {
			sbuf.append("<font color=\"#339933\">");
			sbuf.append(Transform.escapeTags(String.valueOf(event.getLevel())));
			sbuf.append("</font>");
		} else if (event.getLevel().isGreaterOrEqual(Level.WARN)) {
			sbuf.append("<font color=\"#993300\"><strong>");
			sbuf.append(Transform.escapeTags(String.valueOf(event.getLevel())));
			sbuf.append("</strong></font>");
		} else {
			sbuf.append(Transform.escapeTags(String.valueOf(event.getLevel())));
		}
		sbuf.append("</td>" + Layout.LINE_SEP);

		String escapedLogger = Transform.escapeTags(event.getLoggerName());
		sbuf.append("<td title=\"" + escapedLogger + " category\">");
		sbuf.append(escapedLogger);
		sbuf.append("</td>" + Layout.LINE_SEP);

		sbuf.append("<td style=\"font-size : xx-small;\" colspan=\"6\" title=\"Nested Diagnostic Context\">");
		if (event.getNDC() != null) {
			sbuf.append(Transform.escapeTags(event.getNDC()));
		}
		sbuf.append("</td>" + Layout.LINE_SEP);

		sbuf.append("<td title=\"Message\">");
		sbuf.append(limitSize(Transform.escapeTags(event.getRenderedMessage())));
		sbuf.append("</td>" + Layout.LINE_SEP);

		sbuf.append("</tr>" + Layout.LINE_SEP);

		String[] s = event.getThrowableStrRep();
		if (s != null) {
			sbuf.append("<tr><td bgcolor=\"#993300\" style=\"color:White; font-size : xx-small;\" colspan=\"6\">");
			appendThrowableAsHTML(s, sbuf);
			sbuf.append("</td></tr>" + Layout.LINE_SEP);
		}

		return sbuf.toString();
	}

	private static String limitSize(String s) {
		int size = s.length();
		if (size <= MAX_LOGGED_SIZE)
			return s;
		return s.substring(0, MAX_LOGGED_SIZE - 30) + "..." + s.substring(size - 27);
	}

	void appendThrowableAsHTML(String[] s, StringBuffer sbuffer) {
		if (s != null) {
			int len = s.length;
			if (len == 0)
				return;
			sbuffer.append(Transform.escapeTags(s[0]));
			sbuffer.append(Layout.LINE_SEP);
			for (int i = 1; i < len; i++) {
				sbuffer.append(TRACE_PREFIX);
				sbuffer.append(Transform.escapeTags(s[i]));
				sbuffer.append(Layout.LINE_SEP);
			}
		}
	}

	@Override
	public String getHeader() {
		return "";
	}

	@Override
	public String getFooter() {
		return "";
	}

	@Override
	public boolean ignoresThrowable() {
		return false;
	}
}
