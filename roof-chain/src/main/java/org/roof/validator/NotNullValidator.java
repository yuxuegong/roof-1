package org.roof.validator;

/**
 * 非空验证器
 * 
 * @author liuxin
 *
 */
public class NotNullValidator extends DefaultDataValidator {

	public NotNullValidator() {
		this.errorMessageTemp = "不能为空";
	}

	@Override
	public void valid(String val) throws ValidateException {
		if (val != null) {
			return;
		}
		throw new ValidateException(getErrorMessage(val));
	}

}
