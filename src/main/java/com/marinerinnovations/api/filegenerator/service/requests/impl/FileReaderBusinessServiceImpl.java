package com.marinerinnovations.api.filegenerator.service.requests.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.marinerinnovations.api.filegenerator.domain.request.AbstractRequest;
import com.marinerinnovations.api.filegenerator.infrastructure.requests.FilesReaderDAO;
import com.marinerinnovations.api.filegenerator.infrastructure.util.AppConstant;
import com.marinerinnovations.api.filegenerator.service.exceptions.BusinessServiceException;
import com.marinerinnovations.api.filegenerator.service.exceptions.TechnicalException;
import com.marinerinnovations.api.filegenerator.service.requests.FileReaderBusinessService;
import com.univocity.parsers.common.processor.BeanWriterProcessor;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;

import lombok.extern.slf4j.Slf4j;

/**
 * this class represent the business logic layer, it orchestrates the treatments
 * 
 * @author adas1
 *
 */
@Slf4j
@Service
public class FileReaderBusinessServiceImpl implements FileReaderBusinessService {

	@Value("${file.name}")
	private String fileName;

	@Value("${output.file}")
	private String resultFilePath;

	List<AbstractRequest> csvs;
	List<AbstractRequest> xmls;
	List<AbstractRequest> jsons;

	/**
	 * list that contains the final result to be written back to the csv file
	 * result
	 */
	private List<AbstractRequest> combinedResults;

	private final FilesReaderDAO filesReaderCsvDAO;
	private final FilesReaderDAO filesReaderXmlDAO;
	private final FilesReaderDAO filesReaderJsonDAO;

	@Autowired
	public FileReaderBusinessServiceImpl(@Qualifier("csvDAO") FilesReaderDAO filesReaderCsvDAO,
			@Qualifier("xmlDAO") FilesReaderDAO filesReaderXmlDAO,
			@Qualifier("jsonDAO") FilesReaderDAO filesReaderJsonDAO) {

		// we assume tha we have three different and heterogeneous data sources
		// => we consider one repository for each data source
		this.filesReaderCsvDAO = filesReaderCsvDAO;
		this.filesReaderXmlDAO = filesReaderXmlDAO;
		this.filesReaderJsonDAO = filesReaderJsonDAO;
	}

	@Override
	public void processFiles() throws TechnicalException, BusinessServiceException {

		log.debug(">>> readCsvFile : retreiving datas from data sources");

		if (StringUtils.isBlank(fileName)) {
			throw new BusinessServiceException(
					"you must specify a file name to continue, please check the properties file !");
		}

		/*******************************************************************************
		 * 1 - according to the business requirement, we start by retrieving
		 * data from our (heterogeneous) data sources we use @Qualifier for each
		 * impl repo
		 ******************************************************************************/
		this.readFiles();

		/************************************************************************
		 * 2 - generate combined list from all object issued from our data
		 * sources and apply Business requirement.
		 *************************************************************************/

		Optional<List<AbstractRequest>> allReports = this.getCombinedList(csvs, xmls, jsons);

		/************************************************************************
		 * 3 - output a combined CSV file with required characteristics.
		 *************************************************************************/

		if (!allReports.isPresent()) {
			throw new BusinessServiceException("connot process data ==> input files are empty !");
		}

		this.generateCsvCombinedFile(allReports.get());

		/***********************************************************************************************************
		 * 4 - print a summary showing the number of records in the output file
		 * associated with each service_guid.
		 ***********************************************************************************************************/
		this.printSummary(allReports.get());
	}

	/**
	 * retrieve date from differents data sources
	 * 
	 * @throws TechnicalException
	 */
	private void readFiles() throws TechnicalException {
		csvs = this.filesReaderCsvDAO.readFile(fileName + AppConstant.CSV_EXTENSION);
		xmls = this.filesReaderXmlDAO.readFile(fileName + AppConstant.XML_EXTENSION);
		jsons = this.filesReaderJsonDAO.readFile(fileName + AppConstant.JSON_EXTENSION);

	}

