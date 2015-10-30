package com.dastres.master.rsws.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;

import com.dastres.config.Constant;
import com.dastres.http.jaxrs.JRSRequest;
import com.dastres.http.jaxrs.JRSResponse;
import com.dastres.master.domain.SearchLogTO;
import com.dastres.master.domain.SlaveTO;
import com.dastres.master.factory.JRSFactory;
import com.dastres.master.factory.ServiceFactory;
import com.dastres.master.http.jaxrs.JRSResponseHelper;
import com.dastres.master.rsws.types.input.LocationParamTO;
import com.dastres.master.rsws.types.output.FindLocationResultTO;
import com.dastres.master.thread.FindLocationCallable;

@Path("/search")
public class SearchService {

	@Context
	HttpServletRequest request;

	private Logger logger = Logger.getLogger(this.getClass());

	@POST
	@Consumes(Constant.MEDIA_TYPE_JSON_UTF8)
	@Produces(Constant.MEDIA_TYPE_JSON_UTF8)
	@Path("/findLocation")
	public FindLocationResultTO findLocation(LocationParamTO locParamTO) {
		logger.debug("enter");
		FindLocationResultTO result = new FindLocationResultTO();
		try {
			List<SlaveTO> actSlvs = ServiceFactory.getSlaveManagementService()
					.getActiveSlaveTO();
			List<Future<JRSResponse>> futures = new ArrayList<Future<JRSResponse>>(
					actSlvs.size());
			List<JRSResponse> responses = new ArrayList<JRSResponse>(actSlvs
					.size());

			ExecutorService exeSrv = Executors.newFixedThreadPool(actSlvs
					.size());
			for (int i = 0; i < actSlvs.size(); i++) {
				JRSRequest request = JRSFactory.getFindLocationRequest(
						locParamTO, actSlvs.get(i));
				futures
						.add(i, exeSrv
								.submit(new FindLocationCallable(request)));
			}
			logger.debug("send all request");
			for (int i = 0; i < futures.size(); i++) {
				responses.add(i, futures.get(i).get());
			}
			logger.debug("receive all response");

			exeSrv.shutdown();

			result = JRSResponseHelper.processFindLocation(actSlvs, responses);
			logger.debug("process response");

			result.setSuccess(true);

			logger.debug("make search log");
			 SearchLogTO searchLogTO = new SearchLogTO();
			 searchLogTO.setKeyword(locParamTO.getKeyword());
			 searchLogTO.setRegion(locParamTO.getRegion().toString());
			 searchLogTO.setUserAgent(getUserAgent());
			 searchLogTO.setWaitTime(result.getSearchTime());
			 logger.debug("before call log service");
			 ServiceFactory.getLogService().logSearchServiceCall(searchLogTO);

		} catch (Throwable ex) {
			logger.error(ex.getMessage(), ex);
		}
		return result;
	}

	private String getUserAgent() {
		String userAgent = "User-Agent";
		return request.getHeader(userAgent);
	}
}
