package com.dastres.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtil {

	public static List<byte[]> extractFiles(InputStream is) throws IOException {
		

		System.out.println("size of inputStream " + is.available());

		List<byte[]> zEntryList = new ArrayList<byte[]>();

		ZipInputStream zis = new ZipInputStream(is);
		ZipEntry zipEntry = zis.getNextEntry();

		while (zipEntry != null) {

			System.out.println("zEntry.getName: " + zipEntry.getName());
			System.out.println("zEntry.isDirectory: " + zipEntry.isDirectory());
			
			int bufferSize = 1024 * 20;

			byte[] buffer = new byte[bufferSize];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			if (!zipEntry.isDirectory()) {
				int readLen = zis.read(buffer);
				while (readLen > 0) {
					baos.write(buffer, 0, readLen);
					readLen = zis.read(buffer);
				}
				zEntryList.add(baos.toByteArray());
			}

			zipEntry = zis.getNextEntry();
		}
		
		is.close();

		return zEntryList;
	}

}
