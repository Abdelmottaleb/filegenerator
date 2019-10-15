package com.marinerinnovations.api.filegenerator.infrastructure.util;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

@Component
public class CustomZonedDateTimeFormatterJson extends JsonDeserializer<ZonedDateTime>{

	@Value("${zone.id}")
	private String idZone;

	@Override
	public ZonedDateTime deserialize(JsonParser arg0, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
		ZonedDateTime zonedTimeJSON = Instant.ofEpochMilli(new Long(arg0.getText())).atZone(ZoneId.of("Canada/Atlantic"));
		return zonedTimeJSON;
	}




}
