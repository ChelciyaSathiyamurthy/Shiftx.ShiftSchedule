package com.shiftx.shiftpatterns;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ShiftService {

	public void processShiftPattern(String fullPattern) throws ShiftServiceException {
		String[] splitedDaywisePattern = fullPattern.split(",");
		for (int i = 0; i < splitedDaywisePattern.length; i++) {
			if (!splitedDaywisePattern[i].equalsIgnoreCase("OFF")) {
				processDayPattern(splitedDaywisePattern[i]);
			}
		}
	}

	public void processDayPattern(String splitedDaywisePattern) throws ShiftServiceException {
		DayInfoVO dayInfoVO = parseDayInfo(splitedDaywisePattern);
		Instant startTimeInstant = convertStringTimeToInstant(dayInfoVO.getStartTime(), LocalDate.of(2024, 5, 6));
		Instant endTimeInstant = convertStringTimeToInstant(dayInfoVO.getEndTime(), LocalDate.of(2024, 5, 6));
		LocalTime breakTimeInstant = convertBreakTimeToInstant(dayInfoVO.getBreakTime(), LocalDate.of(2024, 5, 6));
		dayInfoVO.setStartTimeInstant(startTimeInstant);
		dayInfoVO.setEndTimeInstant(endTimeInstant);
		dayInfoVO.setBreakTimeInstant(breakTimeInstant);
		dayInfoVO.setWorkingHours(minutesBetween(dayInfoVO.startTimeInstant, dayInfoVO.endTimeInstant));
		findShiftType(dayInfoVO);
	}
	//

	public void findShiftType(DayInfoVO dayInfoVO) {
		Instant StartTime1 = null;
		Instant EndTime1 = null;
		if (StartTime1 == null && EndTime1 == null) {
			StartTime1 = dayInfoVO.getStartTimeInstant();
			EndTime1 = dayInfoVO.getEndTimeInstant();
		} else {

			if (!StartTime1.equals(dayInfoVO.getStartTimeInstant())
					|| !EndTime1.equals(dayInfoVO.getEndTimeInstant())) {
				dayInfoVO.setScheduleType("Variable Type");
			} else {
				dayInfoVO.setScheduleType("Regular Type");
			}
		}

	}

	public DayInfoVO parseDayInfo(String splitedDaywisePattern) throws ShiftServiceException {
		if (splitedDaywisePattern == null) {
			throw new ShiftServiceException("Pattern cannot be null value");
		}
		String[] splittedDayInfo = splitedDaywisePattern.split("-");
		if (splittedDayInfo.length != 3) {
			throw new ShiftServiceException("Invalid Pattern");
		}
		DayInfoVO dayInfoVO = new DayInfoVO();
		dayInfoVO.setStartTime(splittedDayInfo[0]);
		dayInfoVO.setEndTime(splittedDayInfo[1]);
		dayInfoVO.setBreakTime(splittedDayInfo[2]);
		return dayInfoVO;
	}
	// Method to convert the string time into instant..

	public Instant convertStringTimeToInstant(String stringTime, LocalDate localdate) {
		LocalTime localTime = convertTimeStringToLocaltime(stringTime);
		ZonedDateTime zonedDateTime = localTime.atDate(localdate).atZone(ZoneId.of("Asia/Kolkata"));
		return zonedDateTime.toInstant();
	}
	// Method to convert string time to localtime..

	public LocalTime convertTimeStringToLocaltime(String stringTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
		return LocalTime.parse(stringTime, formatter);
	}

	// Method to convert breaktime string to instant
	public LocalTime convertBreakTimeToInstant(String breakTime, LocalDate localdate) throws IllegalArgumentException {
		if (!breakTime.startsWith("B")) {
			throw new IllegalArgumentException("Invalid break time format");
		}
		LocalTime Localbreaktime = LocalTime.of(0, Integer.parseInt(breakTime.substring(1)));
		System.out.println(Localbreaktime);
		return Localbreaktime;

	}

	// Method to find the working minutes
	public long minutesBetween(Instant fromTime, Instant toTime) {
		Duration workDuration = Duration.between(fromTime, toTime);
		System.out.println(workDuration);
		return workDuration.toMinutes();
	}
}
