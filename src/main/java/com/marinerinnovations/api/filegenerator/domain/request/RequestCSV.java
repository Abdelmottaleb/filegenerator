package com.marinerinnovations.api.filegenerator.domain.request;

import java.time.ZonedDateTime;

import com.marinerinnovations.api.filegenerator.infrastructure.util.CustomZonedDateTimeFormatterCsv;
import com.univocity.parsers.annotations.Convert;
import com.univocity.parsers.annotations.Parsed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestCSV extends AbstractRequest {

	@Parsed(field = "request-time")
	@Convert(conversionClass = CustomZonedDateTimeFormatterCsv.class, args = { "yyyy-MM-dd HH:mm:ss z", "Canada/Atlantic" })
	private ZonedDateTime requestTime;
}
