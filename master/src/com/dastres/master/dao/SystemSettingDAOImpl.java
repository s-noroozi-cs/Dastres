package com.dastres.master.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.dastres.master.domain.ConfigTO;
import com.dastres.master.factory.ServiceFactory;

public class SystemSettingDAOImpl implements SystemSettingDAO {

	private Logger logger = Logger.getLogger(this.getClass());

	private interface TBL_CFG_SYS {
		String NAME = "cfg_sys";
		String FLD_ID = "fld_id";
		String FLD_NAME = "fld_name";
		String FLD_VALUE = "fld_value";
	}

	@Override
	public ConfigTO find(ConfigTO configTO) {
		Connection con = null;
		try {
			con = ServiceFactory.getResourceService().getConnection();
			String sql = "select * from " + TBL_CFG_SYS.NAME + " where "
					+ TBL_CFG_SYS.FLD_NAME + " = ? ";

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, configTO.getName());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				configTO.setId(rs.getInt(TBL_CFG_SYS.FLD_ID));
				configTO.setName(rs.getString(TBL_CFG_SYS.FLD_NAME));
				configTO.setValue(rs.getString(TBL_CFG_SYS.FLD_VALUE));
				return configTO;
			}
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
	public Set<ConfigTO> list() {
		Connection con = null;
		try {
			con = ServiceFactory.getResourceService().getConnection();
			Set<ConfigTO> cfgSet = new HashSet<ConfigTO>();
			String sql = "select * from " + TBL_CFG_SYS.NAME;

			PreparedStatement ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ConfigTO configTO = new ConfigTO();
				configTO.setId(rs.getInt(TBL_CFG_SYS.FLD_ID));
				configTO.setName(rs.getString(TBL_CFG_SYS.FLD_NAME));
				configTO.setValue(rs.getString(TBL_CFG_SYS.FLD_VALUE));
				cfgSet.add(configTO);
			}
			return cfgSet;
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
	public ConfigTO save(ConfigTO configTO) {
		Connection con = null;
		try {
			con = ServiceFactory.getResourceService().getConnection();
			String sql = "";
			boolean exist = find((ConfigTO) configTO.clone()) != null;
			if (!exist) {
				sql = "insert into " + TBL_CFG_SYS.NAME + " ("
						+ TBL_CFG_SYS.FLD_NAME + ", " + TBL_CFG_SYS.FLD_VALUE
						+ ") values(?,?)";
			} else {

				sql = "update " + TBL_CFG_SYS.NAME + " set "
						+ TBL_CFG_SYS.FLD_VALUE + " = ? where "
						+ TBL_CFG_SYS.FLD_NAME + " = ? ";
			}

			logger.debug("SQL: " + sql);

			PreparedStatement ps = con.prepareStatement(sql);
			logger.debug(configTO);
			if (!exist) {
				ps.setString(1, configTO.getName());
				ps.setString(2, configTO.getValue());
			} else {
				ps.setString(1, configTO.getValue());
				ps.setString(2, configTO.getName());
			}
			int rowEffect = ps.executeUpdate();
			logger.debug("row effect: " + rowEffect);
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
