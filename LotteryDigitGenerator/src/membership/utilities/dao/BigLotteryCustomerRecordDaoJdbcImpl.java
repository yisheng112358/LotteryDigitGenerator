package membership.utilities.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectToSqlServer;
import membership.utilities.BigLotteryCustomerRecord;

public class BigLotteryCustomerRecordDaoJdbcImpl implements BigLotteryCustomerRecordDao {

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
	public List<BigLotteryCustomerRecord> findByEmail(String userEmail) throws SQLException {
		String sqlstr = "Select lotterycustomer, lotterynumbers From BigLotteryCustomerRecord Where lotterycustomer = ?;";
		PreparedStatement state = conn.prepareStatement(sqlstr);
		state.setString(1, userEmail);
		ResultSet rs = state.executeQuery();
		List<BigLotteryCustomerRecord> record = new ArrayList<BigLotteryCustomerRecord>();
		while (rs.next()) {
			record.add(new BigLotteryCustomerRecord(rs.getString(1), rs.getString(2)));
		}
		rs.close();
		state.close();
		return record;
	}

	@Override
	public void closeConn() throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}
}
