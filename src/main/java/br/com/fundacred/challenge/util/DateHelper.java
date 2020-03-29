package br.com.fundacred.challenge.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public interface DateHelper {

	public static Date fromZonedDateTime(final ZonedDateTime zonedDateTime) {
		return Date.from(zonedDateTime.toInstant());
	}
	
	public static ZonedDateTime toZonedDateTime(final Date date) {
		return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

}
