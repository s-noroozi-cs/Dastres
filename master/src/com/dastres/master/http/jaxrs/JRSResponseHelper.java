package com.dastres.master.http.jaxrs;

import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.dastres.http.jaxrs.JRSResponse;
import com.dastres.master.domain.SlaveTO;
import com.dastres.master.rsws.types.output.FindLocationResultTO;

public class JRSResponseHelper {
	private static Logger logger = Logger.getLogger(JRSResponseHelper.class);

	private static interface RespKey {
		static interface General {
			String SUCCESS_KEY = "success";
		}

		static interface Storage {
			String REC_COUNT_KEY = "recordCount";
		}
	}

	public static boolean isSuccess(JRSResponse response) {
		return response.getValueAsBoolean(RespKey.General.SUCCESS_KEY);
	}

	public static void processPingTime(SlaveTO slaveTO, JRSResponse response) {
		if (isSuccess(response)) {
			slaveTO.updatePingTime((int) response.getTimeDelay());
		}
	}

	public static FindLocationResultTO processFindLocation(
			List<SlaveTO> slaves, List<JRSResponse> responses) {
		long timeDelay = 0;
		ObjectMapper mapper = new ObjectMapper();
		FindLocationResultTO resultTO = new FindLocationResultTO();
		for (JRSResponse resp : responses) {
			timeDelay += resp.getTimeDelay();

			if (isSuccess(resp)) {
				try {
					resultTO.setSuccess(true);
					FindLocationResultTO findLoc = mapper.readValue(resp
							.getResponse(), FindLocationResultTO.class);
					resultTO.addLocationInfos(findLoc.getLocationInfos());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		resultTO.setSearchTime(timeDelay);
		return resultTO;
	}

	public static FindLocationResultTO processFindLocation(JRSResponse responses) {
		long timeDelay = 0;
		ObjectMapper mapper = new ObjectMapper();
		FindLocationResultTO resultTO = new FindLocationResultTO();
		timeDelay += responses.getTimeDelay();
		if (isSuccess(responses)) {
			resultTO.setSuccess(true);
			try {
				FindLocationResultTO findLoc = mapper.readValue(responses
						.getResponse(), FindLocationResultTO.class);
				resultTO.addLocationInfos(findLoc.getLocationInfos());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		resultTO.setSearchTime(timeDelay);
		return resultTO;
	}

	public static void processRecordCount(SlaveTO slaveTO, JRSResponse response) {
		if (isSuccess(response)) {
			slaveTO.setRecordSize(response
					.getValueAsInt(RespKey.Storage.REC_COUNT_KEY));
		}
	}

}
