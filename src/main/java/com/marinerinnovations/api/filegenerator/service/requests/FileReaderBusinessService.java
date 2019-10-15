package com.marinerinnovations.api.filegenerator.service.requests;

import com.marinerinnovations.api.filegenerator.service.exceptions.BusinessServiceException;
import com.marinerinnovations.api.filegenerator.service.exceptions.TechnicalException;

public interface FileReaderBusinessService {

	/**
	 * this method allow reading the csv file data source
	 */
	public void processFiles() throws TechnicalException, BusinessServiceException;

}
