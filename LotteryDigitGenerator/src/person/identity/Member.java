package person.identity;

public class Member implements java.io.Serializable {
	/**
	 * Only the members can have Big Lottery lucky numbers.
	 */
	private static final long serialVersionUID = 1L;
	private int memberId;
	private String userEmail;
	private String userPwd;
	private boolean isVerified;

	public Member() {
		super();
	}

	public Member(String userEmail, String userPwd, boolean isVerified) {
		super();
		this.userEmail = userEmail;
		this.userPwd = userPwd;
		this.isVerified = isVerified;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
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

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

}
