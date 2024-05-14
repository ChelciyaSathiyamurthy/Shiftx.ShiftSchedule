package com.shiftx.shiftpatterns;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
		List<DayInfoVO> daysList = new ArrayList<>();
		ShiftService service = new ShiftService();
		Instant startTime = Instant.parse("2024-05-10T09:00:00Z");
		Instant endTime = Instant.parse("2024-05-10T17:30:00Z");
		for (int i = 0; i <= 6; i++) {

			DayInfoVO dayInfoVO = new DayInfoVO();
			dayInfoVO.setStartTimeInstant(startTime);
			dayInfoVO.setEndTimeInstant(endTime);
			daysList.add(i, dayInfoVO);
			if (i == 2 || i == 4) {
				daysList.add(i, null);
			}
		}
		assertEquals("Regular Type", service.findShiftType(daysList));
	}

	@Test
	void testFindShiftType02() {
		List<DayInfoVO> daysList = new ArrayList<>();
		ShiftService service = new ShiftService();
		Instant startTime = Instant.parse("2024-05-10T09:00:00Z");
		Instant endTime = Instant.parse("2024-05-10T17:30:00Z");
		daysList.add(0, new DayInfoVO(startTime, endTime));
		daysList.add(1, new DayInfoVO(startTime.plus(Duration.ofHours(1)), endTime.plus(Duration.ofHours(2))));
		daysList.add(2, null);
		daysList.add(3, new DayInfoVO(startTime.plus(Duration.ofHours(1)), endTime.minus(Duration.ofHours(2))));
		daysList.add(4, null);
		daysList.add(5, new DayInfoVO(startTime.plus(Duration.ofHours(1)), endTime.minus(Duration.ofHours(1))));
		daysList.add(6, new DayInfoVO(startTime.plus(Duration.ofHours(1)), endTime.minus(Duration.ofHours(1))));
		assertEquals("Variable Type", service.findShiftType(daysList));
	}

	@Test
	void testIsValidShift() throws ShiftServiceException {
		String input = "0900-1730-B30,0900-1730-B30,OFF,0900-1730-B30,OFF,0900-1730-B30,0900-1730-B30";
		ShiftService service = new ShiftService();

		List<DayInfoVO> daysList = service.processShiftPattern(input);
		assertEquals(true, service.isValidShift(daysList));

	}

	@Test
	void testIsValidShift01() {
		String input = "0900-1730-B30,0900-1730-B30,OFF,0900-1730-B30,OFF,0900-0030-B30,0200-1730-B30";
		ShiftService service = new ShiftService();
		try {
			service.processShiftPattern(input);
		} catch (ShiftServiceException e) {
			assertEquals("Duration between two shifts not meeting the norms", e.getMessage());
		}

	}

}
