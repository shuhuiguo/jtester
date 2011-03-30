/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.runner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import fit.Counts;
import fit.Parse;
import fitlibrary.suite.BatchFitLibrary;
import fitlibrary.table.TableFactory;
import fitlibrary.table.Tables;

/**
 * Run Fit tests from a spreadsheet.
 */
public class SpreadsheetRunner extends AbstractRunner {
	private boolean tableStarted = false;
	private Report report = null;

	public SpreadsheetRunner(Report report) {
		this.report = report;
	}

	public SpreadsheetRunner() {
		//
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		new SpreadsheetRunner().run(new File("test.xls"), new File("report.html"), new BatchFitLibrary());
	}

	public Counts run(File inFile, File reportFile, BatchFitLibrary fitLibraryServer) throws FileNotFoundException,
			IOException {
		return run(inFile, reportFile, new Parse("table", "", null, null), null, fitLibraryServer);
	}

	public Counts run(File inFile, File reportFile, Parse setUpTables, Parse tearDownTables,
			BatchFitLibrary batchFitLibrary) throws FileNotFoundException, IOException {
		PrintWriter output = new PrintWriter(new OutputStreamWriter(new FileOutputStream(reportFile)));
		try {
			CustomRunner customRunner = collect(inFile, reportFile, setUpTables, tearDownTables);
			if (report != null) {
				Parse parse = customRunner.getTables();
				parse.leader = report.addLinks(parse.leader, inFile);
			}
			Tables tables = TableFactory.tables(customRunner.getTables());
			Counts counts = batchFitLibrary.doStorytest(tables).getCounts();
			outputHtml(output, tables);
			return counts;
		} catch (Exception e) {
			stackTrace(output, e);
			return new Counts(0, 0, 0, 0);
		} finally {
			output.close();
		}
	}

	public Parse collectTable(File setUpFile) throws FileNotFoundException, IOException, CustomRunnerException {
		CustomRunner runner = collect(setUpFile, null, null, null);
		return runner.getTables();
	}

	private CustomRunner collect(File inFile, File reportFile, Parse setUpTables, Parse tearDownTables)
			throws IOException, FileNotFoundException {
		CustomRunner runner = new CustomRunner(inFile.getName(), inFile, reportFile);
		if (setUpTables != null)
			runner.addTables(setUpTables);
		FileInputStream fileInputStream = new FileInputStream(inFile);
		HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
		String preText = collectTables(runner, workbook);
		fileInputStream.close();
		if (tearDownTables != null) {
			tearDownTables.leader += preText;
			runner.addTables(tearDownTables);
		} else if (!preText.equals(""))
			runner.addTableTrailer(preText);
		return runner;
	}

	@SuppressWarnings("unchecked")
	private String collectTables(CustomRunner runner, HSSFWorkbook workbook) {
		HSSFSheet sheet = workbook.getSheetAt(0);
		String preText = "";
		for (Iterator<HSSFRow> it = sheet.rowIterator(); it.hasNext();) {
			HSSFRow row = it.next();
			HSSFCell[] cells = getCells(row);
			String[] borderedCellValues = getBorderedCellValues(cells, workbook);
			if (borderedCellValues.length > 0) {
				addRow(runner, borderedCellValues, preText);
				preText = "";
			} else {
				String text = allText(cells, workbook) + "\n";
				if (preText.equals("") && !text.equals(""))
					preText = text;
				else
					preText += "<BR>" + text;
			}
		}
		return preText;
	}

	// private String collectTablesThatFails(CustomRunner runner, HSSFWorkbook
	// workbook) {
	// // I tried this approach to pick up the empty rows, but it doesn't help
	// // What's more, it doesn't even work.
	// HSSFSheet sheet = workbook.getSheetAt(0);
	// System.out.println("rows = "+sheet.getFirstRowNum()+" to "+sheet.getLastRowNum());
	// String preText = "";
	// for (int r = sheet.getFirstRowNum(); r < sheet.getLastRowNum(); r++) {
	// HSSFRow row = sheet.getRow(r);
	// if (row == null)
	// preText += "<BR>";
	// else {
	// HSSFCell[] cells = getCells(row);
	// String[] borderedCellValues = getBorderedCellValues(cells,workbook);
	// if (borderedCellValues.length > 0) {
	// System.out.println("Adding row");
	// addRow(runner,borderedCellValues,preText);
	// preText = "";
	// } else {
	// String text = allText(cells,workbook)+"\n";
	// if (preText.equals("") && !text.equals(""))
	// preText = text;
	// else
	// preText += "<BR>"+text;
	// }
	// }
	// }
	// return preText;
	// }
	public void addRow(CustomRunner runner, String[] cells, String preText) {
		if (tableStarted && preText.equals(""))
			runner.addRow(cells);
		else {
			runner.addTable(cells, preText);
			tableStarted = true;
		}
	}

