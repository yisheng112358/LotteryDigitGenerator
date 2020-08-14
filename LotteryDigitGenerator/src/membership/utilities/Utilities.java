package membership.utilities;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import connection.ConnectToSqlServer;
import person.identity.Account;
import person.identity.Member;
import person.identity.dao.MembershipDao;
import person.identity.dao.MembershipDaoFactory;

public class Utilities {
	public static void register(Member member) throws SQLException {
		MembershipDao mDao = MembershipDaoFactory.createMemberDao();
		mDao.createConn();
		mDao.add(member);
		mDao.closeConn();
	}

	public static void insertCustomerRecord(String userEmail, ArrayList<String> customerRecords) throws SQLException {
		String sqlstr = "Insert Into BigLotteryCustomerRecord(lotterycustomer, lotterynumbers) Values(?, ?);";
		ConnectToSqlServer connection = new ConnectToSqlServer();
		PreparedStatement preState = connection.getConn().prepareStatement(sqlstr);
		for (String customerRecord : customerRecords) {
			preState.setString(1, userEmail);
			preState.setString(2, customerRecord);
			preState.addBatch();
		}
		preState.executeBatch();
		preState.close();
		connection.closeConn();
	}

	static public void persistRretrieveNumbers(String commaSeparatedStr) throws SQLException {
		if (!queryRretrieveNumbers(commaSeparatedStr)) {
			insertRretrieveNumbers(commaSeparatedStr);
		}
	}

	private static void insertRretrieveNumbers(String commaSeparatedStr) throws SQLException {
		String sqlstr = "Insert Into BigLotteryRretrieveNumbers(lotterynumbers) Values(?);";
		ConnectToSqlServer connection = new ConnectToSqlServer();
		PreparedStatement preState = connection.getConn().prepareStatement(sqlstr);
		preState.setString(1, commaSeparatedStr);
		preState.execute();
		preState.close();
		connection.closeConn();
	}

	static private boolean queryRretrieveNumbers(String commaSeparatedStr) throws SQLException {
		String sqlstr = "Select * From BigLotteryRretrieveNumbers where lotterynumbers = ?;";
		ConnectToSqlServer connection = new ConnectToSqlServer();
		PreparedStatement preState = connection.getConn().prepareStatement(sqlstr);
		preState.setString(1, commaSeparatedStr);
		ResultSet rs = preState.executeQuery();
		boolean inDb = false;
		inDb = rs.next();
		rs.close();
		preState.close();
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
		System.out.println("------------------------------------------");
		System.out.println("Press 'I' or 'i' for sign in.");
		System.out.println("Press 'U' or 'u' for sign up.");
		System.out.println("Or enter any other character key for exit.");
		System.out.println("------------------------------------------");
	}

	static public boolean isEmailAlreadyExist(String emailAddress) throws SQLException {
		ConnectToSqlServer connection = new ConnectToSqlServer();
		CallableStatement callState = connection.getConn().prepareCall("{call checkAccount(?, ?)}");
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
				System.out.println("Press 'E' or 'e' for exit or press other keys for the input again!");
				if (new Scanner(System.in).next().equalsIgnoreCase("E")) {
					return null;
				}
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
				System.out.println("Press 'E' or 'e' for exit or press other keys for the input again!");
				if (new Scanner(System.in).next().equalsIgnoreCase("E")) {
					return null;
				}
			}
		}
		Account validAccount = new Account(emailAddress, pwd);
		return validAccount;
	}
}
