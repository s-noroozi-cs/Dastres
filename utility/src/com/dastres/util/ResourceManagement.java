package com.dastres.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class ResourceManagement {


	public static void releaseResource(PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	public static void releaseResource(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	public static void releaseResource(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void releaseResource(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

}
