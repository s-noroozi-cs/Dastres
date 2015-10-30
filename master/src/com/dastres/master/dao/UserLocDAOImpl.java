package com.dastres.master.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import com.dastres.domain.LocationInfo;
import com.dastres.domain.LocationMoreInfo;
import com.dastres.domain.UserLocationInfo;
import com.dastres.master.exception.DAOException;
import com.dastres.master.factory.ServiceFactory;
import com.dastres.util.JsonUtil;
import com.dastres.util.ResourceManagement;
import com.dastres.util.StringUtil;

public class UserLocDAOImpl implements UserLocDAO {

	private interface TBL_USER_LOC {
		String NAME = "user_loc";
		String FLD_ID = "fld_id";
		String FLD_NAME = "fld_name";
		String FLD_LONGITUDE = "fld_longitude";
		String FLD_LATITUDE = "fld_latitude";
		String FLD_TAGS = "fld_tags";
		String FLD_MORE_INFO = "fld_more_info";
		String FLD_DATE = "fld_date";
		String FLD_ACCEPT = "fld_accept";
	}

	@Override
	public int countUserLoc() throws DAOException {
		int size = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String selectSQL = "Select count(*) from " + TBL_USER_LOC.NAME;
			con = ServiceFactory.getResourceService().getConnection();
			ps = con.prepareStatement(selectSQL);

			rs = ps.executeQuery();
			while (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			ResourceManagement.releaseResource(rs);
			ResourceManagement.releaseResource(ps);
			ResourceManagement.releaseResource(con);
		}
		return size;
	}

	@Override
	public List<UserLocationInfo> list(int from, int to, String orderField,
			boolean orderAsc) throws DAOException {
		List<UserLocationInfo> list = new ArrayList<UserLocationInfo>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			if ("date".equalsIgnoreCase(orderField)) {
				orderField = TBL_USER_LOC.FLD_DATE;
			} else if ("name".equalsIgnoreCase(orderField)) {
				orderField = TBL_USER_LOC.FLD_NAME;
			} else if ("longitude".equalsIgnoreCase(orderField)) {
				orderField = TBL_USER_LOC.FLD_LONGITUDE;
			} else if ("latitude".equalsIgnoreCase(orderField)) {
				orderField = TBL_USER_LOC.FLD_LATITUDE;
			} else if ("tags".equalsIgnoreCase(orderField)) {
				orderField = TBL_USER_LOC.FLD_TAGS;
			} else if ("moreInfo".equalsIgnoreCase(orderField)) {
				orderField = TBL_USER_LOC.FLD_MORE_INFO;
			} else {
				orderField = TBL_USER_LOC.FLD_ID;
			}

			String selectSQL = "Select * from " + TBL_USER_LOC.NAME
					+ " order by " + orderField
					+ (orderAsc ? " asc " : " desc ") + " limit "
					+ (to - from + 1) + " offset " + from;
			con = ServiceFactory.getResourceService().getConnection();
			ps = con.prepareStatement(selectSQL);

			rs = ps.executeQuery();
			while (rs.next()) {
				UserLocationInfo locInfo = new UserLocationInfo();

				locInfo.setId(rs.getInt(TBL_USER_LOC.FLD_ID));
				locInfo.setLatitude(rs.getString(TBL_USER_LOC.FLD_LATITUDE));
				locInfo
						.setLongitude(rs
								.getString(TBL_USER_LOC.FLD_LONGITUDE));
				locInfo.setName(rs.getString(TBL_USER_LOC.FLD_NAME));
				locInfo.setTags(mapTagsField(rs
						.getString(TBL_USER_LOC.FLD_TAGS)));
				locInfo.setMoreInfo(LocationMoreInfo.mapMoreInfoField(rs
						.getString(TBL_USER_LOC.FLD_MORE_INFO)));
				locInfo.setDate(rs.getTimestamp(TBL_USER_LOC.FLD_DATE));
				locInfo.setAccept(rs.getString(TBL_USER_LOC.FLD_ACCEPT));

				list.add(locInfo);
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			ResourceManagement.releaseResource(rs);
			ResourceManagement.releaseResource(ps);
			ResourceManagement.releaseResource(con);
		}
		return list;
	}

