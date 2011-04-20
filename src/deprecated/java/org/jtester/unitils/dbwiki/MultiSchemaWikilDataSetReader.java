package org.jtester.unitils.dbwiki;

import java.io.File;
import java.io.StringReader;

import org.unitils.core.UnitilsException;
import org.unitils.dbunit.util.MultiSchemaDataSet;
import org.unitils.dbunit.util.MultiSchemaXmlDataSetReader;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.apache.log4j.Logger;

public class MultiSchemaWikilDataSetReader extends MultiSchemaXmlDataSetReader {
	private final static Logger log4j = Logger.getlog4j(MultiSchemaWikilDataSetReader.class);

	private String defaultSchemaName;

	public MultiSchemaWikilDataSetReader(String defaultSchemaName) {
		super(defaultSchemaName);
		this.defaultSchemaName = defaultSchemaName;
	}

	/**
	 * Parses the datasets from the given files. Each schema is given its own
	 * dataset and each row is given its own table.
	 * 
	 * @param dataSetFiles
	 *            The dataset files, not null
	 * @return The read data set, not null
	 */
	public MultiSchemaDataSet readDataSetWiki(File... dataSetFiles) {
		try {
			DataSetContentHandler dataSetContentHandler = new DataSetContentHandler(defaultSchemaName);
			XMLReader xmlReader = createXMLReader();
			xmlReader.setContentHandler(dataSetContentHandler);
			xmlReader.setErrorHandler(dataSetContentHandler);
			for (File wikiFile : dataSetFiles) {
				String xml = WikiParser.parser(wikiFile);
				if (log4j.isInfoEnabled()) {
					log4j.info("xml parsed form wiki:" + xml);
				}
				StringReader reader = new StringReader(xml);
				xmlReader.parse(new InputSource(reader));
				reader.close();
			}
			return dataSetContentHandler.getMultiSchemaDataSet();
		} catch (Throwable e) {
			throw new UnitilsException("Unable to parse data set xml.", e);
		}
	}
}
