package com.shiftx.shiftpatterns;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Objects;

public class DayInfoVO {

String startTime;
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
String endTime;
String breakTime;
Instant startTimeInstant;
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
public LocalTime getBreakTimeInstant() {
	return breakTimeInstant;
}
public void setBreakTimeInstant(LocalTime breakTimeInstant) {
	this.breakTimeInstant = breakTimeInstant;
}
Instant endTimeInstant;
LocalTime breakTimeInstant;
long workingHours;
public long getWorkingHours() {
	return workingHours;
}
public void setWorkingHours(long workingHours) {
	this.workingHours = workingHours;
}
String scheduleType;
public String getScheduleType() {
	return scheduleType;
}

public void setScheduleType(String scheduleType) {
	this.scheduleType = scheduleType;
}
}
