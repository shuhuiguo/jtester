package org.jtester.json.decoder.object;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.jtester.bytecode.reflector.helper.ClazzHelper;
import org.jtester.bytecode.reflector.helper.FieldHelper;
import org.jtester.exception.JSONException;
import org.jtester.json.JSON;
import org.jtester.json.decoder.ObjectDecoder;
import org.jtester.json.encoder.object.PoJoEncoder;
import org.jtester.json.helper.JSONFeature;
import org.jtester.json.helper.JSONMap;
import org.jtester.json.helper.JSONObject;
import org.jtester.json.helper.JSONSingle;

/**
 * 反序列json为pojo对象
 * 
 * @author darui.wudr
 * 
 */
@SuppressWarnings({ "rawtypes" })
public class PoJoDecoder extends ObjectDecoder<Object> {

	public PoJoDecoder(Class clazz) {
		super(clazz);
	}

	@Override
	protected void parseFromJSONMap(Object target, JSONMap map, Map<String, Object> references) {
		Class type = ClazzHelper.getUnProxyType(target.getClass());
		List<Field> fields = ClazzHelper.getAllFields(type, PoJoEncoder.filterFields, false, false, false);
		for (JSONObject key : map.keySet()) {
			if (key == null) {
				continue;
			}
			if (!(key instanceof JSONSingle)) {
				throw new JSONException("illegal syntax, the pojo field name property must be a JSONSingle type.");
			}

			String fieldname = ((JSONSingle) key).toStringValue();
			if (this.isJSONKeyword(fieldname)) {
				continue;
			}
			Field field = this.getFieldByName(fields, fieldname);
			if (field == null) {
				continue;
			}
			try {
				Object value = JSON.toObject(map.get(key), field.getType(), references);
				FieldHelper.setFieldValue(target, field, value);
			} catch (Exception e) {
				throw new JSONException("decode field[" + fieldname + "] error.", e);
			}
		}
	}

	private boolean isJSONKeyword(String name) {
		if (JSONFeature.ReferFlag.equals(name)) {
			return true;
		}
		if (JSONFeature.ClazzFlag.equals(name)) {
			return true;
		}
		if (JSONFeature.ReferFlag.equals(name)) {
			return true;
		}
		return false;
	}

	private Field getFieldByName(List<Field> fields, String name) {
		for (Field field : fields) {
			if (name.equals(field.getName())) {
				return field;
			}
		}
		return null;
		// Class type = this.realTargetType;
		// while (type != Object.class) {
		// try {
		// Field field = type.getDeclaredField(name);
		// return field;
		// } catch (Exception e) {
		// type = type.getSuperclass();
		// }
		// }
		// return null;
	}
}
