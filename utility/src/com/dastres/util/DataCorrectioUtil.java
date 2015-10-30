package com.dastres.util;

public class DataCorrectioUtil {
	private static final char PERSIAN_YA = 'ی';
	private static final char ARABIC_YA = 'ي';
	private static final char PERSIAN_KAF = 'ک';
	private static final char ARABIC_KAF = 'ك';

	private static final char SEFR = '۰';
	private static final char YEK = '۱';
	private static final char DO = '۲';
	private static final char SE = '۳';
	private static final char CHEHAR = '۴';
	private static final char PANJ = '۵';
	private static final char SHESH = '۶';
	private static final char HAFT = '۷';
	private static final char HASHT = '۸';
	private static final char NOH = '۹';

	private static final char ARB_SEFR = '٠';
	private static final char ARB_YEK = '١';
	private static final char ARB_DO = '٢';
	private static final char ARB_SE = '٣';
	private static final char ARB_CHEHAR = '٤';
	private static final char ARB_PANJ = '٥';
	private static final char ARB_SHESH = '٦';
	private static final char ARB_HAFT = '٧';
	private static final char ARB_HASHT = '٨';
	private static final char ARB_NOH = '٩';

	public static String unifiedChar(String src) {
		String dest = null;
		try {
			dest = src.replace(ARABIC_KAF, PERSIAN_KAF);
			dest = dest.replace(ARABIC_YA, PERSIAN_YA);

			dest = dest.replace(SEFR, '0');
			dest = dest.replace(YEK, '1');
			dest = dest.replace(DO, '2');
			dest = dest.replace(SE, '3');
			dest = dest.replace(CHEHAR, '4');
			dest = dest.replace(PANJ, '5');
			dest = dest.replace(SHESH, '6');
			dest = dest.replace(HAFT, '7');
			dest = dest.replace(HASHT, '8');
			dest = dest.replace(NOH, '9');

			dest = dest.replace(ARB_SEFR, '0');
			dest = dest.replace(ARB_YEK, '1');
			dest = dest.replace(ARB_DO, '2');
			dest = dest.replace(ARB_SE, '3');
			dest = dest.replace(ARB_CHEHAR, '4');
			dest = dest.replace(ARB_PANJ, '5');
			dest = dest.replace(ARB_SHESH, '6');
			dest = dest.replace(ARB_HAFT, '7');
			dest = dest.replace(ARB_HASHT, '8');
			dest = dest.replace(ARB_NOH, '9');
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return dest;
	}

}
