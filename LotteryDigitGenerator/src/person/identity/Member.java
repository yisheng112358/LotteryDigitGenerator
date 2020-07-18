package person.identity;

public class Member {
	private int memberId;
	private String userEmail;
	private String userPwd;
	private boolean isVerified;

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

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public int getMemberId() {
		return memberId;
	}

}
