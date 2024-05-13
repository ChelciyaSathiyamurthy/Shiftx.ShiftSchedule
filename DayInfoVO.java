package com.shiftx.shiftpatterns;

import java.time.Instant;
import java.util.Objects;

public class DayInfoVO {

	String startTime;

	String endTime;
	String breakTime;
	Instant startTimeInstant;

	Instant endTimeInstant;
	int breakTimeInstant;
	long workingHours;
	Boolean isValidShift;
	String shiftType;
	private int day;


	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public DayInfoVO(Instant startTime2, Instant endTime2) {
		this.startTimeInstant = startTime2;
		this.endTimeInstant = endTime2;
	}

	public DayInfoVO() {

	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(breakTime, endTime, startTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DayInfoVO other = (DayInfoVO) obj;
		return Objects.equals(breakTime, other.breakTime) && Objects.equals(endTime, other.endTime)
				&& Objects.equals(startTime, other.startTime);
	}

	public String getBreakTime() {
		return breakTime;
	}

	public void setBreakTime(String breakTime) {
		this.breakTime = breakTime;
	}

	public Instant getStartTimeInstant() {
		return startTimeInstant;
	}

	public void setStartTimeInstant(Instant startTimeInstant) {
		this.startTimeInstant = startTimeInstant;
	}

	public Instant getEndTimeInstant() {
		return endTimeInstant;
	}

	public void setEndTimeInstant(Instant endTimeInstant) {
		this.endTimeInstant = endTimeInstant;
	}

	public int getBreakTimeInstant() {
		return breakTimeInstant;
	}

	public void setBreakTimeInstant(int breakTimeInstant2) {
		this.breakTimeInstant = breakTimeInstant2;
	}

	public long getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(long workingHours) {
		this.workingHours = workingHours;
	}

	public Boolean getIsValidShift() {
		return isValidShift;
	}

	public void setIsValidShift(Boolean isValidShift) {
		this.isValidShift = isValidShift;
	}

	public String getShiftType() {
		return shiftType;
	}

	public void setShiftType(String shiftType) {
		this.shiftType = shiftType;
	}
}
