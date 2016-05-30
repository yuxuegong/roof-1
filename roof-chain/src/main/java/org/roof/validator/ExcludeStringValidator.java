package org.roof.validator;

/**
 * 验证不能带有字符
 * 
 * @author liuxin
 *
 */
public class ExcludeStringValidator extends DefaultDataValidator {
	private String[] excludeStrings;

	public ExcludeStringValidator() {
		this.errorMessageTemp = "参数值{0}不允许包含{1}";
	}

	@Override
	public void valid(String val) throws ValidateException {
		if (val == null) {
			return;
		}
		for (String excludeString : excludeStrings) {
			if (val.indexOf(excludeString) != -1) {
				throw new ValidateException(getErrorMessage(val, excludeString));
			}
		}
		return;
	}

	public String[] getExcludeStrings() {
		return excludeStrings;
	}

	public void setExcludeStrings(String[] excludeStrings) {
		this.excludeStrings = excludeStrings;
	}

}
