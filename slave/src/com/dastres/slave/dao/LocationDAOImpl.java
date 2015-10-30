package com.dastres.slave.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dastres.domain.LocationInfo;
import com.dastres.domain.LocationMoreInfo;
import com.dastres.slave.domain.LocationSearchTO;
import com.dastres.slave.exception.DAOException;
import com.dastres.slave.factory.ServiceFactory;
import com.dastres.rsws.types.input.TransferParamTO;
import com.dastres.util.ResourceManagement;

public class LocationDAOImpl implements LocationDAO {

	private Logger logger = Logger.getLogger(this.getClass());

	private interface DB_GEN {
		String FLD_ID = "fld_id";
	}

	private interface TBL_LOC_INF extends DB_GEN {
		String NAME = "loc_info";
		String FLD_POINT = "fld_point";
		String FLD_NAME = "fld_name";
		String FLD_TRNS_TICKET = "fld_trns_ticket";
		String FLD_MORE_INFO = "fld_more_info";
	}

	private interface TBL_LOC_TAG extends DB_GEN {
		String NAME = "loc_tag";
		String FLD_LOC_ID = "fld_loc_id";
		String FLD_TAG = "fld_tag";
	}

	@Override
	public void add(List<LocationInfo> locInfos) throws DAOException {
		Connection con = null;
		try {
			con = ServiceFactory.getResourceService().getConnection();
			PreparedStatement ps = con.prepareStatement("insert into "
					+ TBL_LOC_INF.NAME + " (" + TBL_LOC_INF.FLD_POINT + ","
					+ TBL_LOC_INF.FLD_NAME + "," + TBL_LOC_INF.FLD_MORE_INFO
					+ ") values (GeomFromText(?),?,?)");

			for (int i = 0; i < locInfos.size(); i++) {
				ps.clearParameters();

				ps.setString(1, "POINT(" + locInfos.get(i).getLongitude()
						+ " " + locInfos.get(i).getLatitude() + ")");
				ps.setString(2, locInfos.get(i).getName());
				if (locInfos.get(i).getMoreInfo() != null) {
					ps.setString(3, locInfos.get(i).getMoreInfo().asJson()
							.toString());
				} else {
					ps.setString(3, "");
				}
				ps.execute();

				locInfos.get(i).setId(MySQLHelper.CallLastInsertFunction(con));
			}
			ps.close();

			Statement st = con.createStatement();
			String LOC_ID = "@LOC_ID";
			String LOC_TAG = "@LOC_TAG";

			String tagSQL = "insert into " + TBL_LOC_TAG.NAME + "("
					+ TBL_LOC_TAG.FLD_LOC_ID + "," + TBL_LOC_TAG.FLD_TAG + ")"
					+ "values(" + LOC_ID + ",'" + LOC_TAG + "')";

			boolean needExecute = false;
			for (LocationInfo locInfo : locInfos) {
				if (locInfo.getTags() != null) {
					for (String tag : locInfo.getTags()) {
						needExecute = true;
						st.addBatch(tagSQL.replaceAll(LOC_ID,
								locInfo.getId() + "").replaceAll(LOC_TAG, tag));
					}
				}
			}
			if (needExecute) {
				st.executeBatch();
			}

		} catch (Throwable ex) {
			logger.error(ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			ResourceManagement.releaseResource(con);
		}
	}

	@Override
	public int countRecord() throws DAOException {
		Connection con = null;
		ResultSet rs = null;
		try {
			con = ServiceFactory.getResourceService().getConnection();
			rs = con.createStatement().executeQuery(
					"select count(*) from " + TBL_LOC_INF.NAME);
			if (rs.next()) {
				return rs.getInt(1);
			} else {
				return -1;
			}

		} catch (Throwable ex) {
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			ResourceManagement.releaseResource(rs);
			ResourceManagement.releaseResource(con);
		}
	}

	private String makeLike(String criteria) {
		return "%".concat(criteria).concat("%");
	}

	private void setPoint(String point, LocationInfo locInfo) {
		point = point.substring("POINT(".length()).replace(')', ' ').trim();
		String[] points = point.split(" ");
		locInfo.setLongitude(points[0]);
		locInfo.setLatitude(points[1]);
	}

	@Override
	public List<LocationInfo> find(LocationSearchTO locSearchTO)
			throws DAOException {
		Connection con = null;
		ResultSet rs = null;
		try {
			List<LocationInfo> result = new ArrayList<LocationInfo>();

			String selectClause = "select t1." + TBL_LOC_INF.FLD_ID
					+ " ,asText(t1." + TBL_LOC_INF.FLD_POINT + "),t1."
					+ TBL_LOC_INF.FLD_NAME + ", t1."
					+ TBL_LOC_INF.FLD_MORE_INFO;
			String fromClause = " from " + TBL_LOC_INF.NAME
					+ " t1 left outer join " + TBL_LOC_TAG.NAME + " t2 on t1."
					+ TBL_LOC_INF.FLD_ID + "=t2." + TBL_LOC_TAG.FLD_LOC_ID;
			String whereClause = " where MBRContains(GeomFromText( 'LINESTRING("
					+ locSearchTO.getRegion().getLeftUpX()
					+ " "
					+ locSearchTO.getRegion().getLeftUpY()
					+ ","
					+ locSearchTO.getRegion().getRightDownX()
					+ " "
					+ locSearchTO.getRegion().getRightDownY()
					+ ")' ),t1."
					+ TBL_LOC_INF.FLD_POINT
					+ ") and (t1."
					+ TBL_LOC_INF.FLD_NAME
					+ " like ? or t2."
					+ TBL_LOC_TAG.FLD_TAG + " like ?) ";

			String pagingClause = " limit 10 offset 0 ";

			String query = selectClause + fromClause + whereClause
					+ pagingClause;

			logger.info("query: " + query);

			con = ServiceFactory.getResourceService().getConnection();
			PreparedStatement ps = con.prepareStatement(query);

			ps.setString(1, makeLike(locSearchTO.getKeyword()));
			ps.setString(2, makeLike(locSearchTO.getKeyword()));

			rs = ps.executeQuery();

			while (rs.next()) {
				LocationInfo rec = new LocationInfo();

				rec.setId(rs.getInt(1));
				setPoint(rs.getString(2), rec);
				rec.setName(rs.getString(3));
				rec.setMoreInfo(LocationMoreInfo.mapMoreInfoField(rs
						.getString(4)));
				result.add(rec);
			}

			return result;

		} catch (Throwable ex) {
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			ResourceManagement.releaseResource(rs);
			ResourceManagement.releaseResource(con);
		}
	}

	@Override
	public void remove(TransferParamTO transferTO) throws DAOException {
		Connection con = null;
		try {
			con = ServiceFactory.getResourceService().getConnection();

			String removeSQL = "delete from " + TBL_LOC_INF.NAME + " where "
					+ TBL_LOC_INF.FLD_TRNS_TICKET + "=?";

			String removeOrphanTags = "delete from " + TBL_LOC_TAG.NAME
					+ " where " + TBL_LOC_TAG.FLD_LOC_ID + " not in (select "
					+ TBL_LOC_INF.FLD_ID + " from " + TBL_LOC_INF.NAME + ")";

			PreparedStatement ps = con.prepareStatement(removeSQL);
			ps.setString(1, transferTO.getTicket());
			ps.execute();

			con.createStatement().execute(removeOrphanTags);

		} catch (Throwable ex) {
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			ResourceManagement.releaseResource(con);
		}
	}

	@Override
	public Collection<LocationInfo> list(TransferParamTO transferTO)
			throws DAOException {
		Map<Integer, LocationInfo> locInfoMap = new HashMap<Integer, LocationInfo>();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Statement st = null;
		try {

			final String TICKET_PARAM = "@TICKET@";
			final String LOC_ID_PARAM = "@LOC_ID@";

			String setTransferTicketSQL = "update " + TBL_LOC_INF.NAME
					+ " set " + TBL_LOC_INF.FLD_TRNS_TICKET + "='"
					+ TICKET_PARAM + "' where " + TBL_LOC_INF.FLD_ID + "="
					+ LOC_ID_PARAM;

			String selectClause = "select t1." + TBL_LOC_INF.FLD_ID
					+ " ,asText(t1." + TBL_LOC_INF.FLD_POINT + "),t1."
					+ TBL_LOC_INF.FLD_NAME + ", t1."
					+ TBL_LOC_INF.FLD_MORE_INFO + ", t2." + TBL_LOC_TAG.FLD_TAG;
			String fromClause = " from " + TBL_LOC_INF.NAME
					+ " t1 left outer join " + TBL_LOC_TAG.NAME + " t2 on t1."
					+ TBL_LOC_INF.FLD_ID + "=t2." + TBL_LOC_TAG.FLD_LOC_ID;

			String pagingClause = " limit " + transferTO.getRecordCount() * 2
					+ " offset 0 ";

			String query = selectClause + fromClause + pagingClause;

			logger.info("query: " + query);

			con = ServiceFactory.getResourceService().getConnection();
			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			while (rs.next()) {
				int locInfoId = rs.getInt(1);

				LocationInfo locInfo = locInfoMap.get(locInfoId);
				if (locInfo == null) {
					locInfo = new LocationInfo();

					locInfo.setId(locInfoId);
					setPoint(rs.getString(2), locInfo);
					locInfo.setName(rs.getString(3));
					locInfo.setMoreInfo(LocationMoreInfo.mapMoreInfoField(rs
							.getString(4)));
					locInfo.addTag(rs.getString(5));

				} else {
					locInfo.addTag(rs.getString(5));
				}

				locInfoMap.put(locInfoId, locInfo);
				if (locInfoMap.size() > transferTO.getRecordCount()) {
					break;
				}
			}

			st = con.createStatement();
			for (LocationInfo locInfo : locInfoMap.values()) {
				st.addBatch(setTransferTicketSQL.replaceFirst(TICKET_PARAM,
						transferTO.getTicket()).replaceFirst(LOC_ID_PARAM,
						locInfo.getId() + ""));
			}
			st.executeBatch();

		} catch (Throwable ex) {
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			ResourceManagement.releaseResource(st);
			ResourceManagement.releaseResource(ps);
			ResourceManagement.releaseResource(rs);
			ResourceManagement.releaseResource(con);
		}
		return locInfoMap.values();
	}
}
