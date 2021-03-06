package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToMySql {

	private Connection conn;

	public ConnectToMySql() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String urlstr = "jdbc:mysql://localhost:3306/emailbox?serverTimezone=UTC";
			String user = "user", pwd = "P@ssw0rd";
			conn = DriverManager.getConnection(urlstr, user, pwd);
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
