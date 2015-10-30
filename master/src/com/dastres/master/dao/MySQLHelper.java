package com.dastres.master.dao;

import java.sql.Connection;
import java.sql.ResultSet;

public abstract class MySQLHelper {
	public static int CallLastInsertFunction(Connection con) throws Exception {
		int retVal = -1;
		String sql = "select last_insert_id()";
		ResultSet rs = con.createStatement().executeQuery(sql);
		if (rs.next()) {
			retVal = rs.getInt(1);
		}
		return retVal;
	}
}
