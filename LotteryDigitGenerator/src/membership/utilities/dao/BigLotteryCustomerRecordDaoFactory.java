package membership.utilities.dao;

public class BigLotteryCustomerRecordDaoFactory {
	   public static BigLotteryCustomerRecordDao createBigLotteryCustomerRecordDao() {
		   BigLotteryCustomerRecordDao bDao = new BigLotteryCustomerRecordDaoJdbcImpl();
		   return bDao;
	   }
}
