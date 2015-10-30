package com.dastres.factory;

import java.util.Collection;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.dastres.config.Constant;
import com.dastres.domain.LocationInfo;
import com.dastres.http.jaxrs.JRSManager;
import com.dastres.http.jaxrs.JRSManagerImpl;
import com.dastres.http.jaxrs.JRSRequest;
import com.dastres.rsws.types.input.TransferParamTO;

public class JRSBaseFactory {

	private static final JRSManager jwsManagerInstace = new JRSManagerImpl();

	public static JRSManager getJWSManager() {
		return jwsManagerInstace;
	}

	public interface RestURL {
		String base = "/rsws";

		interface General {
			String innerBase = base + "/general";
			String ping = innerBase + "/ping";
		}
		
		interface UserService{
			String innerBase = base + "/user";
			String addLocation = innerBase + "/addLocation";
		}

		interface Search {
			String innerBase = base + "/search";
			String findLocation = innerBase + "/findLocation";
		}

		interface Storage {
			String innerBase = base + "/storage";
			String count = innerBase + "/count";
			String add = innerBase + "/add";
			String addWithReceipt = innerBase + "/addWithReceipt";
			String transfer = innerBase + "/transfer";
			String transferReceipt = innerBase + "/transferReceipt";
		}
	}

	public static JRSRequest getTransferRequest(String machineAddress,
			TransferParamTO transferTO) {
		JRSRequest request = new JRSRequest();
		request.setUrl(machineAddress + RestURL.Storage.transfer);
		request.setMethod(Constant.HTTP_METHOD_POST);
		request.addHeader(Constant.HTTP_HEADER_ACCEPT,
				Constant.MEDIA_TYPE_JSON_UTF8);
		request.addHeader(Constant.HTTP_HEADER_CONTENT_TYPE,
				Constant.MEDIA_TYPE_JSON_UTF8);
		try {
			request.setData(transferTO.asJson().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return request;
	}

	public static JRSRequest getTransferReceiptRequest(String machineAddress,
			String ticket) {
		JRSRequest request = new JRSRequest();
		request.setUrl(machineAddress + RestURL.Storage.transferReceipt);
		request.setMethod(Constant.HTTP_METHOD_POST);
		request.addHeader(Constant.HTTP_HEADER_ACCEPT,
				Constant.MEDIA_TYPE_JSON_UTF8);
		request.addHeader(Constant.HTTP_HEADER_CONTENT_TYPE,
				Constant.MEDIA_TYPE_JSON_UTF8);
		try {
			JSONObject js = new JSONObject();
			js.put("ticket", ticket);
			request.setData(js.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return request;
	}

	public static JRSRequest getSendStorageDataRequest(
			Collection<LocationInfo> locInfos, String machineAddress) {

		JRSRequest request = new JRSRequest();
		request.setUrl(machineAddress + RestURL.Storage.add);
		request.setMethod(Constant.HTTP_METHOD_POST);
		request.addHeader(Constant.HTTP_HEADER_ACCEPT,
				Constant.MEDIA_TYPE_JSON_UTF8);
		request.addHeader(Constant.HTTP_HEADER_CONTENT_TYPE,
				Constant.MEDIA_TYPE_JSON_UTF8);
		try {
			JSONObject js = new JSONObject();
			JSONArray jsArr = new JSONArray();
			for (LocationInfo locInfo : locInfos) {
				jsArr.put(locInfo.asJson());
			}
			js.put("locStorageTOs", jsArr);
			request.setData(js.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return request;
	}

	public static JRSRequest getSendStorageDataWithReceiptRequest(
			Collection<LocationInfo> locInfos, String machineAddress,
			String ticket, String receiptAddress) {

		JRSRequest request = new JRSRequest();
		request.setUrl(machineAddress + RestURL.Storage.addWithReceipt);
		request.setMethod(Constant.HTTP_METHOD_POST);
		request.addHeader(Constant.HTTP_HEADER_ACCEPT,
				Constant.MEDIA_TYPE_JSON_UTF8);
		request.addHeader(Constant.HTTP_HEADER_CONTENT_TYPE,
				Constant.MEDIA_TYPE_JSON_UTF8);
		try {
			JSONObject js = new JSONObject();
			JSONArray jsArr = new JSONArray();
			for (LocationInfo locInfo : locInfos) {
				jsArr.put(locInfo.asJson());
			}
			js.put("locStorageTOs", jsArr);
			js.put("ticket", ticket);
			js.put("receiptURL", receiptAddress);
			request.setData(js.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return request;
	}

}
