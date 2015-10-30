package com.dastres.util;

public class JsonUtil {

	public static String removeJsonKeywords(String str) {
		if (StringUtil.isNotEmpty(str))
			return str.replace('"', ' ').replace('\'', ' ').replace(':', ' ')
					.replace('{', ' ').replace('}', ' ').replace('[', ' ')
					.replace(']', ' ');
		else
			return str;
	}

}
