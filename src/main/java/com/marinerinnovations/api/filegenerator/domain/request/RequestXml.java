package com.marinerinnovations.api.filegenerator.domain.request;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.marinerinnovations.api.filegenerator.infrastructure.util.CustomZonedDateTimeFormatterCsv;
import com.univocity.parsers.annotations.Convert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestXml extends AbstractRequest{
	
	@JsonProperty("request-time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss z")
	@Convert(conversionClass = CustomZonedDateTimeFormatterCsv.class, args = { "yyyy-MM-dd HH:mm:ss z", "Canada/Atlantic" })
	private ZonedDateTime requestTime;
}
