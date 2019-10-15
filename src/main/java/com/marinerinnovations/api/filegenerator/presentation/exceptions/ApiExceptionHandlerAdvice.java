package com.marinerinnovations.api.filegenerator.presentation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;
import com.marinerinnovations.api.filegenerator.presentation.exceptions.ApiError.APITypeError;
import com.marinerinnovations.api.filegenerator.service.exceptions.BusinessServiceException;
import com.marinerinnovations.api.filegenerator.service.exceptions.TechnicalException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandlerAdvice {

	private static final Logger LOGGER= LoggerFactory.getLogger(ApiExceptionHandlerAdvice.class);

	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiError technical(Exception exception, WebRequest request) {
		LOGGER.error(exception.getMessage(), exception);
		ApiError error= new ApiError(exception.getMessage());
		error.setTypeError(APITypeError.GENERIC);
		return error;
	}

	@ExceptionHandler(value = TechnicalException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiError technicalException(Throwable exception, WebRequest request) {
		ApiError error= new ApiError(exception.getMessage());
		error.setTypeError(APITypeError.TECHNICAL);
		error.setCause(Throwables.getRootCause(exception).getMessage());
		log.error(exception.getMessage());
		return error;
	}



	@ExceptionHandler(value = BusinessServiceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiError badRequest(BusinessServiceException exception, WebRequest request) {
		ApiError error= new ApiError(exception.getMessage());
		error.setTypeError(APITypeError.FUNCTIONAL);
		error.setMessage(exception.getMessage());
		return error;
	}

	@ExceptionHandler(value = Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiError internal(Throwable exception, WebRequest request) {
		LOGGER.error(exception.getMessage(), exception);
		ApiError error= new ApiError(exception.getMessage());
		error.setTypeError(APITypeError.GENERIC);
		error.setCause(Throwables.getRootCause(exception).getMessage());
		return error;
	}

}
