package org.roof.validator;

public class ValidatorUnit {
	private String paramName; // 参数名称
	private String validators;// 验证器名称,多个用英文逗号(,)隔开
	private String defaultVal; // 默认值

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getValidators() {
		return validators;
	}

	public void setValidators(String validators) {
		this.validators = validators;
	}

	public String getDefaultVal() {
		return defaultVal;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}

}
