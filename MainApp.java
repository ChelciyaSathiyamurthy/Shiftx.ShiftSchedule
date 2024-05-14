package com.shiftx.shiftpatterns;

public class MainApp {

	public static void main(String[] args) {
		String input="0900-1730-B30,0900-1730-B30,OFF,0900-1730-B30,OFF,0900-1730-B30,0900-1730-B30";

		ShiftService service=new ShiftService();
		try {
			service.processShiftPattern(input);
		} catch (ShiftServiceException e) {
		
			e.printStackTrace();
		}
	}
}
