package org.jtester.json.decoder.array;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import org.jtester.json.JSON;
import org.jtester.json.decoder.ArrayDecoder;
import org.jtester.json.helper.JSONObject;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ArraysDecoder<T> extends ArrayDecoder<T[]> {

	public ArraysDecoder(Class clazz) {
		super(clazz == Object.class ? HashMap.class : clazz);
	}

	@Override
	protected void setObjectByIndex(T[] array, int index, JSONObject json, Map<String, Object> references) {
		Object value = JSON.toObject(json, this.clazz, references);
		array[index] = (T)value;
	}

	@Override
	protected T[] newArraysObject(int size) {
		T[] array = (T[]) Array.newInstance(this.realTargetType == null ? this.clazz : this.realTargetType, size);
		return array;
	}
}
