package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToMySql {

	public Connection conn;

	public ConnectToMySql() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");			
			String urlstr = "jdbc:mysql://localhost:3306/emailbox?serverTimezone=UTC";			
			String user = "user", pwd = "password";
			conn = DriverManager.getConnection(urlstr, user, pwd);
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
