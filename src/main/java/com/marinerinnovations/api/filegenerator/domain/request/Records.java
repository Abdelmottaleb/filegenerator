package com.marinerinnovations.api.filegenerator.domain.request;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "records")
public class Records {

	@JacksonXmlElementWrapper(localName = "report", useWrapping = false)
	private List<RequestXml> report = new ArrayList<RequestXml>();

}
