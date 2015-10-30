package com.dastres.master.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dastres.master.domain.SlaveTO;
import com.dastres.master.exception.DAOException;
import com.dastres.master.factory.ServiceFactory;
import com.dastres.util.StringUtil;

public class SlaveManagementDAOImpl implements SlaveManagementDAO {

	private Logger logger = Logger.getLogger(this.getClass());

	private interface TBL_SLAVE {
		String NAME = "slvaves";
		String FLD_ID = "fld_id";
		String FLD_NAME = "fld_name";
		String FLD_ADDRESS = "fld_address";
		String FLD_DISABLE = "fld_disable";
		String FLD_LST_PING = "fld_last_ping_time";
		String FLD_MIN_PING = "fld_min_ping_time";
		String FLD_MAX_PING = "fld_max_ping_time";
		String FLD_SUM_PING = "fld_sum_ping_time";
		String FLD_COUNT_PING = "fld_count_ping";
		String FLD_REC_SIZE = "fld_rec_size";
	}

	@Override
	public List<SlaveTO> list() throws DAOException {
		Connection con = null;
		try {
			con = ServiceFactory.getResourceService().getConnection();
			List<SlaveTO> slaves = new ArrayList<SlaveTO>();
			ResultSet rs = con.prepareStatement(
					"select * from " + TBL_SLAVE.NAME).executeQuery();
			while (rs.next()) {
				SlaveTO slave = new SlaveTO();
				slave.setAddress(rs.getString(TBL_SLAVE.FLD_ADDRESS));
				slave.setPingSum(rs.getLong(TBL_SLAVE.FLD_SUM_PING));
				slave.setDisabled("Y".equalsIgnoreCase(rs
						.getString(TBL_SLAVE.FLD_DISABLE)) ? true : false);
				slave.setId(rs.getInt(TBL_SLAVE.FLD_ID));
				slave.setLastPing(rs.getInt(TBL_SLAVE.FLD_LST_PING));
				slave.setMaxPing(rs.getInt(TBL_SLAVE.FLD_MAX_PING));
				slave.setMinPing(rs.getInt(TBL_SLAVE.FLD_MIN_PING));
				slave.setName(rs.getString(TBL_SLAVE.FLD_NAME));
				slave.setPingCount(rs.getInt(TBL_SLAVE.FLD_COUNT_PING));
				slave.setRecordSize(rs.getInt(TBL_SLAVE.FLD_REC_SIZE));

				slaves.add(slave);
			}
			return slaves;
		} catch (Throwable ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public boolean save(SlaveTO... slaveTOs) throws DAOException {
		boolean result = false;
		Connection con = null;
		try {
			con = ServiceFactory.getResourceService().getConnection();
			Statement st = con.createStatement();
			for (SlaveTO slv : slaveTOs) {

				if (slv.getId() > 0) {
					st.addBatch(makeUpdateQuery(slv));
				} else {
					st.addBatch(makeInsertQuery(slv));
				}
			}
			int recEffect = 0;
			for (int num : st.executeBatch()) {
				recEffect += num;
			}
			result = recEffect == slaveTOs.length;
		} catch (Throwable ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	private String wrapSingleQuote(String value) {
		return "'" + value + "'";
	}

	private String makeInsertQuery(SlaveTO slaveTO) {
		StringBuilder sb = new StringBuilder();
		sb.append("insert into ");
		sb.append(TBL_SLAVE.NAME);
		sb.append("(" + TBL_SLAVE.FLD_NAME + ",");
		sb.append(TBL_SLAVE.FLD_ADDRESS + "," + TBL_SLAVE.FLD_DISABLE);
		sb.append(") values(" + wrapSingleQuote(slaveTO.getName()) + ",");
		sb.append(wrapSingleQuote(slaveTO.getAddress()) + ",");
		sb.append((slaveTO.isDisabled() ? "'Y'" : "'N'") + ")");
		logger.debug("SQL : " + sb.toString());
		return sb.toString();
	}

	private String makeUpdateQuery(SlaveTO slaveTO) {
		StringBuilder sb = new StringBuilder();
		sb.append("update " + TBL_SLAVE.NAME + " set ");

		if (StringUtil.isNotEmpty(slaveTO.getName())) {
			sb.append(TBL_SLAVE.FLD_NAME + "="
					+ wrapSingleQuote(slaveTO.getName()) + ",");
		}

		if (StringUtil.isNotEmpty(slaveTO.getAddress())) {
			sb.append(TBL_SLAVE.FLD_ADDRESS + "="
					+ wrapSingleQuote(slaveTO.getAddress()) + ",");
		}

		sb.append(TBL_SLAVE.FLD_DISABLE + "="
				+ wrapSingleQuote(slaveTO.isDisabled() ? "Y" : "N") + ",");

		sb.append(TBL_SLAVE.FLD_LST_PING + "=" + slaveTO.getLastPing() + ",");
		sb.append(TBL_SLAVE.FLD_MIN_PING + "=" + slaveTO.getMinPing() + ",");
		sb.append(TBL_SLAVE.FLD_MAX_PING + "=" + slaveTO.getMaxPing() + ",");
		sb.append(TBL_SLAVE.FLD_SUM_PING + "=" + slaveTO.getPingSum() + ",");
		sb
				.append(TBL_SLAVE.FLD_COUNT_PING + "=" + slaveTO.getPingCount()
						+ ",");
		sb.append(TBL_SLAVE.FLD_REC_SIZE + "=" + slaveTO.getRecordSize() + ",");

		sb.append(TBL_SLAVE.FLD_ID + "=" + TBL_SLAVE.FLD_ID);

		sb.append(" where " + TBL_SLAVE.FLD_ID + "=" + slaveTO.getId());

		logger.debug("SQL : " + sb.toString());

		return sb.toString();
	}

	@Override
	public boolean remove(int slaveId) throws DAOException {
		boolean result = false;
		Connection con = null;
		try {
			con = ServiceFactory.getResourceService().getConnection();
			int recEffect = con.createStatement().executeUpdate(
					"delete from " + TBL_SLAVE.NAME + " where "
							+ TBL_SLAVE.FLD_ID + "=" + slaveId);
			result = recEffect == 1;
		} catch (Throwable ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	public List<SlaveTO> activeList() throws DAOException {
		Connection con = null;
		try {
			con = ServiceFactory.getResourceService().getConnection();
			List<SlaveTO> slaves = new ArrayList<SlaveTO>();
			ResultSet rs = con.prepareStatement(
					"select * from " + TBL_SLAVE.NAME + " where "
							+ TBL_SLAVE.FLD_DISABLE + "='N'").executeQuery();
			while (rs.next()) {
				SlaveTO slave = new SlaveTO();
				slave.setAddress(rs.getString(TBL_SLAVE.FLD_ADDRESS));
				slave.setPingSum(rs.getLong(TBL_SLAVE.FLD_SUM_PING));
				slave.setDisabled(false);
				slave.setId(rs.getInt(TBL_SLAVE.FLD_ID));
				slave.setLastPing(rs.getInt(TBL_SLAVE.FLD_LST_PING));
				slave.setMaxPing(rs.getInt(TBL_SLAVE.FLD_MAX_PING));
				slave.setMinPing(rs.getInt(TBL_SLAVE.FLD_MIN_PING));
				slave.setName(rs.getString(TBL_SLAVE.FLD_NAME));
				slave.setPingCount(rs.getInt(TBL_SLAVE.FLD_COUNT_PING));
				slave.setRecordSize(rs.getInt(TBL_SLAVE.FLD_REC_SIZE));

				slaves.add(slave);
			}
			return slaves;
		} catch (Throwable ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
