package test.dastres.master;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import com.dastres.master.factory.JRSFactory;
import com.dastres.http.jaxrs.JRSRequest;
import com.dastres.http.jaxrs.JRSResponse;
import com.dastres.master.http.jaxrs.JRSResponseHelper;
import com.dastres.master.rsws.types.input.LocationParamTO;
import com.dastres.master.rsws.types.input.RegionParamTO;
import com.dastres.master.rsws.types.output.FindLocationResultTO;

public class SearchRestServiceTest extends TestCase {

	private Logger logger = Logger.getLogger(this.getClass());

	public void testFindLocation() {
		try {
			String masterAdr = "http://127.0.0.1:8080/Master";
			
			masterAdr = "http://dastres-noroozi.rhcloud.com/app";
			
			logger.debug(masterAdr);
			
			RegionParamTO region = new RegionParamTO();
			region.setLeftUpX("0");
			region.setLeftUpY("0");
			region.setRightDownX("100");
			region.setRightDownY("100");

			LocationParamTO locationParamTO = new LocationParamTO();
			locationParamTO.setKeyword("hotostudio");
			locationParamTO.setRegion(region);

			JRSRequest request = JRSFactory.getFindLocationRequest(
					locationParamTO, masterAdr);
			
			JRSResponse response = JRSFactory.getJWSManager().send(request);
			
			FindLocationResultTO resultTO = JRSResponseHelper
					.processFindLocation(response);
			logger.debug(resultTO.getSearchTime());
			logger.debug(resultTO.getLocationInfos());
			assertNotNull(resultTO);
		} catch (Throwable ex) {
			logger.error(ex.getMessage(), ex);
			fail();
		}
	}
}
