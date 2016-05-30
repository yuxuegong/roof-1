package org.roof.validator;


import org.apache.commons.lang3.StringUtils;

/**
 * 字符串有值校验
 *
 * @author liuxin
 */
public class NotEmptyValidator extends DefaultDataValidator {

    public NotEmptyValidator() {
        this.errorMessageTemp = "不能为空";
    }

    @Override
    public void valid(String val) throws ValidateException {
        if (StringUtils.isNotEmpty(val)) {
            return;
        }
        throw new ValidateException(getErrorMessage(val));
    }

}
