package membership.registration;

import java.sql.SQLException;
import java.sql.Statement;

import connection.ConnectToSqlServer;
import person.identity.Member;

public class Registration {

	public static void register(Member member) throws SQLException {
		ConnectToSqlServer connection = new ConnectToSqlServer();
		int isVerified = member.isVerified() ? 1 : 0;
		String sqlstr = String.format(
				"Insert Into membership(useremail, userpwd, isverified) Values(\'%s\', \'%s\', \'%d\')",
				member.getuserEmail(), member.getuserPwd(), isVerified);
		Statement state = connection.conn.createStatement();
		int count = state.executeUpdate(sqlstr);
		System.out.printf("Add %d member into the database.\n", count);
		state.close();
		connection.closeConn();
	}

}