	/**
	 * permet to return a combined list from the three data sources lists csv,
	 * sml and json with the following requirements : - All report records with
	 * packets-serviced equal to zero should be excluded - records should be
	 * sorted by request-time in ascending order
	 * 
	 * @param csvs
	 *            : list of reports read from csv file datat source
	 * @param xmls
	 *            : list of reports read from xml file datat source
	 * @param jsons
	 *            : list of reports read from json file datat source
	 * @return
	 */
	Optional<List<AbstractRequest>> getCombinedList(List<AbstractRequest> csvs, List<AbstractRequest> xmls,
			List<AbstractRequest> jsons) {

		combinedResults = new ArrayList<AbstractRequest>();
		if (!CollectionUtils.isEmpty(csvs)) {
			combinedResults.addAll(csvs);
		}
		if (!CollectionUtils.isEmpty(xmls)) {
			combinedResults.addAll(xmls);
		}
		if (!CollectionUtils.isEmpty(jsons)) {
			combinedResults.addAll(jsons);
		}

		if (!CollectionUtils.isEmpty(combinedResults)) {

			// - All report records with packets-serviced equal to zero should
			// be excluded
			combinedResults = combinedResults.stream().filter(report -> report.getPacketsServiced() != 0)
					.collect(Collectors.toList());

			// - records should be sorted by request-time in ascending order
			combinedResults = combinedResults.stream().sorted(Comparator.comparing(AbstractRequest::getRequestTime))
					.collect(Collectors.toList());
		}

		return Optional.of(combinedResults);
	}

	/**
	 * generate a csv file contains combination aff all readed data sources
	 * 
	 * @param allReports
	 *            beans to write back to csv file result
	 * @throws TechnicalException
	 */
	private void generateCsvCombinedFile(List<AbstractRequest> allReports) throws TechnicalException {
		CsvWriterSettings csvWriterSettings = new CsvWriterSettings();

		// - The same column order and formatting as reports.csv
		csvWriterSettings.setHeaders(AppConstant.CLIENT_ADDRESS, AppConstant.CLIENT_GUID, AppConstant.REQUEST_TIME,
				AppConstant.SERVICE_GUID, AppConstant.RETRIES_REQUEST, AppConstant.PACKETS_REQUESTED,
				AppConstant.PACKETS_SERVICED, AppConstant.MAX_HOLE_SIZE);

		// desired path of result file is administrable in properties file
		File file = new File(resultFilePath);

		// delete if exist - deleteQuietly : Deletes a file, never throwing an exception
		FileUtils.deleteQuietly(file);
		try {
			// create new
			FileUtils.touch(file);
		} catch (IOException e) {
			log.error(" error reading/parsing  file : {} ", file.getName());
			throw new TechnicalException(e.getMessage());
		}

		FileReaderBusinessServiceImpl.writeBeans(file, csvWriterSettings, allReports, AbstractRequest.class);

	}

	/**
	 * generate csv file from jaba beans list
	 * 
	 * @param file
	 *            the output file wich we weill write csv data
	 * @param csvWriterSettings
	 *            csv settings
	 * @param records
	 *            lst of elements to write in output file
	 * @param cls
	 *            type of elements
	 * @throws FileNotFoundException
	 * @throws TechnicalException
	 */
	public static <T> void writeBeans(File file, CsvWriterSettings csvWriterSettings, List<T> records, Class<T> cls)
			throws TechnicalException {
		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			csvWriterSettings.setRowWriterProcessor(new BeanWriterProcessor<T>(cls));
			CsvWriter csvWriter = new CsvWriter(bos, csvWriterSettings);
			csvWriter.writeHeaders();
			csvWriter.processRecords(records);
			csvWriter.close();
		} catch (FileNotFoundException e) {
			log.error(" error reading/parsing  file : {} ", file.getName());
			throw new TechnicalException(e.getMessage());
		}
	}

	/**
	 * print a summary showing the number of records in the output file
	 * associated with each service_guid.
	 */
	private void printSummary(List<AbstractRequest> allReports) {
		Map<String, Long> counting = allReports.stream()
				.collect(Collectors.groupingBy(AbstractRequest::getServiceGuid, Collectors.counting()));
		
		log.info("******* SUMMARYY : number of records in the output file associated with each service_guid *******");
		
		counting.forEach((key, value) -> log.info("service_guid : " + key + ", number of records : " + value));
		
		log.info("*************************************************************************************************");

	}
}
