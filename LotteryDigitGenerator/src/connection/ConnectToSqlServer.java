package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToSqlServer {

	private Connection conn;

	public ConnectToSqlServer() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String urlstr = "jdbc:sqlserver://localhost:1433;databaseName=master;user=sa;password=P@ssw0rd";
			conn = DriverManager.getConnection(urlstr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConn() {
		return conn;
	}

	public void closeConn() throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}
}
