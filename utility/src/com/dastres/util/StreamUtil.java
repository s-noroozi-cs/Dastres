package com.dastres.util;

import java.io.InputStream;

import com.dastres.config.Constant;

public class StreamUtil {

	public static String getString(InputStream inStream) {
		StringBuilder responseStorage = new StringBuilder();

		byte[] buffer = new byte[1024 * 10];
		try {
			int readLen = inStream.read(buffer);
			while (readLen > 0) {
				responseStorage.append(new String(buffer, 0, readLen,
						Constant.UTF8_ENCODING));
				readLen = inStream.read(buffer);
			}
			inStream.close();
		} catch (Throwable ex) {
			responseStorage.append(ex.getMessage());
		}

		return responseStorage.toString();
	}

}
