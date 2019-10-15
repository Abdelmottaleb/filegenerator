package com.marinerinnovations.api.filegenerator.domain.request;

import java.time.ZonedDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.marinerinnovations.api.filegenerator.infrastructure.util.CustomZonedDateTimeFormatterCsv;
import com.marinerinnovations.api.filegenerator.infrastructure.util.CustomZonedDateTimeFormatterJson;
import com.univocity.parsers.annotations.Convert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestJson extends AbstractRequest{
	
	@JsonProperty("request-time")
	@JsonDeserialize(using = CustomZonedDateTimeFormatterJson.class)
	@Convert(conversionClass = CustomZonedDateTimeFormatterCsv.class, args = { "yyyy-MM-dd HH:mm:ss z", "Canada/Atlantic" })
	private ZonedDateTime requestTime;
}
