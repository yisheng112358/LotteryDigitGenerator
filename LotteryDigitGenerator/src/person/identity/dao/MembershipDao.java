package person.identity.dao;

import java.sql.SQLException;

import person.identity.Member;

public interface MembershipDao {
	public boolean createConn() throws SQLException;

	public void add(Member member) throws SQLException;

	public void update(Member member) throws SQLException;

	public void delete(Member member) throws SQLException;

	public Member findByEmail(String userEmail) throws SQLException;

	public void closeConn() throws SQLException;

}
