package person.identity;

import java.sql.CallableStatement;
import java.sql.SQLException;

import connection.ConnectToSqlServer;

public class Visitor {
	private String userEmail;
	private String userPwd;

	public String getuserEmail() {
		return userEmail;
	}

	public void setuserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getuserPwd() {
		return userPwd;
	}

	public void setuserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public boolean isTrueMember() throws SQLException {
		ConnectToSqlServer connection = new ConnectToSqlServer();
		CallableStatement callState = connection.getConn().prepareCall("{call checkAccount(?, ?)}");
		callState.setString(1, this.getuserEmail());
		callState.registerOutParameter(2, java.sql.Types.NVARCHAR);
		callState.execute();

		String memberPwd = callState.getString(2);

		callState.close();
		connection.closeConn();

		if (memberPwd == null) {
			return false;
		} else if (!this.getuserPwd().equals(memberPwd)) {
			System.out.println("Your input password is wrong!");
			return false;
		}
		return true;
	}

}
