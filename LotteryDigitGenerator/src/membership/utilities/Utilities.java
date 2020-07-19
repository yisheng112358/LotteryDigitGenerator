package membership.utilities;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import connection.ConnectToSqlServer;
import person.identity.Account;
import person.identity.Member;

public class Utilities {
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

	public static void insertCustomerRecord(String userEmail, String commaSeparatedStr) throws SQLException {
		String sqlstr = String.format(
				"Insert Into BigLotteryCustomerRecord(lotterycustomer, lotterynumbers) Values(\'%s\', \'%s\');",
				userEmail, commaSeparatedStr);
		ConnectToSqlServer connection = new ConnectToSqlServer();
		Statement state = connection.conn.createStatement();
		state.executeUpdate(sqlstr);
		state.close();
		connection.closeConn();
	}

	static public void persistRretrieveNumbers(String commaSeparatedStr) throws SQLException {
		if (!queryRretrieveNumbers(commaSeparatedStr)) {
			insertRretrieveNumbers(commaSeparatedStr);
		}
	}

	private static void insertRretrieveNumbers(String commaSeparatedStr) throws SQLException {
		String sqlstr = String.format("Insert Into BigLotteryRretrieveNumbers(lotterynumbers) Values(\'%s\');",
				commaSeparatedStr);
		ConnectToSqlServer connection = new ConnectToSqlServer();
		Statement state = connection.conn.createStatement();
		state.executeUpdate(sqlstr);
		state.close();
		connection.closeConn();
	}

	static private boolean queryRretrieveNumbers(String commaSeparatedStr) throws SQLException {
		String sqlstr = String.format("Select * From BigLotteryRretrieveNumbers where lotterynumbers=\'%s\';",
				commaSeparatedStr);
		ConnectToSqlServer connection = new ConnectToSqlServer();
		Statement state = connection.conn.createStatement();
		ResultSet rs = state.executeQuery(sqlstr);
		boolean inDb = false;
		inDb = rs.next();
		rs.close();
		state.close();
		connection.closeConn();
		return inDb;
	}

	public static String luckyNumbersToStr(SortedSet<Integer> luckyNumbers) {
		String commaSeparatedStr = "";
		for (int num : luckyNumbers) {
			commaSeparatedStr += String.valueOf(num) + ",";
		}
		return commaSeparatedStr;
	}

	@SuppressWarnings("resource")
	static public int checkTicketNumber() {
		System.out.println("Hi, boss!");
		System.out.println("How many lottery tickets do you want?");
		System.out.println("Please just input number within 99 along: ");
		Matcher m;
		String ticketNumber;
		do {
			ticketNumber = new Scanner(System.in).next();
			Pattern p = Pattern.compile("[0-9]{1,2}");
			m = p.matcher(ticketNumber);
			if (!m.matches()) {
				System.out.println("Please just input number within 99 along: ");
			}

		} while (!m.matches());

		return Integer.valueOf(ticketNumber);
	}

	static public void firstPromt() {
		System.out.println("Press 'I' or 'i' for sign in.");
		System.out.println("Press 'U' or 'u' for sign up.");
		System.out.println("Or press any other key for exit.");
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
	static public Account accountInputPromt() {
		String emailAddress = "";
		while (true) {
			System.out.println("User Email: ");
			emailAddress = new Scanner(System.in).next();
			if (Utilities.verifyEmailFormat(emailAddress)) {
				break;
			} else {
				System.out.println("Invalid email format!");
				System.out.println("Please enter again!");
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
				System.out.println("Please enter again!");
			}
		}
		Account validAccount = new Account(emailAddress, pwd);
		return validAccount;
	}
}
