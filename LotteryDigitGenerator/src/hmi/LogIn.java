package hmi;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;

import lotterynumbers.BigLotteryNumberGenerator;
import membership.registration.Registration;
import membership.utilities.Utilities;
import membership.verification.ThirdpartyVerification;
import person.identity.Account;
import person.identity.Member;
import person.identity.Visitor;

public class LogIn {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws SQLException {

		while (true) {
			Utilities.firstPromt();
			String firstInput = new Scanner(System.in).next();

			if (firstInput.equalsIgnoreCase("I")) {
				// sign in
				Visitor visitor = new Visitor();
				Account validAccount = Utilities.accountInputPromt();
				visitor.setuserEmail(validAccount.getUserEmail());
				visitor.setuserPwd(validAccount.getUserPassword());

				if (visitor.isTrueMember()) {
					boolean isContinue = false;
					do {
						int checkedTicketNumber = Utilities.checkTicketNumber();
						System.out.println("------------------------------------------");

						// pick randomness by set
						for (SortedSet<Integer> luckyNumberSet : BigLotteryNumberGenerator
								.luckyNumberSetsGenerator(checkedTicketNumber)) {
							System.out.println(luckyNumberSet);
						}

//						// pick randomness by number
//						for (SortedSet<Integer> luckyNumberSet : BigLotteryNumberGeneratorSimple
//								.LuckyNumberSetGenerator(checkedTicketNumber)) {
//							System.out.println(luckyNumberSet);
//						}

						System.out.println("------------------------------------------");
						System.out.println("Press 'C' or 'c' for continue or any other key to go to the last level.");
						isContinue = new Scanner(System.in).next().equalsIgnoreCase("C");
					} while (isContinue);

				} else {
					System.out.println("You are not a member yet.");
				}

			} else if (firstInput.equalsIgnoreCase("U")) {
				// sign up
				Member member = new Member();
				System.out.println("The length of the password should be at least 8 but no more that 16.");
				System.out.println("The password should include numbers and letters");
				Account validAccount = Utilities.accountInputPromt();
				// check the email is in the local database independently
				if (Utilities.isAlreadyExist(validAccount.getUserEmail())) {
					System.out.println("The email is already exist.");
				} else {
					member.setuserEmail(validAccount.getUserEmail());
					member.setuserPwd(validAccount.getUserPassword());

					// verification from other database
					ThirdpartyVerification.verify(member);

					// registration to the local database
					Registration.register(member);

					// check the email is in the local database independently
					if (Utilities.isAlreadyExist(member.getuserEmail())) {
						System.out.println("You may login again.");
					} else {
						System.out.println("The sign up is fail, you need to sign up again!");
					}

				}

			} else if (firstInput.equalsIgnoreCase("t")) {
				// test for developers~
				System.out.println("This is test mode!!!");

				break;

			} else {
				break;
			}
		}

		System.out.println("See you next time~~~");
	}
}
