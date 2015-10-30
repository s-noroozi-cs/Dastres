package com.dastres.master.thread;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.dastres.master.comparator.RecordCountOrderComparator;
import com.dastres.master.domain.ImportDataInfo;
import com.dastres.domain.LocationInfo;
import com.dastres.master.domain.SlaveTO;
import com.dastres.master.exception.DataException;
import com.dastres.master.exception.ServiceException;
import com.dastres.master.factory.JRSFactory;
import com.dastres.master.factory.ServiceFactory;
import com.dastres.http.jaxrs.JRSResponse;
import com.dastres.master.http.jaxrs.JRSResponseHelper;
import com.dastres.util.CollectionUtil;

public class ImportDataThread extends Thread {

	private Logger logger = Logger.getLogger(this.getClass());

	private final ImportDataInfo importDataInfo;

	public ImportDataThread(ImportDataInfo importDataInfo) {
		super.setPriority(Thread.MIN_PRIORITY);
		this.importDataInfo = importDataInfo;
	}

	public void run() {
		try {

			List<LocationInfo> dataList = importDataInfo.getLocationInfos();

			List<SlaveTO> slaveList = ServiceFactory
					.getSlaveManagementService().getActiveSlaveTO();

			for (int i = 0; i < slaveList.size(); i++) {
				JRSResponse response = JRSFactory.getJWSManager().send(
						JRSFactory.getRecordCountRequest(slaveList.get(i)));
				JRSResponseHelper
						.processRecordCount(slaveList.get(i), response);
			}

			ServiceFactory.getSlaveManagementService().setSlaveTO(
					slaveList.toArray(new SlaveTO[slaveList.size()]));

			Collections.sort(slaveList, new RecordCountOrderComparator(true));

			if (CollectionUtil.isNotEmpty(dataList)) {
				logger.info("data list size: " + dataList.size());

				if (CollectionUtil.isNotEmpty(slaveList)) {
					logger.info("active slave list size: " + slaveList.size());

					int dataCatSize = dataList.size() / slaveList.size();

					int catCount = dataList.size() / dataCatSize;
					int remData = dataList.size() % dataCatSize;
					int curSlaveIdx = 0;

					List<LocationInfo> dataSubList = null;
					SlaveTO slaveTO = null;

					for (int i = 0; i < catCount; i++) {
						logger.info("current slave idx: " + curSlaveIdx);

						dataSubList = dataList.subList(i * dataCatSize, (i + 1)
								* dataCatSize);

						slaveTO = slaveList.get(curSlaveIdx % slaveList.size());
						logger.info(slaveTO);

						new SendDataThread(dataSubList, slaveTO).start();

						curSlaveIdx++;
						curSlaveIdx %= slaveList.size();
					}

					slaveTO = slaveList.get(curSlaveIdx);
					logger.info(slaveTO);

					dataSubList = dataList.subList(catCount * dataCatSize,
							catCount * dataCatSize + remData);
					logger.info("rem sub list size: " + dataSubList.size());

					new SendDataThread(dataSubList, slaveTO).start();

				} else {
					logger.error("active slave machine list is empty");
				}
			} else {
				logger.error("data list is empty");
			}

		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
		} catch (DataException e) {
			logger.error(e.getMessage(), e);
		} finally {
			
		}

	}
}
