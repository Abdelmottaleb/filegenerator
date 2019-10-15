package com.marinerinnovations.api.filegenerator.infrastructure.requests.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marinerinnovations.api.filegenerator.domain.request.AbstractRequest;
import com.marinerinnovations.api.filegenerator.domain.request.RequestJson;
import com.marinerinnovations.api.filegenerator.infrastructure.requests.FilesReaderDAO;
import com.marinerinnovations.api.filegenerator.infrastructure.util.FileUtils;
import com.marinerinnovations.api.filegenerator.service.exceptions.TechnicalException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository("jsonDAO")
public class FilesReaderJsonDAOImpl implements FilesReaderDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<AbstractRequest> readFile(String jsonFileName) throws TechnicalException {
		
		log.debug("readFile >>> retreiving datas from JSON data source.");
		
		ObjectMapper objectMapper = new ObjectMapper();
		FileUtils fileUtils = new FileUtils();
		List<RequestJson> requests = new ArrayList<RequestJson>();

		try {
			RequestJson[] lines = objectMapper.readValue(fileUtils.getInputStreamForFile(jsonFileName),
					RequestJson[].class);
			if (lines != null) {
				requests = Arrays.asList(lines);
			}
		} catch (IOException e) {
			log.error(" error reading/parsing  file : {} ", jsonFileName);
			throw new TechnicalException("error reading/parsing  file " + e.getMessage());
		}

		return (List<AbstractRequest>) (List<? extends AbstractRequest>)requests;
	}

}
