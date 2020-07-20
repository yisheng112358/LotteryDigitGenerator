package person.identity.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.ConnectToSqlServer;
import person.identity.Member;

public class MembershipDaoJdbcImpl implements MembershipDao {

	private Connection conn;

	@Override
	public boolean createConn() throws SQLException {
		try {
			ConnectToSqlServer connection = new ConnectToSqlServer();
			conn = connection.getConn();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public void add(Member member) throws SQLException {
		String sqlstr = "Insert Into membership(useremail, userpwd, isverified) Values(?, ?, ?);";
		PreparedStatement preState = conn.prepareStatement(sqlstr);
		preState.setString(1, member.getUserEmail());
		preState.setString(2, member.getUserPwd());
		preState.setInt(3, member.isVerified() ? 1 : 0);
		boolean addNotSuccess = preState.execute();
		if (!addNotSuccess) {
			System.out.printf("Welcome to join the members!");
		} else {
			System.out.printf("Something went wrong please try again later.");
		}
		preState.close();
	}

	@Override
	public void update(Member member) throws SQLException {
		String sqlstr = "Update membership Set userpwd = ?, isverified = ? Where useremail = ?;";
		PreparedStatement state = conn.prepareStatement(sqlstr);
		state.setString(1, member.getUserPwd());
		state.setInt(2, member.isVerified() ? 1 : 0);
		state.setString(3, member.getUserEmail());
		state.execute();
		state.close();
	}

	@Override
	public void delete(Member member) throws SQLException {
		String sqlstr = "Delete From membership Where useremail = ?;";
		PreparedStatement state = conn.prepareStatement(sqlstr);
		state.setString(1, member.getUserEmail());
		state.execute();
		state.close();
	}

	@Override
	public Member findByEmail(String userEmail) throws SQLException {
		String sqlstr = "Select * From membership Where useremail = ?;";
		PreparedStatement state = conn.prepareStatement(sqlstr);
		state.setString(1, userEmail);
		ResultSet rs = state.executeQuery();
		Member m1 = null;
		if (rs.next()) {
			m1 = new Member();

			m1.setMemberId(rs.getInt(1));
			m1.setUserEmail(rs.getString(2));
			m1.setUserPwd(rs.getString(3));
			m1.setVerified(rs.getInt(4) == 0 ? false : true);
		}
		rs.close();
		state.close();
		return m1;
	}

	@Override
	public void closeConn() throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}

}
