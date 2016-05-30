package org.roof.validator;

import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;


public abstract class DefaultDataValidator implements DataValidator {

    protected String errorMessageTemp;

    @Override
    public String getErrorMessage(Object... params) {
        if (StringUtils.isEmpty(errorMessageTemp)) {
            return this.getClass().getName() + "未定义验证错误信息";
        }
        return MessageFormat.format(errorMessageTemp, params);
    }

    public String getErrorMessageTemp() {
        return errorMessageTemp;
    }

    public void setErrorMessageTemp(String errorMessageTemp) {
        this.errorMessageTemp = errorMessageTemp;
    }

}
