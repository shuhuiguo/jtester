package org.jtester.json.decoder.single.spec;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.jtester.bytecode.reflector.helper.FieldHelper;
import org.jtester.bytecode.reflector.helper.MethodHelper;
import org.jtester.json.decoder.single.SpecTypeDecoder;

@SuppressWarnings("rawtypes")
public class SimpleDateFormatDecoder<T extends SimpleDateFormat> extends SpecTypeDecoder<SimpleDateFormat, T> {

	public SimpleDateFormatDecoder(Class clazz) {
		super(clazz);
	}

	@Override
	protected void setTargetValue(SimpleDateFormat target, String value) {
		FieldHelper.setFieldValue(target, "pattern", value);
		Locale locale = Locale.getDefault();
		target.setDateFormatSymbols(new DateFormatSymbols(locale));
		MethodHelper.invoke(target, "initialize", locale);
	}

	@Override
	protected SimpleDateFormat getDefaultInstance() {
		return new SimpleDateFormat();
	}
}
