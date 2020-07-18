package membership.verification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connection.ConnectToMySql;
import person.identity.Member;

public class ThirdpartyVerification {
	public void verify(Member member) throws SQLException {
		ConnectToMySql connection = new ConnectToMySql();
		Statement state = connection.conn.createStatement();
		String sqlstr = String.format("Select * From emails Where emailaddress=\'%s\';", member.getuserEmail());
		ResultSet rs = state.executeQuery(sqlstr);
		boolean userStatus = rs.next();
		state.close();
		connection.closeConn();
		member.setVerified(userStatus);
	}
}
