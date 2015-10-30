package com.dastres.util;


public class NumberUtil {

	public static boolean isPositive(Integer number) {
		return number != null && number.intValue() > 0;
	}

	public static boolean isPositive(Long number) {
		return number != null && number.longValue() > 0;
	}

	public static boolean isPositive(String number) {
		try {
			return Integer.parseInt(number) > 0;
		} catch (Throwable ex) {
			return false;
		}
	}

	public static int cast2Int(String number, int defultValue) {
		int retVal = -1;
		if (StringUtil.isEmpty(number)) {
			retVal = defultValue;
		} else {
			try {
				retVal = Integer.parseInt(number);
			} catch (Throwable ex) {
				retVal = defultValue;
			}
		}
		return retVal;
	}

}
