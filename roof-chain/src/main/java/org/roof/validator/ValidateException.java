package org.roof.validator;

/**
 * 验证失败异常
 * 
 * @author liuxin
 *
 */
public class ValidateException extends Exception {

	private static final long serialVersionUID = -6637206672713818217L;

	public ValidateException() {
		super();
	}

	public ValidateException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidateException(String message) {
		super(message);
	}

	public ValidateException(Throwable cause) {
		super(cause);
	}

}
