package com.dastres.master.rsws.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.dastres.config.Constant;
import com.dastres.master.rsws.types.input.UserLocationParamTO;
import com.dastres.master.thread.UserLocationThread;
import com.dastres.rsws.types.output.ResultTO;
import com.dastres.util.StringUtil;

@Path("/user")
public class UserService {

	@POST
	@Consumes(Constant.MEDIA_TYPE_JSON_UTF8)
	@Produces(Constant.MEDIA_TYPE_JSON_UTF8)
	@Path("/addLocation")
	public ResultTO addLocation(UserLocationParamTO userLocParamTO) {
		ResultTO resultTO = new ResultTO();
		String validationMessage = userLocParamTO.checkValidation();
		if (StringUtil.isEmpty(validationMessage)) {
			new UserLocationThread(userLocParamTO).start();
			resultTO.setSuccess(true);
		} else {
			resultTO.setErrorMsg(validationMessage);
			resultTO.setSuccess(false);
		}
		return resultTO;
	}

}
