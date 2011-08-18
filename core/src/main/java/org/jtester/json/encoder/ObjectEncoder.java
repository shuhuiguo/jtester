package org.jtester.json.encoder;

import java.io.Writer;
import java.util.Collection;
import java.util.List;

@SuppressWarnings({ "rawtypes" })
public abstract class ObjectEncoder<T> extends JSONEncoder<T> {

	protected ObjectEncoder(Class clazz) {
		super(clazz);
	}

	public void encode(T target, Writer writer, List<String> references) {
		try {
			this.encodeObject(target, writer, references);
		} catch (Exception e) {
			throw this.wrapException(e);
		}
	}

	private final void encodeObject(T target, Writer writer, List<String> references) throws Exception {
		boolean isNullOrRef = this.writerNullOrReference(target, writer, references, true);
		if (isNullOrRef) {
			return;
		}
		Collection<PropertyEncoder> encoders = this.getPropertyEncoders(target);

		writer.write("{");
		boolean isFirst = !this.writeClassFlag(target, writer);
		for (PropertyEncoder encoder : encoders) {
			if (isFirst) {
				isFirst = false;
			} else {
				writer.write(",");
			}
			encoder.encode(target, writer, references);
		}
		writer.write("}");
	}

	/**
	 * 返回对象需要序列化的属性列表
	 * 
	 * @param target
	 * @return
	 */
	protected abstract Collection<PropertyEncoder> getPropertyEncoders(T target);
}
