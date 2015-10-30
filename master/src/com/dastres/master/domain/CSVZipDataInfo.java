package com.dastres.master.domain;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import com.dastres.domain.LocationInfo;
import com.dastres.master.exception.DataException;
import com.dastres.master.factory.Maker;
import com.dastres.util.ZipUtil;

public class CSVZipDataInfo implements ImportDataInfo {

	private InputStream inputStream;
	private String separator;

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public List<LocationInfo> getLocationInfos() throws DataException {
		
		List<LocationInfo> list = new LinkedList<LocationInfo>();

		try {

			for (byte[] data : ZipUtil.extractFiles(inputStream)) {
				list.addAll(Maker.makeFromCSV(data, separator));
			}

		} catch (Throwable ex) {
			throw new DataException(ex.getMessage(), ex);
		}

		
		return list;
	}

}
