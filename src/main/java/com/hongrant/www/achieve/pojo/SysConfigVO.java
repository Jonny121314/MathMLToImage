package com.hongrant.www.achieve.pojo;

import java.io.Serializable;
/**
 * 系统配置实体类
 * @author jonny
 *
 */
public class SysConfigVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer parameterId;
	
	private String paramName;
	
	private String paramValue;

	public Integer getParameterId() {
		return parameterId;
	}

	public void setParameterId(Integer parameterId) {
		this.parameterId = parameterId;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	@Override
	public String toString() {
		return "SysConfigVO [parameterId=" + parameterId + ", paramName=" + paramName + ", paramValue=" + paramValue
				+ "]";
	}
	
}
