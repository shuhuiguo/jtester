package org.jtester.unitils.dbwiki;

import java.io.File;
import java.util.Arrays;
import java.util.Properties;

import org.unitils.core.UnitilsException;
import org.unitils.dbunit.datasetfactory.DataSetFactory;
import org.unitils.dbunit.util.MultiSchemaDataSet;

public class MultiSchemaWikiDataSetFactory implements DataSetFactory {
	/**
	 * The schema name to use when no name was explicitly specified.
	 */
	protected String defaultSchemaName;

	public MultiSchemaDataSet createDataSet(File... dataSetFiles) {
		try {
			MultiSchemaWikilDataSetReader multiSchemaWikiDataSetReader = new MultiSchemaWikilDataSetReader(
					defaultSchemaName);
			return multiSchemaWikiDataSetReader.readDataSetWiki(dataSetFiles);
		} catch (Exception e) {
			throw new UnitilsException("Unable to create DbUnit dataset for data set files: "
					+ Arrays.toString(dataSetFiles), e);
		}
	}

	public void init(Properties configuration, String defaultSchemaName) {
		this.defaultSchemaName = defaultSchemaName;
	}

	public String getDataSetFileExtension() {
		return "wiki";
	}
}
