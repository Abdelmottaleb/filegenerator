/**
 * 
 */
package com.marinerinnovations.api.filegenerator.infrastructure.requests.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.marinerinnovations.api.filegenerator.domain.request.AbstractRequest;
import com.marinerinnovations.api.filegenerator.domain.request.RequestCSV;
import com.marinerinnovations.api.filegenerator.infrastructure.requests.FilesReaderDAO;
import com.marinerinnovations.api.filegenerator.infrastructure.util.FileUtils;
import com.marinerinnovations.api.filegenerator.service.exceptions.TechnicalException;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import lombok.extern.slf4j.Slf4j;

/**
 * @author adas1 we have three distinct and heterogeneous data sources this
 *         implementation to read data from the CSV data source.
 *
 */

@Slf4j
@Repository("csvDAO")
public class FilesReaderCsvDAOImpl implements FilesReaderDAO {

	/*
	 * read line from csv file data source and map them to our domain objects
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AbstractRequest> readFile(String csvFileName) throws TechnicalException {

		log.debug("readFile >>> retreiving datas from CSV data source.");

		BeanListProcessor<RequestCSV> rowProcessor = new BeanListProcessor<RequestCSV>(RequestCSV.class);

		// config csv parser
		CsvParserSettings parserSettings = new CsvParserSettings();
		parserSettings.getFormat().setLineSeparator("\n");
		parserSettings.setProcessor(rowProcessor);
		parserSettings.setHeaderExtractionEnabled(true);

		CsvParser parser = new CsvParser(parserSettings);
		FileUtils fileUtils = new FileUtils();
		List<RequestCSV> requests = new ArrayList<RequestCSV>();

		try {
			// get the data source
			parser.parse(fileUtils.getInputStreamForFile(csvFileName));

			// retrieve data
			requests = rowProcessor.getBeans();
		} catch (Exception e) {
			log.error(" error reading/parsing  file : {} ", csvFileName);
			throw new TechnicalException("error reading/parsing  file " + e.getMessage());
		}

		return (List<AbstractRequest>) (List<? extends AbstractRequest>) requests;
	}

}
