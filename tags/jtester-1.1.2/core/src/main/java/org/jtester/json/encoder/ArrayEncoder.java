package org.jtester.json.encoder;

import java.io.Writer;
import java.util.List;

import org.jtester.json.helper.JSONFeature;

@SuppressWarnings({ "rawtypes" })
public abstract class ArrayEncoder<T> extends JSONEncoder<T> {

	public ArrayEncoder(Class clazz) {
		super(clazz);
	}

	@Override
	public void encode(T target, Writer writer, List<String> references) {
		try {
			boolean isNullOrRef = this.writerNullOrReference(target, writer, references, true);
			if (isNullOrRef) {
				return;
			}
			if (this.unMarkClassFlag == false) {
				writer.append('{');
				this.writeClassFlag(target, writer);
				writer.append(',');
				this.writerSpecProperty(JSONFeature.ValueFlag, writer);
				writer.append(':');
			}
			writer.append('[');
			this.encodeIterator(target, writer, references);
			writer.append(']');
		} catch (Exception e) {
			throw this.wrapException(e);
		}
	}

	protected abstract void encodeIterator(T target, Writer writer, List<String> references) throws Exception;
}
