package com.shiftx.shiftpatterns;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ShiftService {

	public void processShiftPattern(String fullPattern) throws ShiftServiceException {
		String[] splitedDaywisePattern = fullPattern.split(",");
		Map<Integer, DayInfoVO> daysMap = new HashMap<>();
		for (int i = 0; i < splitedDaywisePattern.length; i++) {
			if (!splitedDaywisePattern[i].equalsIgnoreCase("OFF")) {
				DayInfoVO dayInfoVO = processDayPattern(splitedDaywisePattern[i]);
				daysMap.put(i + 1, dayInfoVO);
				dayInfoVO.setIsValidShift(isValidShift(daysMap));
				dayInfoVO.setShiftType(findShiftType(daysMap));
			}
		}

	}

	public String findShiftType(Map<Integer, DayInfoVO> daysMap) {
		DayInfoVO firstDayInfo = daysMap.get(1);
		for (int i = 2; i <= daysMap.size(); i++) {
			DayInfoVO dayInfo = daysMap.get(i);
			if (dayInfo == null) {
				continue;
			}
			if (!dayInfo.getStartTimeInstant().equals(firstDayInfo.getStartTimeInstant())
					|| !dayInfo.getEndTimeInstant().equals(firstDayInfo.getEndTimeInstant())) {
				return "Variable Type";
			}
		}
		return "Regular Type";
	}

	public boolean isValidShift(Map<Integer, DayInfoVO> daysMap)throws NullPointerException {
		int currentDay = 1;
		int lastDay = 7;
		boolean isValid = true;
		while (currentDay < lastDay) {
			DayInfoVO currentDayInfo = daysMap.get(currentDay);
			int nextDay = currentDay + 1;
			while (nextDay <= lastDay && !daysMap.containsKey(nextDay)) {
				nextDay++;
			}
			if (nextDay > lastDay) {
				break;
			}
			DayInfoVO nextDayInfo = daysMap.get(nextDay);
			if (currentDayInfo==null || nextDayInfo==null) {
				throw new NullPointerException("Input cannot be null.");
			}
			Duration duration = Duration.between(currentDayInfo.getEndTimeInstant(), nextDayInfo.getStartTimeInstant());
			if (duration.isNegative()) {
				duration = duration.plusHours(24);
			}
			if (duration.toHours() < 8) {
				isValid = false;
			}
			currentDay = nextDay;
		}

		return isValid;
	}

	public DayInfoVO processDayPattern(String splitedDaywisePattern) throws ShiftServiceException {
		DayInfoVO dayInfoVO = parseDayInfo(splitedDaywisePattern);
		Instant startTimeInstant = convertStringTimeToInstant(dayInfoVO.getStartTime(), LocalDate.of(2024, 5, 6));
		Instant endTimeInstant = convertStringTimeToInstant(dayInfoVO.getEndTime(), LocalDate.of(2024, 5, 6));
		int breakTimeInstant = Integer.parseInt(dayInfoVO.getBreakTime().substring(1));
		dayInfoVO.setStartTimeInstant(startTimeInstant);
		dayInfoVO.setEndTimeInstant(endTimeInstant);
		dayInfoVO.setBreakTimeInstant(breakTimeInstant);
		dayInfoVO.setWorkingHours(minutesBetween(dayInfoVO.startTimeInstant, dayInfoVO.endTimeInstant));

		return dayInfoVO;
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

	public Instant convertStringTimeToInstant(String stringTime, LocalDate localdate) throws ShiftServiceException {
		if (!stringTime.matches("\\d{4}")) {
			throw new ShiftServiceException("Invalid time format.Please provide a 4-digit number.");
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
		LocalTime localTime = LocalTime.parse(stringTime, formatter);
		ZonedDateTime zonedDateTime = localTime.atDate(localdate).atZone(ZoneId.of("Asia/Kolkata"));
		return zonedDateTime.toInstant();
	}

	// Method to find the working minutes
	public long minutesBetween(Instant fromTime, Instant toTime) throws NullPointerException {
		if (fromTime == null || toTime == null) {
			throw new NullPointerException("Input value cannot be null");
		}
		Duration workDuration = Duration.between(fromTime, toTime);
		return workDuration.toMinutes();
	}

}
