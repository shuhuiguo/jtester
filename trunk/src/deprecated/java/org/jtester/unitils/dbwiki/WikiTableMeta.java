package org.jtester.unitils.dbwiki;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WikiTableMeta {
	private String schemaName = null;

	private List<String> fieldNames = new ArrayList<String>();

	private FieldMeta currValues = null;

	private List<FieldMeta> fieldValues = new ArrayList<FieldMeta>();

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public void addFieldName(String fieldName) {
		this.fieldNames.add(fieldName);
	}

	public void newFieldLine() {
		if (this.currValues != null) {
			this.fieldValues.add(this.currValues);
		}
		this.currValues = new FieldMeta();
	}

	public void addFieldValue(String fieldValue) {
		this.currValues.addMeta(fieldValue);
	}

	public void endFieldLine() {
		if (this.currValues != null) {
			this.fieldValues.add(this.currValues);
		}
		this.currValues = null;
	}

	public String toXmlSnippet() {
		if (this.fieldValues == null || this.fieldValues.size() == 0) {
			return this.emptyDataSet();
		} else {
			return this.notEmptyDataSet();
		}
	}

	private String emptyDataSet() {
		StringBuffer xml = new StringBuffer();
		xml.append("<");
		xml.append(this.schemaName);
		xml.append("/>");
		return xml.toString();
	}

	private String notEmptyDataSet() {
		StringBuffer xml = new StringBuffer();
		for (FieldMeta values : fieldValues) {
			xml.append("<");
			xml.append(this.schemaName);
			Iterator<String> it = values.getMetaNames().iterator();
			for (String fieldName : this.fieldNames) {
				if (it.hasNext()) {
					String value = it.next();
					xml.append(String.format(" %s=\"%s\"", fieldName, value));
				} else {
					xml.append(String.format(" %s=\"\"", fieldName));
				}
			}
			xml.append("/>");
		}
		return xml.toString();
	}

	public static class FieldMeta {
		private List<String> metaNames = new ArrayList<String>();

		public List<String> getMetaNames() {
			return metaNames;
		}

		public void addMeta(String meta) {
			this.metaNames.add(meta);
		}
	}

	public List<String> getFieldNames() {
		return fieldNames;
	}

	public List<FieldMeta> getFieldValues() {
		return fieldValues;
	}
}
