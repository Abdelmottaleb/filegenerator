package com.marinerinnovations.api.filegenerator.presentation.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
abstract public class AbstractController {

	// we can put here common behavoir ...
}
