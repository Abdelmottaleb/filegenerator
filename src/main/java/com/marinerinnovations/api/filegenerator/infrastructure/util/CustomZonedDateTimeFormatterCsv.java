package com.marinerinnovations.api.filegenerator.infrastructure.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.univocity.parsers.conversions.Conversion;

@Component
public class CustomZonedDateTimeFormatterCsv implements Conversion<String, ZonedDateTime> {

	@Value("${date.time.formatter.pattern}")
	private String pattern;

	@Value("${zone.id}")
	private String idZone;

	private DateTimeFormatter formatter;
	
	private ZoneId zone;

	public CustomZonedDateTimeFormatterCsv() {

	}

	public CustomZonedDateTimeFormatterCsv(String... args) {
		//the @Value and env.getProperty doesnt work for this class ?! ==> I use constant args 
		 if(args.length > 0){
	            pattern = args[0];
	            idZone = args[1];
	        }
		this.formatter = DateTimeFormatter.ofPattern(pattern);
		this.zone = ZoneId.of(idZone);
	}

	@Override
	public ZonedDateTime execute(String input) {
		return ZonedDateTime.parse(input, formatter);
	}

	@Override
	public String revert(ZonedDateTime input) {
		return formatter.format(input.withZoneSameInstant(zone));
	}

}
