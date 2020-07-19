package person.identity;

import membership.utilities.Utilities;

public class Account {
	private String userEmail;
	private String userPassword;

	public Account() {
		super();
	}

	public Account(String userEmail, String userPassword) {
		super();
		this.userEmail = userEmail;
		this.userPassword = userPassword;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public boolean isFormatValid() {
		return Utilities.verifyEmailFormat(getUserEmail()) && Utilities.verifyPwdFormat(getUserPassword());
	}

}
