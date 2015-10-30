package test.dastres.master;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.dastres.domain.LocationInfo;
import com.dastres.master.domain.SlaveTO;
import com.dastres.master.factory.JRSFactory;
import com.dastres.http.jaxrs.JRSRequest;
import com.dastres.http.jaxrs.JRSResponse;
import com.dastres.master.http.jaxrs.JRSResponseHelper;
import com.dastres.master.rsws.types.input.LocationParamTO;
import com.dastres.master.rsws.types.input.RegionParamTO;

public class SlaveServiceTest extends TestCase {


	public final void testPingService() {
		SlaveTO slaveTO = new SlaveTO();
		slaveTO.setAddress("http://localhost:8080/Slave");
		JRSRequest requet = JRSFactory.getPingTimeRequest(slaveTO);
		JRSResponse response = JRSFactory.getJWSManager().send(requet);
		assertTrue(JRSResponseHelper.isSuccess(response));
	}

	public final void testStogateCountService() {
		SlaveTO slaveTO = new SlaveTO();
		slaveTO.setAddress("http://localhost:8080/Slave");
		JRSRequest requet = JRSFactory.getRecordCountRequest(slaveTO);
		JRSResponse response = JRSFactory.getJWSManager().send(requet);
		assertTrue(JRSResponseHelper.isSuccess(response));
	}

	public final void testSearchServiceFindLocation() {

		LocationParamTO locationParamTO = new LocationParamTO();

		RegionParamTO region = new RegionParamTO();
		region.setLeftUpX("0");
		region.setLeftUpY("0");
		region.setRightDownX("1");
		region.setRightDownY("1");

		locationParamTO.setKeyword("keyword");
		locationParamTO.setRegion(region);

		SlaveTO slaveTO = new SlaveTO();
		slaveTO.setAddress("http://localhost:8080/Slave");
		JRSRequest requet = JRSFactory.getFindLocationRequest(locationParamTO,
				slaveTO);
		JRSResponse response = JRSFactory.getJWSManager().send(requet);
		assertTrue(JRSResponseHelper.isSuccess(response));
	}

	public final void testStogateServiceAdd() {

		List<LocationInfo> locInfos = new ArrayList<LocationInfo>();
		LocationInfo locInfo = new LocationInfo();
		locInfo.setLatitude("2");
		locInfo.setLongitude("2");
		locInfo.setName("name-2");
		locInfo.addTag("1");
		locInfo.addTag("2");
		locInfo.addTag("3");

		locInfos.add(locInfo);

		SlaveTO slaveTO = new SlaveTO();
		slaveTO.setAddress("http://localhost:8080/Slave");
		JRSRequest requet = JRSFactory.getSendStorageDataRequest(locInfos,
				slaveTO);
		JRSResponse response = JRSFactory.getJWSManager().send(requet);
		assertTrue(JRSResponseHelper.isSuccess(response));

	}
}
