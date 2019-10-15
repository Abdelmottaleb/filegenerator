package com.marinerinnovations.api.filegenerator.presentation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marinerinnovations.api.filegenerator.service.exceptions.BusinessServiceException;
import com.marinerinnovations.api.filegenerator.service.exceptions.TechnicalException;
import com.marinerinnovations.api.filegenerator.service.requests.FileReaderBusinessService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import sun.util.logging.resources.logging;

@Slf4j
@Api("/files")
@RequestMapping("/files")
@RestController
public class FilesController extends AbstractController {
	
	private final FileReaderBusinessService fileReaderBS;

    @Autowired
    public FilesController(FileReaderBusinessService fileReaderBS) {
        this.fileReaderBS = fileReaderBS;
    }
	
	@ApiOperation("Generate a combined CSV file from 3 input files : .json, .csv and .xml ...")
	@GetMapping("/generate")
	public ResponseEntity<Boolean> generateCombinedCsvFile() throws TechnicalException, BusinessServiceException
	{
		
		log.debug("generateCombinedCsvFile >>> beging processing...");
		
		 this.fileReaderBS.processFiles();
		 
		 log.debug("<<< generateCombinedCsvFile  end processing...");
		 
		return ResponseEntity.ok().build();
	}

}
