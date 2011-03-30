package fitlibrary.domainAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import fitlibrary.annotation.NullaryAction;
import fitlibrary.annotation.ShowSelectedActions;
import fitlibrary.annotation.SimpleAction;
import fitlibrary.traverse.DomainAdapter;

@ShowSelectedActions
public class AbstractFileHandler implements DomainAdapter {
	protected File file = new File(".");

	@SimpleAction(wiki = "|''<i>append</i>''|contents|", tooltip = "Append the contents to the end of the file.")
	public void append(String content) throws IOException {
		writeToFile(content, true);
	}

	@SimpleAction(wiki = "|''<i>write</i>''|contents|", tooltip = "Write the contents to the file.")
	public void write(String content) throws IOException {
		writeToFile(content, false);
	}

	@SimpleAction(wiki = "|''<i>append unicode</i>''|contents|", tooltip = "Append the contents as unicode to the end of the file.")
	public void appendUnicode(String content) throws IOException {
		writeUnicodeToFile(content, true);
	}

	@SimpleAction(wiki = "|''<i>write unicode</i>''|contents|", tooltip = "Write the contents as unicode to the file.")
	public void writeUnicode(String content) throws IOException {
		writeUnicodeToFile(content, false);
	}

	@NullaryAction(tooltip = "Read the contents of the file, so we can use it or check (parts of) it.")
	public String read() throws IOException {
		return readFile();
	}

	public String readFile() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String result = "";
		while (true) {
			String line = reader.readLine();
			if (line == null)
				break;
			result += line + "\n";
		}
		reader.close();
		return result;
	}

	@SimpleAction(wiki = "|''<i>make folders</i>''|folder name|", tooltip = "Create this as a folder, along with any other folders that are needed to hold it.")
	public void makeFolders(String folderPath) { // Don't need this action, but
													// leave it for backwards
													// compatibility
		new File(folderPath).mkdirs();
	}

	@NullaryAction(tooltip = "Delete the file, returning true if it succeeded.")
	public boolean delete() {
		return file.delete();
	}

	@Override
	public String toString() {
		return "FileHandler[" + file.getName() + "]";
	}

	private void writeToFile(String content, boolean append) throws IOException {
		FileWriter fileWriter = new FileWriter(file, append);
		fileWriter.write(content.replace("\\n", "\n"));
		fileWriter.close();
	}

	private void writeUnicodeToFile(String content, boolean append) throws IOException {
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file, append), "UTF8");
		out.write(content.replace("\\n", "\n"));
		out.close();
	}

	// @Override
	public Object getSystemUnderTest() {
		return file;
	}
}