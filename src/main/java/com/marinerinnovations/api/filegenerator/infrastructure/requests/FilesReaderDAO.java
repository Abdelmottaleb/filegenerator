package com.marinerinnovations.api.filegenerator.infrastructure.requests;

import java.util.List;

import com.marinerinnovations.api.filegenerator.domain.request.AbstractRequest;
import com.marinerinnovations.api.filegenerator.service.exceptions.TechnicalException;

/**
 * @author adas1 asume that csv, xml and json ares our dataSources instead of a
 *         real BDD or NoSql BDD..
 *
 */
public interface FilesReaderDAO {

	public List<AbstractRequest> readFile(String fileName) throws TechnicalException;

}
