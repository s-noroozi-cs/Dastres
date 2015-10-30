package com.dastres.slave.rsws.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import com.dastres.config.Constant;
import com.dastres.slave.factory.ServiceFactory;
import com.dastres.slave.rsws.types.input.ReceiptParamTO;
import com.dastres.slave.rsws.types.input.StorageParamTO;
import com.dastres.slave.rsws.types.input.StorageWithReceiptParamTO;
import com.dastres.rsws.types.input.TransferParamTO;
import com.dastres.rsws.types.output.ResultTO;
import com.dastres.slave.rsws.types.output.StorageStatisticTO;
import com.dastres.slave.thread.StorageThread;
import com.dastres.slave.thread.TransferThread;

@Path("/storage")
public class RestStorageService {
	private Logger logger = Logger.getLogger(this.getClass());

	@GET
	@Produces(Constant.MEDIA_TYPE_JSON_UTF8)
	@Path("/count")
	public StorageStatisticTO count() {
		StorageStatisticTO storageStatTO = new StorageStatisticTO();
		try {
			storageStatTO.setRecordCount(ServiceFactory.getStorageService()
					.getRecordCount());
			storageStatTO.setSuccess(true);
		} catch (Throwable ex) {
			logger.error(ex.getMessage(), ex);
			storageStatTO.setSuccess(false);
		}
		return storageStatTO;
	}

	@POST
	@Consumes(Constant.MEDIA_TYPE_JSON_UTF8)
	@Produces(Constant.MEDIA_TYPE_JSON_UTF8)
	@Path("/add")
	public StorageStatisticTO add(StorageParamTO storageTO) {
		StorageStatisticTO result = new StorageStatisticTO();

		try {
			new StorageThread(storageTO).start();
			result.setSuccess(true);
		} catch (Throwable ex) {
			logger.error(ex.getMessage(), ex);
		}
		return result;
	}

	@POST
	@Consumes(Constant.MEDIA_TYPE_JSON_UTF8)
	@Produces(Constant.MEDIA_TYPE_JSON_UTF8)
	@Path("/addWithReceipt")
	public ResultTO addWithReceipt(StorageWithReceiptParamTO storageTO) {
		ResultTO result = new StorageStatisticTO();

		try {
			new StorageThread(storageTO).start();
			result.setSuccess(true);
		} catch (Throwable ex) {
			logger.error(ex.getMessage(), ex);
		}
		return result;
	}

	@POST
	@Consumes(Constant.MEDIA_TYPE_JSON_UTF8)
	@Produces(Constant.MEDIA_TYPE_JSON_UTF8)
	@Path("/transfer")
	public ResultTO transfer(TransferParamTO transferTO) {
		ResultTO result = new ResultTO();
		try {
			new TransferThread(transferTO).start();
			result.setSuccess(true);
		} catch (Throwable ex) {
			logger.error(ex.getMessage(), ex);
		}
		return result;
	}

	@POST
	@Consumes(Constant.MEDIA_TYPE_JSON_UTF8)
	@Produces(Constant.MEDIA_TYPE_JSON_UTF8)
	@Path("/transferReceipt")
	public ResultTO transferReceipt(ReceiptParamTO receiptTO) {
		ResultTO result = new ResultTO();
		try {
			TransferParamTO transferTO = new TransferParamTO();
			transferTO.setTicket(receiptTO.getTicket());

			ServiceFactory.getStorageService().remove(transferTO);

			result.setSuccess(true);
		} catch (Throwable ex) {
			logger.error(ex.getMessage(), ex);
		}
		return result;
	}

}
