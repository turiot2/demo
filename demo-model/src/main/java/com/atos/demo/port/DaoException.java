package com.atos.demo.port;

/**
 * A (not checked) exception raised by a IDAO implementation.
 */
public class DaoException extends RuntimeException {

	private static final long serialVersionUID = -7304159436342371344L;

	/**
	 * Constructor for DaoException.
	 * @param msg the detail message
	 */
	public DaoException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for DaoException.
	 * @param msg the detail message
	 * @param cause the root cause (usually from using a underlying
	 * data access API such as JDBC)
	 */
	public DaoException(String msg, Throwable cause) {
		super(msg, cause);
	}

}