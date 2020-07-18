package hmi;

import java.sql.SQLException;
import java.util.Scanner;

import connection.ConnectToMySql;
import membership.registration.Registration;
import membership.utilities.Utilities;
import membership.verification.ThirdpartyVerification;
import person.identity.Member;
import person.identity.Visitor;

public class LogIn {
	public static void main(String[] args) throws SQLException {

		while (true) {
			Utilities.firstPromt();
			@SuppressWarnings("resource")
			String firstInput = new Scanner(System.in).next();

			if (firstInput.equalsIgnoreCase("I")) {
				// sign in
				Visitor visitor = new Visitor();
				String[] validAccount = Utilities.validAccountFormat();
				visitor.setuserEmail(validAccount[0]);
				visitor.setuserPwd(validAccount[1]);

				if (visitor.isTrueMember()) {
					System.out.println("Hi, welcome!");
					System.out.println("We will give you 6 magic number~~~");
					System.out.println("------------------------------------------");
					System.out.println("Under construction...");
					System.out.println("------------------------------------------");
				} else {
					System.out.println("You are not a member yet.");
				}

			} else if (firstInput.equalsIgnoreCase("U")) {
				// sign up
				Member member = new Member();
				System.out.println("The length of the password should be at least 8 but no more that 16.");
				System.out.println("The password should include numbers and letters");
				String[] validAccount = Utilities.validAccountFormat();
				// check the email is in the local database independently
				if (Utilities.isAlreadyExist(validAccount[0])) {
					System.out.println("The email is already exist.");
				} else {
					member.setuserEmail(validAccount[0]);
					member.setuserPwd(validAccount[1]);

					// verification from other database
					ThirdpartyVerification verification = new ThirdpartyVerification();
					verification.verify(member);

					// registration to the local database
					Registration regist = new Registration(member);
					regist.register();

					// check the email is in the local database independently
					if (Utilities.isAlreadyExist(member.getuserEmail())) {
						System.out.println("You may login again.");
					} else {
						System.out.println("The sign up is fail, you need to sign up again!");
					}
					
				}

			} else if (firstInput.equalsIgnoreCase("E")) {
				// exit
				break;

			} else if (firstInput.equalsIgnoreCase("t")) {
				// test
				ConnectToMySql connection = new ConnectToMySql();
				connection.closeConn();
				break;

			} else {
				System.out.println("Please input 'I', 'U' or 'E'.");
			}
		}

		System.out.println("See you next time~~~");
	}
}
