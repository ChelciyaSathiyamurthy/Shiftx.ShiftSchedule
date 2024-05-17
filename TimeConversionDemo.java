package com.shiftx.shiftpatterns;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class TimeConversionDemo {

	public static void main(String[] args) {
		ZonedDateTime z=ZonedDateTime.of(LocalDate.now(), LocalTime.now(),(ZoneOffset.UTC));
		Instant i=z.toInstant();
		System.out.println("instant    "+i);
		convertToIndianTimeZone(i);
	}

	private static ZonedDateTime convertToIndianTimeZone(Instant i) {
ZonedDateTime i1=i.atZone(ZoneId.systemDefault());
ZonedDateTime convertedZonedDateTime=i1.withZoneSameInstant(ZoneId.of("America/Los_Angeles"));
System.out.println("1stconvert  "+i1);
System.out.println("2ndconvert  "+convertedZonedDateTime);
return convertedZonedDateTime;

	}

}
