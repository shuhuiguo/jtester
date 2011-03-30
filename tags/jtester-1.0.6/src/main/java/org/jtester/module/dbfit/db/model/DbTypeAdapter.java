package org.jtester.module.dbfit.db.model;

import org.jtester.fit.util.ParseArg;
import org.jtester.module.dbfit.db.enviroment.Options;

import fit.TypeAdapter;

public class DbTypeAdapter extends TypeAdapter {

	public Object parse(final String s) throws Exception {
		String text = ParseArg.parseCellValue(s);
		boolean isNull = text.toLowerCase().equals("<null>");
		if (isNull) {
			return null;
		}
		boolean isString = this.type.equals(String.class) && Options.isFixedLengthStringParsing()
				&& text.startsWith("'") && text.endsWith("'");
		if (isString) {
			return text.substring(1, text.length() - 1);
		}
		TypeAdapter ta = TypeAdapter.adapterFor(this.type);
		boolean isTypeAdapter = ta.getClass().equals(TypeAdapter.class);
		if (isTypeAdapter) {
			return super.parse(text);
		} else {
			return ta.parse(text);
		}
	}
}
