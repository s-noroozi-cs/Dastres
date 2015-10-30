package com.dastres.slave.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.dastres.slave.exception.DAOException;
import com.dastres.slave.factory.ServiceFactory;
import com.dastres.util.ResourceManagement;

public class TrustedHostDAOImpl implements TrustedHostDAO {

	private interface TBL_TRUST_HOST {
		String NAME = "trust_host";
		String FLD_HOST = "fld_address";
	}

	@Override
	public void save(Set<String> hosts) throws DAOException {
		Connection con = null;
		Statement st = null;
		try {
			String hostParam = "@HOST@";

			String removeSQL = "delete from " + TBL_TRUST_HOST.NAME;

			String insertSQL = "insert into " + TBL_TRUST_HOST.NAME + "("
					+ TBL_TRUST_HOST.FLD_HOST + ") values('" + hostParam + "')";

			con = ServiceFactory.getResourceService().getConnection();

			con.createStatement().execute(removeSQL);

			st = con.createStatement();
			for (String host : hosts) {
				st.addBatch(insertSQL.replaceFirst(hostParam, host));
			}

			st.executeBatch();

		} catch (Throwable ex) {
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			ResourceManagement.releaseResource(st);
			ResourceManagement.releaseResource(con);
		}

	}

	@Override
	public Set<String> list() throws DAOException {
		Connection con = null;
		ResultSet rs = null;
		Set<String> hostSet = new HashSet<String>();

		try {
			String selectSQL = "select " + TBL_TRUST_HOST.NAME + " from "
					+ TBL_TRUST_HOST.NAME;

			con = ServiceFactory.getResourceService().getConnection();

			rs = con.createStatement().executeQuery(selectSQL);

			while (rs.next()) {
				hostSet.add(rs.getString(1));
			}

		} catch (Throwable ex) {
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			ResourceManagement.releaseResource(rs);
			ResourceManagement.releaseResource(con);
		}
		return hostSet;
	}

}
