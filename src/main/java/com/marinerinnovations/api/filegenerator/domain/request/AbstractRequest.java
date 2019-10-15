package com.marinerinnovations.api.filegenerator.domain.request;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.marinerinnovations.api.filegenerator.infrastructure.util.CustomZonedDateTimeFormatterCsv;
import com.univocity.parsers.annotations.Convert;
import com.univocity.parsers.annotations.Parsed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractRequest {

	@Parsed(field = "client-address")
	@JsonProperty("client-address")
	private String clientAdress;

	@Parsed(field = "client-guid")
	@JsonProperty("client-guid")
	private String clientGuid;

	@Parsed(field = "service-guid")
	@JsonProperty("service-guid")
	private String serviceGuid;

	@Parsed(field = "retries-request")
	@JsonProperty("retries-request")
	private int retriesRequest;

	@Parsed(field = "packets-requested")
	@JsonProperty("packets-requested")
	private int packetsRequested;

	@Parsed(field = "packets-serviced")
	@JsonProperty("packets-serviced")
	private int packetsServiced;

	@Parsed(field = "max-hole-size")
	@JsonProperty("max-hole-size")
	private int maxHoleSize;
	
	@Parsed(field = "request-time")
	@JsonProperty("request-time")
	@Convert(conversionClass = CustomZonedDateTimeFormatterCsv.class, args = { "yyyy-MM-dd HH:mm:ss z", "Canada/Atlantic" })
	private ZonedDateTime requestTime;

}
