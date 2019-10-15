package com.marinerinnovations.api.filegenerator.presentation.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.FieldError;

public class ApiError {

	public enum APITypeError {

		ACCESS_DENIED("AccessDeniedFault"), 
		GENERIC("GenericFault"), 
		FUNCTIONAL("functionnal"), 
		UNAUTHORIZED("UnauthorizedFault"), 
		NOT_FOUND("ResourceNotFoundFault"), 
		TECHNICAL("technical");

		private String label = "";

		APITypeError(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
	}

	private APITypeError typeError;
	private String message;
	private String cause;
	private List<FieldError> fieldErrors = new ArrayList<FieldError>();

	public ApiError() {
	}

	public ApiError(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public APITypeError getTypeError() {
		return typeError;
	}

	public void setTypeError(APITypeError typeError) {
		this.typeError = typeError;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}
}
