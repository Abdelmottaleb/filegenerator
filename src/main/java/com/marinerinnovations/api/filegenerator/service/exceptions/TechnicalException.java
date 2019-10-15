package com.marinerinnovations.api.filegenerator.service.exceptions;


/**
 * @author adas1
 * class to handel Technicals exceptions
 *
 */
public class TechnicalException  extends Exception  {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public TechnicalException(String message) {
		super(message);
	}

	public TechnicalException(Throwable cause) {
		super(cause);
	}

	public TechnicalException(String message, Throwable cause) {
		super(message, cause);
	}
}