package framework.utils.database;

import java.sql.Connection;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import framework.utils.parsers.PropertyFileReader;

public class SQLDataBaseConnector {
	private static PropertyFileReader propertiesReader = new PropertyFileReader(
			"/mysql-database.properties");

	private static BoneCP connectionPool = null;
	private static BoneCPConfig config;

	private SQLDataBaseConnector() {
	};

	public static BoneCP getConnectionPool() {
		return connectionPool;
	}

	public static void setConnectionPool(BoneCP connectionPool) {
		SQLDataBaseConnector.connectionPool = connectionPool;
	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = getConnectionPool().getConnection();
			// will get a thread-safe connection from the BoneCP connection
			// pool.
			// synchronization of the method will be done inside BoneCP source
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void setConnectionProperties() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			config = new BoneCPConfig();

			config.setJdbcUrl("jdbc:mysql://"
					+ propertiesReader.getPropertyValue("dbServer") + ":"
					+ propertiesReader.getPropertyValue("dbport") + "/"
					+ propertiesReader.getPropertyValue("database"));
			config.setUsername(propertiesReader.getPropertyValue("dbUser"));
			config.setPassword(propertiesReader.getPropertyValue("dbpassword"));
			config.setMinConnectionsPerPartition(5); // if you say 5 here, there
														// will be 10 connection
														// available
														// config.setMaxConnectionsPerPartition(10);
			config.setPartitionCount(2);
			connectionPool = new BoneCP(config);
			SQLDataBaseConnector.setConnectionPool(connectionPool);

		} catch (ClassNotFoundException cnfe) {
			throw new RuntimeException(cnfe);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void shutdownConnPool() {
		try {
			BoneCP connectionPool = SQLDataBaseConnector.getConnectionPool();
			if (connectionPool != null) {
				connectionPool.shutdown(); // this method must be called only
											// once when the application stops.

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
