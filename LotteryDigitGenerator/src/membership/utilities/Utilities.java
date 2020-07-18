package membership.utilities;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import connection.ConnectToSqlServer;

public class Utilities {
	static public void firstPromt() {
		System.out.println("Press 'I' or 'i' for sign in.");
		System.out.println("Press 'U' or 'u' for sign up.");
		System.out.println("Press 'E' or 'e' for exit.");
	}

	static public boolean isAlreadyExist(String emailAddress) throws SQLException {
		ConnectToSqlServer connection = new ConnectToSqlServer();
		CallableStatement callState = connection.conn.prepareCall("{call checkAccount(?, ?)}");
		callState.setString(1, emailAddress);
		callState.registerOutParameter(2, java.sql.Types.NVARCHAR);
		callState.execute();

		String memberPwd = callState.getString(2);

		callState.close();
		connection.closeConn();

		if (memberPwd == null) {
			return false;
		}
		return true;
	}

	static public boolean verifyEmailFormat(String emailAddress) {
		// https://codertw.com/%E7%A8%8B%E5%BC%8F%E8%AA%9E%E8%A8%80/320404/
		String EMAIL_REGEX = "\\p{Alpha}\\w{2,15}[@][a-z0-9]{3,}[.]\\p{Lower}{2,}";
		return emailAddress.matches(EMAIL_REGEX);
	}

	static public boolean verifyPwdFormat(String pwd) {
		Pattern p = Pattern.compile("[a-zA-Z0-9]{8,16}");
		Matcher m = p.matcher(pwd);
		return m.matches();
	}

	@SuppressWarnings("resource")
	static public String[] validAccountFormat() {
		String emailAddress = "";
		while (true) {
			System.out.println("User Email: ");
			emailAddress = new Scanner(System.in).next();
			if (Utilities.verifyEmailFormat(emailAddress)) {
				break;
			} else {
				System.out.println("Invalid email format!");
				System.out.print("Please enter again!");
			}
		}
		String pwd = "";
		while (true) {
			System.out.println("User Password: ");
			pwd = new Scanner(System.in).next();
			if (Utilities.verifyPwdFormat(pwd)) {
				break;
			} else {
				System.out.println("Invalid password format!");
				System.out.print("Please enter again!");
			}
		}
		String[] validAccount = { emailAddress, pwd };
		return validAccount;
	}
}