	private List<String> mapTagsField(String tags) {
		List<String> result = new ArrayList<String>();
		if (StringUtil.isNotEmpty(tags)) {
			try {
				JSONArray jsArr = new JSONArray(tags);
				for (int i = 0; i < jsArr.length(); i++) {
					result.add(jsArr.getString(i));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		return result;
	}

	private String mapTagsField(List<String> tags) {
		JSONArray result = new JSONArray();
		if (tags != null && tags.size() > 0) {

			for (String tag : tags) {
				result.put(JsonUtil.removeJsonKeywords(tag));
			}

		}
		return result.toString();
	}

	@Override
	public void add(UserLocationInfo... locInfos) throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = ServiceFactory.getResourceService().getConnection();

			String insertSQL = "insert into " + TBL_USER_LOC.NAME + " ("
					+ TBL_USER_LOC.FLD_NAME + "," + TBL_USER_LOC.FLD_LATITUDE
					+ "," + TBL_USER_LOC.FLD_LONGITUDE + ","
					+ TBL_USER_LOC.FLD_MORE_INFO + "," + TBL_USER_LOC.FLD_TAGS
					+ ") values(?,?,?,?,?)";
			ps = con.prepareStatement(insertSQL);

			for (UserLocationInfo to : locInfos) {
				ps.setString(1, to.getName());
				ps.setString(2, to.getLatitude());
				ps.setString(3, to.getLongitude());
				if (to.getMoreInfo() != null) {
					ps.setString(4, to.getMoreInfo().asJson().toString());
				} else {
					ps.setString(4, "");
				}
				ps.setString(5, mapTagsField(to.getTags()));
				ps.addBatch();
			}

			ps.executeBatch();

		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			ResourceManagement.releaseResource(rs);
			ResourceManagement.releaseResource(ps);
			ResourceManagement.releaseResource(con);
		}
	}

	@Override
	public List<LocationInfo> accept(List<Integer> ids) throws DAOException {
		List<LocationInfo> list = new ArrayList<LocationInfo>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			StringBuilder sbIds = new StringBuilder();
			sbIds.append(ids.get(0));
			for (int i = 1; i < ids.size(); i++) {
				sbIds.append("," + ids.get(i));
			}

			String selectSQL = "Select * from " + TBL_USER_LOC.NAME + " where "
					+ TBL_USER_LOC.FLD_ID + " in (" + sbIds.toString() + ")";

			String updateSQL = "update " + TBL_USER_LOC.NAME + " set "
					+ TBL_USER_LOC.FLD_ACCEPT + " ='Y' " + " where "
					+ TBL_USER_LOC.FLD_ID + " in (" + sbIds.toString() + ")";

			con = ServiceFactory.getResourceService().getConnection();
			ps = con.prepareStatement(selectSQL);

			rs = ps.executeQuery();
			while (rs.next()) {
				UserLocationInfo locInfo = new UserLocationInfo();

				locInfo.setId(rs.getInt(TBL_USER_LOC.FLD_ID));
				locInfo.setLatitude(rs.getString(TBL_USER_LOC.FLD_LATITUDE));
				locInfo
						.setLongitude(rs
								.getString(TBL_USER_LOC.FLD_LONGITUDE));
				locInfo.setName(rs.getString(TBL_USER_LOC.FLD_NAME));
				locInfo.setTags(mapTagsField(rs
						.getString(TBL_USER_LOC.FLD_TAGS)));
				locInfo.setMoreInfo(LocationMoreInfo.mapMoreInfoField(rs
						.getString(TBL_USER_LOC.FLD_MORE_INFO)));
				locInfo.setDate(rs.getTimestamp(TBL_USER_LOC.FLD_DATE));

				list.add(locInfo);
			}

			con.createStatement().execute(updateSQL);

		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			ResourceManagement.releaseResource(rs);
			ResourceManagement.releaseResource(ps);
			ResourceManagement.releaseResource(con);
		}
		return list;
	}

	@Override
	public void remove(List<Integer> ids) throws DAOException {
		Connection con = null;
		try {

			StringBuilder sbIds = new StringBuilder();
			sbIds.append(ids.get(0));
			for (int i = 1; i < ids.size(); i++) {
				sbIds.append("," + ids.get(i));
			}

			String deleteSQL = "delete from " + TBL_USER_LOC.NAME + " where "
					+ TBL_USER_LOC.FLD_ID + " in (" + sbIds.toString() + ")";

			con = ServiceFactory.getResourceService().getConnection();
			con.createStatement().execute(deleteSQL);
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			ResourceManagement.releaseResource(con);
		}
	}
}
