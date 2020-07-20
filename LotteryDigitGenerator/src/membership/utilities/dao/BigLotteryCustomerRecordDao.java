package membership.utilities.dao;

import java.sql.SQLException;
import java.util.List;

import membership.utilities.BigLotteryCustomerRecord;

public interface BigLotteryCustomerRecordDao {
	public boolean createConn() throws SQLException;

	public List<BigLotteryCustomerRecord> findByEmail(String userEmail) throws SQLException;

	public void closeConn() throws SQLException;
}
