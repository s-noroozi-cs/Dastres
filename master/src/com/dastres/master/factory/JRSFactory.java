package com.dastres.master.factory;

import java.util.List;

import com.dastres.config.Constant;
import com.dastres.domain.LocationInfo;
import com.dastres.factory.JRSBaseFactory;
import com.dastres.http.jaxrs.JRSRequest;
import com.dastres.master.domain.SlaveTO;
import com.dastres.master.rsws.types.input.LocationParamTO;

public class JRSFactory extends JRSBaseFactory{
	
	public static JRSRequest getSendStorageDataRequest(
			List<LocationInfo> locInfos, SlaveTO slaveTO) {
		return getSendStorageDataRequest(locInfos, slaveTO.getAddress());
	}

	public static JRSRequest getRecordCountRequest(SlaveTO slaveTO) {
		JRSRequest request = new JRSRequest();
		request.setMethod(Constant.HTTP_METHOD_GET);
		request.setUrl(slaveTO.getAddress() + RestURL.Storage.count);
		request.setData("");
		return request;
	}

	public static JRSRequest getPingTimeRequest(SlaveTO slaveTO) {
		JRSRequest request = new JRSRequest();
		request.setMethod(Constant.HTTP_METHOD_GET);
		request.setUrl(slaveTO.getAddress() + RestURL.General.ping);
		request.setData("");
		return request;
	}

	public static JRSRequest getFindLocationRequest(
			LocationParamTO locationParamTO, SlaveTO slaveTO) {

		JRSRequest request = new JRSRequest();
		request.setMethod(Constant.HTTP_METHOD_POST);
		request.setUrl(slaveTO.getAddress() + RestURL.Search.findLocation);
		request
				.addHeader(Constant.HTTP_HEADER_ACCEPT,
						Constant.MEDIA_TYPE_JSON_UTF8);
		request.addHeader(Constant.HTTP_HEADER_CONTENT_TYPE,
				Constant.MEDIA_TYPE_JSON_UTF8);
		request.setData(locationParamTO.asJson().toString());
		return request;
	}
	
	public static JRSRequest getFindLocationRequest(
			LocationParamTO locationParamTO,String masterAdr) {

		JRSRequest request = new JRSRequest();
		request.setMethod(Constant.HTTP_METHOD_POST);
		request.setUrl(masterAdr + RestURL.Search.findLocation);
		request
				.addHeader(Constant.HTTP_HEADER_ACCEPT,
						Constant.MEDIA_TYPE_JSON_UTF8);
		request.addHeader(Constant.HTTP_HEADER_CONTENT_TYPE,
				Constant.MEDIA_TYPE_JSON_UTF8);
		request.setData(locationParamTO.asJson().toString());
		return request;
	}
	
	public static JRSRequest getAddUserLocationRequest(String hostAddress,LocationInfo locInfo){
		
		JRSRequest request = new JRSRequest();
		request.setMethod(Constant.HTTP_METHOD_POST);
		request.setUrl(hostAddress + RestURL.UserService.addLocation);
		request
				.addHeader(Constant.HTTP_HEADER_ACCEPT,
						Constant.MEDIA_TYPE_JSON_UTF8);
		request.addHeader(Constant.HTTP_HEADER_CONTENT_TYPE,
				Constant.MEDIA_TYPE_JSON_UTF8);
		request.setData(locInfo.asJson().toString());
		return request;
	}
}
