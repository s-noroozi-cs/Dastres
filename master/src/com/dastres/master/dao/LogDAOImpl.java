package com.dastres.master.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.dastres.master.domain.SearchLogTO;
import com.dastres.master.exception.DAOException;
import com.dastres.master.factory.ServiceFactory;
import com.dastres.util.ResourceManagement;

public class LogDAOImpl implements LogDAO {

	private interface TBL_SEARCH_LOG {
		String NAME = "search_log";
		String FLD_ID = "fld_id";
		String FLD_KEYWORD = "fld_keyword";
		String FLD_REGION = "fld_region";
		String FLD_USER_AGENT = "fld_user_agent";
		String FLD_WAIT_TIME = "fld_wait_time";
		String FLD_DATE = "fld_date";
	}

	@Override
	public void saveSearchLog(SearchLogTO searchLogTO) throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			String insertSQL = "insert into " + TBL_SEARCH_LOG.NAME + "("
					+ TBL_SEARCH_LOG.FLD_KEYWORD + ", "
					+ TBL_SEARCH_LOG.FLD_REGION + ", "
					+ TBL_SEARCH_LOG.FLD_USER_AGENT + ", "
					+ TBL_SEARCH_LOG.FLD_WAIT_TIME + ") values(?,?,?,?)";
			con = ServiceFactory.getResourceService().getConnection();
			ps = con.prepareStatement(insertSQL);
			ps.setObject(1, searchLogTO.getKeyword());
			ps.setObject(2, searchLogTO.getRegion());
			ps.setObject(3, searchLogTO.getUserAgent());
			ps.setObject(4, searchLogTO.getWaitTime());

			ps.execute();
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			ResourceManagement.releaseResource(ps);
			ResourceManagement.releaseResource(con);
		}
	}

	@Override
	public List<SearchLogTO> list(int from, int to, String orderField,
			boolean orderAsc) throws DAOException {
		List<SearchLogTO> list = new ArrayList<SearchLogTO>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			if ("keyword".equalsIgnoreCase(orderField)) {
				orderField = TBL_SEARCH_LOG.FLD_KEYWORD;
			} else if ("waitTime".equalsIgnoreCase(orderField)) {
				orderField = TBL_SEARCH_LOG.FLD_WAIT_TIME;
			} else if ("date".equalsIgnoreCase(orderField)) {
				orderField = TBL_SEARCH_LOG.FLD_DATE;
			} else {
				orderField = TBL_SEARCH_LOG.FLD_ID;
			}

			String selectSQL = "Select * from " + TBL_SEARCH_LOG.NAME
					+ " order by " + orderField
					+ (orderAsc ? " asc " : " desc ") + " limit "
					+ (to - from + 1) + " offset " + from;
			con = ServiceFactory.getResourceService().getConnection();
			ps = con.prepareStatement(selectSQL);

			rs = ps.executeQuery();
			while (rs.next()) {
				SearchLogTO srchLogTO = new SearchLogTO();
				srchLogTO.setId(rs.getInt(TBL_SEARCH_LOG.FLD_ID));
				srchLogTO.setKeyword(rs.getString(TBL_SEARCH_LOG.FLD_KEYWORD));
				srchLogTO.setLogDate(rs.getTimestamp(TBL_SEARCH_LOG.FLD_DATE));
				srchLogTO.setRegion(rs.getString(TBL_SEARCH_LOG.FLD_REGION));
				srchLogTO.setUserAgent(rs
						.getString(TBL_SEARCH_LOG.FLD_USER_AGENT));
				srchLogTO.setWaitTime(rs.getInt(TBL_SEARCH_LOG.FLD_WAIT_TIME));

				list.add(srchLogTO);
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

	@Override
	public int countSearchLog() throws DAOException {
		int size = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String selectSQL = "Select count(*) from " + TBL_SEARCH_LOG.NAME;
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
}
