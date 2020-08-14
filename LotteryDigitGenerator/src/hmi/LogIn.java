package hmi;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.SortedSet;

import lotterynumbers.BigLotteryNumberGenerator;
import membership.utilities.BigLotteryCustomerRecord;
import membership.utilities.Utilities;
import membership.utilities.dao.BigLotteryCustomerRecordDao;
import membership.utilities.dao.BigLotteryCustomerRecordDaoFactory;
import membership.verification.ThirdpartyVerification;
import person.identity.Account;
import person.identity.Member;
import person.identity.Visitor;
import person.identity.dao.MembershipDao;
import person.identity.dao.MembershipDaoFactory;

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
				if (validAccount != null) {
					visitor.setuserEmail(validAccount.getUserEmail());
					visitor.setuserPwd(validAccount.getUserPwd());
				}

				if (visitor.isTrueMember()) {
					System.out.println("------------------------------------------");
					System.out.println("Press 'N' or 'n' for asking lucky numbers.");
					System.out.println("Press 'R' or 'r' for checking the records.");
					System.out.println("Or enter any character to go to the last level.");
					System.out.println("------------------------------------------");
					String trueMemberFirstInput = new Scanner(System.in).next();
					if (trueMemberFirstInput.equalsIgnoreCase("N")) {
						boolean isContinue = false;
						do {
							int checkedTicketNumber = Utilities.checkTicketNumber();
							// pick randomness by set
							ArrayList<String> customerRecords = new ArrayList<String>();
							for (SortedSet<Integer> luckyNumberSet : BigLotteryNumberGenerator
									.luckyNumberSetsGenerator(checkedTicketNumber)) {
								String commaSeparatedStr = Utilities.luckyNumbersToStr(luckyNumberSet);
								customerRecords.add(commaSeparatedStr);
								Utilities.persistRretrieveNumbers(commaSeparatedStr);
								System.out.println("(" + commaSeparatedStr + ")");
							}
							Utilities.insertCustomerRecord(visitor.getuserEmail(), customerRecords);

//							// pick randomness by number
//							for (SortedSet<Integer> luckyNumberSet : BigLotteryNumberGeneratorSimple
//									.LuckyNumberSetGenerator(checkedTicketNumber)) {
//								System.out.println(luckyNumberSet);
//							}

							System.out.println("------------------------------------------");
							System.out
									.println("Press 'C' or 'c' for continue or any other key to go to the last level.");
							isContinue = new Scanner(System.in).next().equalsIgnoreCase("C");
						} while (isContinue);

					} else if (trueMemberFirstInput.equalsIgnoreCase("R")) {
						System.out.println(visitor.getuserEmail() + " You have asked the numbers below:");
						BigLotteryCustomerRecordDao bDao = BigLotteryCustomerRecordDaoFactory
								.createBigLotteryCustomerRecordDao();
						bDao.createConn();
						ArrayList<BigLotteryCustomerRecord> customerRecords = new ArrayList<BigLotteryCustomerRecord>();
						customerRecords.addAll(bDao.findByEmail(visitor.getuserEmail()));
						for (BigLotteryCustomerRecord record : customerRecords) {
							System.out.println(record.getLotterynumbers());
						}
						System.out.println("The end of the records.");
						bDao.closeConn();
						System.out.println("------------------------------------------");
					}

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
				if (Utilities.isEmailAlreadyExist(validAccount.getUserEmail())) {
					System.out.println("The email is already exist.");
				} else {
					member.setUserEmail(validAccount.getUserEmail());
					member.setUserPwd(validAccount.getUserPwd());

					// verification from other database
					ThirdpartyVerification.verify(member);

					// registration to the local database
					Utilities.register(member);

					// check the email is in the local database independently
					if (Utilities.isEmailAlreadyExist(member.getUserEmail())) {
						System.out.println("You may login again.");
					} else {
						System.out.println("The sign up is fail, you need to sign up again!");
					}

				}

			} else if (firstInput.equalsIgnoreCase("T")) {
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
