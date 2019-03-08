package homework3;

import java.util.Date;

public class DoJudge {

	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isDateMatchAge(String date, String age) {
		if ((2019-Integer.parseInt(date.substring(0, 4))) == (Integer.parseInt(age))) {
			return true;
		} else {
			return false;
		}
	}
	
}
