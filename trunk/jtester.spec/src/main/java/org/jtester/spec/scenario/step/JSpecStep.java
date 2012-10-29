package org.jtester.spec.scenario.step;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jtester.spec.exceptions.SkipStepException;
import org.jtester.spec.util.ParaConverter;
import org.jtester.tools.commons.StringHelper;

@SuppressWarnings("serial")
public abstract class JSpecStep implements Serializable {
	protected StepType type;

	protected String method;

	protected String initialText;

	protected String displayText;

	protected LinkedHashMap<String, String> paras;

	protected boolean isSkip;

	protected Throwable error;

	protected String scenario;

	protected JSpecStep(String scenario) {
		this.scenario = scenario;
		this.error = null;
	}

	public void setError(Throwable error) {
		this.error = error;
	}

	public boolean isSkip() {
		return isSkip;
	}

	public Object[] getArguments(List<String> paraNameds, List<Type> paraTypes) {
		List<Object> values = new ArrayList<Object>();
		for (int index = 0; index < paraNameds.size(); index++) {
			String paraNamed = paraNameds.get(index);
			if (this.paras.containsKey(paraNamed) == false) {
				String keys = StringHelper.merger(this.paras.keySet(), ',');
				String error = String.format("can't find parameter %s, the existed parameters are [%s].", paraNamed,
						keys);
				throw new RuntimeException(error);
			}
			String paraValue = this.paras.get(paraNamed);

			Type paraType = paraTypes.get(index);
			Object value = ParaConverter.convert(paraValue, paraType);
			values.add(value);
		}
		return values.toArray(new Object[0]);
	}

	/**
	 * 解析场景步骤的具体内容<br>
	 * etc: 参数, 描述等
	 * 
	 * @param content
	 */
	public abstract void parseStep(Object content, JSpecStep template);

	public SpecMethodID getSpecMethodID() {
		SpecMethodID id = new SpecMethodID(type, method, paras.size());
		return id;
	}

	public StepType getType() {
		return type;
	}

	public String getInitialText() {
		return initialText;
	}

	public String getDisplayText() {
		return displayText;
	}

	public String getMethod() {
		return method;
	}

	public LinkedHashMap<String, String> getParas() {
		return paras;
	}

	@Override
	public String toString() {
		return type + " method[" + method + "], skip status[" + isSkip + "]";
	}

	public String toTxtString() {
		StringBuilder buff = new StringBuilder();
		if (this.error == null) {
			buff.append("SUCCESS ");
		} else if (this.error == SkipStepException.instance) {
			buff.append("SUSPEND ");
		} else {
			buff.append("FAILURE ");
		}
		buff.append(type.name()).append("\t").append(this.method);
		buff.append("\n").append(this.displayText).append("\n");
		if (this.error != null && this.error != SkipStepException.instance) {
			buff.append("\tError:").append(this.error.getMessage()).append("\n");
		}
		return buff.toString();
	}

	public static JSpecStep findTemplate(List<JSpecStep> templates, String method, StepType type) {
		if (templates == null || method == null || type == null) {
			return null;
		}
		for (JSpecStep template : templates) {
			if (method.equals(template.method) && type.equals(template.type)) {
				return template;
			}
		}
		return null;
	}

	/**
	 * 根据模板初始化参数内容
	 * 
	 * @param template
	 * @return
	 */
	protected LinkedHashMap<String, String> initParameters(JSpecStep template) {
		LinkedHashMap<String, String> inits = new LinkedHashMap<String, String>();
		if (template == null) {
			return inits;
		}
		for (Map.Entry<String, String> para : template.getParas().entrySet()) {
			inits.put(para.getKey(), para.getValue());
		}
		return inits;
	}
}