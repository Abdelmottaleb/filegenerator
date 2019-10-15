package com.marinerinnovations.api.filegenerator.infrastructure.requests.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.marinerinnovations.api.filegenerator.domain.request.AbstractRequest;
import com.marinerinnovations.api.filegenerator.domain.request.Records;
import com.marinerinnovations.api.filegenerator.domain.request.RequestXml;
import com.marinerinnovations.api.filegenerator.infrastructure.requests.FilesReaderDAO;
import com.marinerinnovations.api.filegenerator.infrastructure.util.FileUtils;
import com.marinerinnovations.api.filegenerator.service.exceptions.TechnicalException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository("xmlDAO")
public class FilesReaderXmlDAOImpl implements FilesReaderDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<AbstractRequest> readFile(String xmlFileName) throws TechnicalException {

		log.debug("readFile >>> retreiving datas from XML data source.");
		
		final XmlMapper mapper = new XmlMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
		FileUtils fileUtils = new FileUtils();
		List<RequestXml> requests = new ArrayList<RequestXml>();

		try {
			Records records = mapper.readValue(fileUtils.getInputStreamForFile(xmlFileName), Records.class);
			if (records != null && !CollectionUtils.isEmpty(records.getReport())) {
				requests = records.getReport();
			}
		} catch (IOException e) {
			log.error(" error reading/parsing  file : {} ", xmlFileName);
			throw new TechnicalException("error reading/parsing  file " + e.getMessage());
		}

		return (List<AbstractRequest>) (List<? extends AbstractRequest>) requests;
	}
}
