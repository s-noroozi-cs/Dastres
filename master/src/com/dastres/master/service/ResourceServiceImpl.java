package com.dastres.master.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.dastres.master.exception.ServiceException;

public class ResourceServiceImpl implements ResourceService {

	private static Logger logger = Logger.getLogger(ResourceServiceImpl.class);

	private static Properties config = new Properties();

	static {
		reloadConfig();
	}

	private static void reloadConfig() {
		try {
			logger.info("try to load mysql driver");
			Class.forName("com.mysql.jdbc.Driver");
			logger.info("mysql driver has been load successfully");

			logger.info("try to load db_connection properties");
			config
					.load(Thread
							.currentThread()
							.getContextClassLoader()
							.getResourceAsStream(
									"com/dastres/master/config/db_connection.properties"));
			logger.info("db_connection properties has been load successfully");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	@Override
	public Connection getConnection() throws ServiceException {
		try {
			return DriverManager.getConnection(getConnectionURL());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	private String getConnectionURL() {
		return "jdbc:mysql://" + config.getProperty("server.ip") + ":"
				+ config.getProperty("server.port") + "/"
				+ config.getProperty("server.schemaName")
				+ "?characterEncoding=utf8&user="
				+ config.getProperty("server.user") + "&password="
				+ config.getProperty("server.pass");
	}

}
