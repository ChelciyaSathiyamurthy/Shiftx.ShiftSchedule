package com.shiftx.shiftpatterns;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterAll;
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
		DayInfoVO dayInfoVO;
		try {
			dayInfoVO = service.parseDayInfo(null);
		} catch (ShiftServiceException e) {
			assertEquals("Pattern cannot be null value", e.getMessage());

		}

	}

	@Test
	void testParseDayInfo02() {
		ShiftService service = new ShiftService();
		DayInfoVO dayInfoVO;
		try {
			dayInfoVO = service.parseDayInfo("09001730-B30");
		} catch (ShiftServiceException e) {
			assertEquals("Invalid Pattern", e.getMessage());

		}

	}

	@Test
	public void testConvertStringTimeToInstant() {
		ShiftService service = new ShiftService();
		LocalDate date = LocalDate.of(2024, 5, 6);
		Instant expected = Instant.parse("2024-05-06T04:40:00Z");
		Instant actual = service.convertStringTimeToInstant("1010", date);
		assertEquals(expected, actual);
	}

	@Test
	public void testConvertBreakTimeToInstant() {
		ShiftService service = new ShiftService();
		LocalDate date = LocalDate.of(2024, 5, 6);
		LocalTime expected = LocalTime.of(0, 30);
		LocalTime actual = service.convertBreakTimeToInstant("B30", date);
		assertEquals(expected, actual);
	}

	@Test
	public void testConvertBreakTimeToInstant01() {
		ShiftService service = new ShiftService();
		LocalDate date = LocalDate.of(2024, 5, 6);
		LocalTime expected = LocalTime.of(0, 30);
		try {
			LocalTime actual = service.convertBreakTimeToInstant("u30", date);
		} catch (IllegalArgumentException e) {

			assertEquals("Invalid break time format", e.getMessage());
		}
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
}
