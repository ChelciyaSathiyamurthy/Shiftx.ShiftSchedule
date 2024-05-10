package com.shiftx.shiftpatterns;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class ShiftServiceTest {

	@Test
	void testParseDayInfo() throws ShiftServiceException {
		ShiftService service = new ShiftService();
		DayInfoVO dayInfoVO = service.parseDayInfo("0900-1730-B30");
		DayInfoVO expecteddayInfoVO = new DayInfoVO();
		expecteddayInfoVO.setStartTime("0900");
		expecteddayInfoVO.setEndTime("1730");
		expecteddayInfoVO.setBreakTime("B30");
		assertEquals(expecteddayInfoVO, dayInfoVO);
	}

	@Test
	void testParseDayInfo01() {
		ShiftService service = new ShiftService();

		try {
			service.parseDayInfo(null);
		} catch (ShiftServiceException e) {
			assertEquals("Pattern cannot be null value", e.getMessage());

		}

	}

	@Test
	void testParseDayInfo02() {
		ShiftService service = new ShiftService();
		try {
			service.parseDayInfo("09001730-B30");
		} catch (ShiftServiceException e) {
			assertEquals("Invalid Pattern", e.getMessage());

		}

	}

	@Test
	public void testConvertStringTimeToInstant() throws ShiftServiceException {
		ShiftService service = new ShiftService();
		LocalDate date = LocalDate.of(2024, 5, 6);
		Instant expected = Instant.parse("2024-05-06T04:40:00Z");
		Instant actual = service.convertStringTimeToInstant("1010", date);
		assertEquals(expected, actual);
	}

	@Test
	public void testMinutesBetween() {
		ShiftService service = new ShiftService();
		Instant fromTime = Instant.parse("2024-05-06T08:00:00Z");
		Instant toTime = Instant.parse("2024-05-06T17:00:00Z");
		long expected = 540;
		long actual = service.minutesBetween(fromTime, toTime);
		assertEquals(expected, actual);
	}

	@Test
	public void testMinutesBetween01() {
		ShiftService service = new ShiftService();
		Instant fromTime = null;
		Instant toTime = Instant.parse("2024-05-06T17:00:00Z");
		try {
			service.minutesBetween(fromTime, toTime);
		} catch (NullPointerException e) {
			assertEquals("Input value cannot be null", e.getMessage());
		}
	}

	@Test
	void testFindShiftType() {
		Map<Integer, DayInfoVO> daysMap = new HashMap<>();
		ShiftService service = new ShiftService();
		Instant startTime = Instant.parse("2024-05-10T09:00:00Z");
		Instant endTime = Instant.parse("2024-05-10T17:30:00Z");
		for (int i = 1; i <= 7; i++) {
			if (i==3&&i==5) {continue;}
			DayInfoVO dayInfoVO = new DayInfoVO();
			dayInfoVO.setStartTimeInstant(startTime);
			dayInfoVO.setEndTimeInstant(endTime);
			daysMap.put(i, dayInfoVO);
		}
		assertEquals("Regular Type", service.findShiftType(daysMap));
	}

	@Test
	void testFindShiftType01() {
		Map<Integer, DayInfoVO> daysMap = new HashMap<>();
		ShiftService service = new ShiftService();
		for (int i = 1; i <= 7; i++) {
			daysMap.put(i, null);
		}
		try {
			service.findShiftType(daysMap);
		} catch (NullPointerException e) {
			assertEquals("Input Contains Null values", e.getMessage());
		}
	}

	@Test
	void testFindShiftType02() {
		Map<Integer, DayInfoVO> daysMap = new HashMap<>();
		ShiftService service = new ShiftService();
		Instant startTime = Instant.parse("2024-05-10T09:00:00Z");
		Instant endTime = Instant.parse("2024-05-10T17:30:00Z");
		daysMap.put(1, new DayInfoVO(startTime, endTime));
		daysMap.put(2, new DayInfoVO(startTime.plus(Duration.ofHours(1)), endTime.plus(Duration.ofHours(2))));
		daysMap.put(4, new DayInfoVO(startTime.plus(Duration.ofHours(1)), endTime.minus(Duration.ofHours(2))));
		daysMap.put(6, new DayInfoVO(startTime.plus(Duration.ofHours(1)), endTime.minus(Duration.ofHours(1))));
		daysMap.put(7, new DayInfoVO(startTime.plus(Duration.ofHours(1)), endTime.minus(Duration.ofHours(1))));
		assertEquals("Variable Type", service.findShiftType(daysMap));
	}
}
