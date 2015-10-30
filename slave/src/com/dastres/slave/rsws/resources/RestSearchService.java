package com.dastres.slave.rsws.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import com.dastres.config.Constant;
import com.dastres.slave.factory.ServiceFactory;
import com.dastres.slave.rsws.mapper.LocationParamTransfer;
import com.dastres.slave.rsws.types.input.FindLocationParamTO;
import com.dastres.slave.rsws.types.output.FindLocationResultTO;

@Path("/search")
public class RestSearchService {

	private Logger logger = Logger.getLogger(this.getClass());

	@POST
	@Consumes(Constant.MEDIA_TYPE_JSON_UTF8)
	@Produces(Constant.MEDIA_TYPE_JSON_UTF8)
	@Path("/findLocation")
	public FindLocationResultTO findLocation(FindLocationParamTO locParamTO) {
		FindLocationResultTO result = new FindLocationResultTO();
		try {
			result.setLocationInfos(ServiceFactory.getSearchService()
					.findLocation(
							LocationParamTransfer
									.getLocationSearchTO(locParamTO)));
			result.setSuccess(true);
		} catch (Throwable ex) {
			result.setSuccess(false);
			result.setLocationInfos(null);
			logger.error(ex.getMessage(), ex);
		}
		return result;
	}
}
