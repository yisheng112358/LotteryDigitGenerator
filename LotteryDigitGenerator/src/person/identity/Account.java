package person.identity;

import membership.utilities.Utilities;

public class Account {
	private String userEmail;
	private String userPwd;

	public Account() {
		super();
	}

	public Account(String userEmail, String userPassword) {
		super();
		this.userEmail = userEmail;
		this.userPwd = userPassword;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPassword) {
		this.userPwd = userPassword;
	}

	public boolean isFormatValid() {
		return Utilities.verifyEmailFormat(getUserEmail()) && Utilities.verifyPwdFormat(getUserPwd());
	}

}
