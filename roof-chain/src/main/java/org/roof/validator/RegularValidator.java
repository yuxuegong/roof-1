package org.roof.validator;

import java.util.regex.Pattern;

import org.springframework.beans.factory.InitializingBean;

/**
 * 正则表达式验证
 * 
 * <p>
 * 是否符合正则表达式
 * 
 * @author liuxin
 *
 */
public class RegularValidator extends DefaultDataValidator implements InitializingBean {
	private String regular;
	private Pattern pattern = null;

	public RegularValidator() {
		this.errorMessageTemp = "值[{0}]不符合正则表达式[{1}]";
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		pattern = Pattern.compile(regular);
	}

	@Override
	public void valid(String val) throws ValidateException {
		if (val == null) {
			return;
		}
		if (!pattern.matcher(val).matches()) {
			throw new ValidateException(getErrorMessage(val, regular));
		}
	}

	public String getRegular() {
		return regular;
	}

	public void setRegular(String regular) {
		this.regular = regular;
	}

}
