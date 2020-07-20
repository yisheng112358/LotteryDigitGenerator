package person.identity.dao;

public class MembershipDaoFactory {
	   public static MembershipDao createMemberDao() {
		   MembershipDao mDao = new MembershipDaoJdbcImpl();
		   return mDao;
	   }
}
