package com.xxx.biz.service;

public class EsbResult implements java.io.Serializable {
	private static final long serialVersionUID = 8514215927548436570L;

	private boolean success;

	private Object result;

	public EsbResult() {
		this.success = true;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}
