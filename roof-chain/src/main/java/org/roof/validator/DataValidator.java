package org.roof.validator;

/**
 * 数据有效验证器
 * 
 * @author liuxin
 *
 */
public interface DataValidator {
	/**
	 * 验证
	 * 
	 * @param val
	 *            需要验证的值
	 * @return true 通过验证, false 不通过验证
	 */
	void valid(String val) throws ValidateException;

	/**
	 * 获得验证错误信息
	 * 
	 * @return
	 */
	String getErrorMessage(Object... params);

}
