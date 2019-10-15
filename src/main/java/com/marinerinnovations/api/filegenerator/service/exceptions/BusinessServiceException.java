package com.marinerinnovations.api.filegenerator.service.exceptions;

/**
 * @author adas1
 * class to handel busnesses exceptions
 *
 */
public class BusinessServiceException extends Exception{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public BusinessServiceException(String message) {
		super(message);
	}

	public BusinessServiceException(Throwable cause) {
		super(cause);
	}

	public BusinessServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
