package com.dastres.slave.rsws.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.dastres.config.Constant;
import com.dastres.slave.rsws.types.output.PingResponseTO;

@Path("/general")
public class RestGeneralService {

	@GET
	@Produces(Constant.MEDIA_TYPE_JSON_UTF8)
	@Path("/ping")
	public PingResponseTO ping() {
		PingResponseTO pingResp = new PingResponseTO();
		pingResp.setSuccess(true);
		return pingResp;
	}

}
