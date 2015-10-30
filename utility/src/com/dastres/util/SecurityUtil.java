package com.dastres.util;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;

import com.dastres.config.Constant;

public class SecurityUtil {
	public static String hash(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(input.getBytes(Constant.US_ASCII_ENCODING));
			return new String(Hex.encodeHex(md.digest()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String makeCredential(String userName, String pass)
			throws Exception {
		return hash(userName + "|" + pass);
	}

}