	private String allText(HSSFCell[] cells, HSSFWorkbook workbook) {
		String result = format(cells[0], workbook);
		for (int i = 1; i < cells.length; i++) {
			String value = format(cells[i], workbook);
			if (!value.equals(""))
				result += " " + value;
		}
		return result;
	}

	private String[] getBorderedCellValues(HSSFCell[] cells, HSSFWorkbook workbook) {
		List<String> list = new ArrayList<String>();
		int cellNo = 0;
		while (cellNo < cells.length) {
			HSSFCell cell = cells[cellNo++];
			if (leftBordered(cell)) {
				list.add(format(cell, workbook));
				break;
			}
		}
		while (cellNo < cells.length) {
			HSSFCell cell = cells[cellNo++];
			if (leftBordered(cell))
				list.add(format(cell, workbook));
		}
		String[] result = new String[list.size()];
		int i = 0;
		for (String it : list)
			result[i++] = it;
		return result;
	}

	private boolean leftBordered(HSSFCell cell) {
		if (cell == null)
			return false;
		return cell.getCellStyle().getBorderLeft() > 0;
	}

	@SuppressWarnings("unchecked")
	private HSSFCell[] getCells(HSSFRow row) {
		int maxCell = row.getLastCellNum();
		HSSFCell[] cells = new HSSFCell[maxCell];
		for (int i = 0; i < cells.length; i++)
			cells[i] = null;
		for (Iterator<HSSFCell> r = row.cellIterator(); r.hasNext();) {
			HSSFCell cell = r.next();
			short cellNo = cell.getCellNum();
			cells[cellNo] = cell;
		}
		return cells;
	}

	// private String[] getStringsOfCells(HSSFRow row) {
	// int maxCell = row.getLastCellNum();
	// HSSFCell[] cells = new HSSFCell[maxCell];
	// String[] stringInCells = new String[maxCell];
	// for (int i = 0; i < stringInCells.length; i++)
	// stringInCells[i] = "";
	// for (Iterator r = row.cellIterator(); r.hasNext(); ) {
	// HSSFCell cell = (HSSFCell)r.next();
	// HSSFCellStyle style = cell.getCellStyle();
	// int borders = style.getBorderLeft() + style.getBorderRight() +
	// style.getBorderTop() + style.getBorderBottom();
	// short background = style.getFillBackgroundColor();
	// short foreground = style.getFillForegroundColor();
	// short fillPattern = style.getFillPattern();
	// if (foreground + background + fillPattern > 0)
	// System.out.println("Spreadsheet colours: "+
	// background+","+foreground+","+fillPattern);
	// if (borders > 0) {
	// short cellNo = cell.getCellNum();
	// stringInCells[cellNo] = format(cell,null);
	// }
	// }
	// return stringInCells;
	// }
	private String format(HSSFCell cell, HSSFWorkbook workbook) {
		if (cell == null)
			return "";
		String value = value(cell);
		HSSFCellStyle style = cell.getCellStyle();
		HSSFFont font = workbook.getFontAt(style.getFontIndex());
		// System.err.println("Formatting "+value(cell)+"= "+font.getFontHeight());
		if (font.getItalic())
			value = tag("i", value);
		if (font.getBoldweight() > 400)
			value = tag("b", value);
		if (font.getUnderline() > 0)
			value = tag("u", value);
		if (font.getFontHeight() >= 480)
			value = tag("h1", value);
		else if (font.getFontHeight() >= 280)
			value = tag("h2", value);
		else if (font.getFontHeight() > 200)
			value = tag("h3", value);
		return value;
	}

	private String tag(String tag, String string) {
		return "<" + tag + ">" + string + "</" + tag + ">";
	}

	@SuppressWarnings("deprecation")
	private String value(HSSFCell cell) {
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_BLANK:
			return "";
		case HSSFCell.CELL_TYPE_BOOLEAN:
			return "" + cell.getBooleanCellValue();
		case HSSFCell.CELL_TYPE_ERROR:
			return "ERROR";
		case HSSFCell.CELL_TYPE_FORMULA:
			if (Double.isNaN(cell.getNumericCellValue())) {
				try {
					return "" + cell.getBooleanCellValue();
				} catch (NumberFormatException ex) {
					return cell.getStringCellValue();
				}
			}
			return number(cell);
		case HSSFCell.CELL_TYPE_NUMERIC:
			return number(cell);
		case HSSFCell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		}
		return "UNKNOWN";
	}

	private String number(HSSFCell cell) {
		double value = cell.getNumericCellValue();
		if (((int) value) == value)
			return "" + ((int) value);
		return "" + value;
	}
}
