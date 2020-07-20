package membership.verification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connection.ConnectToMySql;
import person.identity.Member;

public class ThirdpartyVerification {
	public static void verify(Member member) throws SQLException {
		ConnectToMySql connection = new ConnectToMySql();
		Statement state = connection.getConn().createStatement();
		String sqlstr = String.format("Select * From emails Where emailaddress=\'%s\';", member.getUserEmail());
		ResultSet rs = state.executeQuery(sqlstr);
		boolean userStatus = rs.next();
		rs.close();
		state.close();
		connection.closeConn();
		member.setVerified(userStatus);
	}
}
