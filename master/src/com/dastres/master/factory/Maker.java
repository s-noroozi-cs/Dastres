package com.dastres.master.factory;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.dastres.config.Constant;
import com.dastres.master.domain.CSVZipDataInfo;
import com.dastres.master.domain.ImportDataInfo;
import com.dastres.domain.LocationInfo;

public class Maker {

	private static Logger logger = Logger.getLogger(Maker.class);

	public static List<LocationInfo> makeFromCSV(byte[] data, String separator)
			throws UnsupportedEncodingException {
		List<LocationInfo> list = new LinkedList<LocationInfo>();

		StringTokenizer records = new StringTokenizer(new String(data,
				Constant.UTF8_ENCODING), Constant.CSV_RECORD_SEPARATOR);
		logger.info("number of records: " + records.countTokens());
		
		while (records.hasMoreTokens()) {
			StringTokenizer fields = new StringTokenizer(records.nextToken(),
					separator);
			LocationInfo locInfo = new LocationInfo();

			if (fields.countTokens() > 2) {
				locInfo.setLongitude(fields.nextToken());
				locInfo.setLatitude(fields.nextToken());
				locInfo.setName(fields.nextToken());

				while (fields.hasMoreTokens()) {
					locInfo.addTag(fields.nextToken());
				}
				list.add(locInfo);
			}

		}

		return list;
	}

	public static ImportDataInfo makeCSVZipDataInfo(InputStream is,
			String separator) {
		CSVZipDataInfo dataInfo = new CSVZipDataInfo();
		dataInfo.setInputStream(is);
		dataInfo.setSeparator(separator);
		return dataInfo;
	}
}
