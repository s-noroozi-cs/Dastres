package test.dastres.master;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.dastres.domain.UserLocationInfo;
import com.dastres.domain.LocationMoreInfo;
import com.dastres.http.jaxrs.JRSRequest;
import com.dastres.http.jaxrs.JRSResponse;
import com.dastres.master.factory.JRSFactory;
import com.dastres.master.factory.ServiceFactory;
import com.dastres.master.http.jaxrs.JRSResponseHelper;

import junit.framework.TestCase;

public class UserServiceTest extends TestCase {

	private Logger logger = Logger.getLogger(this.getClass());

	public final void testAddUserLocRestService() {
		String hostAddress = "http://127.0.0.1:8080/Master";
		UserLocationInfo locInfo = new UserLocationInfo();
		LocationMoreInfo moreInfo = new LocationMoreInfo();
		locInfo.setName("name - " + new Date().toString());
		locInfo.setLatitude(Math.random() + "");
		locInfo.setLongitude(Math.random() + "");
		locInfo.addTag("tag-1");
		locInfo.addTag("tag-2");
		
		moreInfo.setNote("note");
		moreInfo.setTel("tel");
		moreInfo.setWeb("web");
		locInfo.setMoreInfo(moreInfo);
		
		
		
		JRSRequest request = JRSFactory.getAddUserLocationRequest(hostAddress,
				locInfo);
		JRSResponse response = JRSFactory.getJWSManager().send(request);

		assertTrue(JRSResponseHelper.isSuccess(response));
		logger.info(response.getTimeDelay());
	}

	public final void testAddUserLocService() {
		try {
			UserLocationInfo userLoc = new UserLocationInfo();
			String uniqueNumber = String.valueOf(System.currentTimeMillis());

			userLoc.setLatitude(uniqueNumber);
			userLoc.setLongitude(uniqueNumber);
			userLoc.setName("test-" + uniqueNumber);
			ServiceFactory.getUserLocService().add(userLoc);

			LocationMoreInfo moreInfo = new LocationMoreInfo();
			moreInfo.setNote("note-" + uniqueNumber);
			moreInfo.setTel("tel-" + uniqueNumber);
			moreInfo.setWeb("web-" + uniqueNumber);
			userLoc.setMoreInfo(moreInfo);
			ServiceFactory.getUserLocService().add(userLoc);

			List<String> tags = new ArrayList<String>();
			tags.add("tag-1" + uniqueNumber);
			tags.add("tag-2" + uniqueNumber);
			tags.add("tag-3" + uniqueNumber);
			userLoc.setTags(tags);
			ServiceFactory.getUserLocService().add(userLoc);
			
			Thread.sleep(1000);

			assertTrue(ServiceFactory.getUserLocService().countUserLoc() > 2);

			List<UserLocationInfo> locInfos = ServiceFactory.getUserLocService()
					.list(0, 10, null, false);

			boolean insLocInfo = false;
			boolean insMoreInfo = false;
			boolean insTags = false;
			
			for (UserLocationInfo locInfo : locInfos) {
				System.out.println("db-name: " + locInfo.getName());
				System.out.println("client-name: " + userLoc.getName());
				if (locInfo.getName().equals(userLoc.getName())) {
					boolean noMoreInfo = locInfo.getMoreInfo() == null;
					boolean noTags = locInfo.getTags() == null
							|| locInfo.getTags().size() == 0;
					
					System.out.println(locInfo.toString());
					System.out.println("noMoreInfo: " + noMoreInfo);
					System.out.println("noTags: " + noTags);
					

					if (noMoreInfo && noTags) {
						insLocInfo = true;
					}

					if (!noMoreInfo && noTags) {
						insMoreInfo = true;
					}

					if (!noMoreInfo && !noTags) {
						insTags = true;
					}

				}
			}

			assertTrue(insLocInfo && insMoreInfo && insTags);

		} catch (Throwable ex) {
			ex.printStackTrace();
			fail();
		}
	}
}
