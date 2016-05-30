package org.roof.chain.exceptions;

/**
 * 集合内值不存在
 * 
 * @author liuxin 2011-9-26
 * @version 1.0 ValueExistsException.java liuxin 2011-9-26
 */
public class ValueExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public ValueExistsException() {
		super();
	}

	public ValueExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValueExistsException(String message) {
		super(message);
	}

	public ValueExistsException(Throwable cause) {
		super(cause);
	}

}
