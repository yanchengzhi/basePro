package com.ycz.pojo;

public class AjaxResult {
	
	private boolean success;
	private Object data;//用来封装数据

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
