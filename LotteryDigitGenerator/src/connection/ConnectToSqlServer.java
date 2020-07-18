package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToSqlServer {

	public Connection conn;

	public ConnectToSqlServer() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String urlstr = "jdbc:sqlserver://localhost:1433;databaseName=membership;user=user;password=password";
			conn = DriverManager.getConnection(urlstr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeConn() throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}
}
