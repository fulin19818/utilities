package zhaopin.highend.utilities;



public class Test {

	public static void main(String args[])
	{

		test();
		
	}
	
	private static void test()
	{

		SqlAccess access=configAdapter.getAccessByDBKey("ZhaoPin.HighEnd.SeekerDB");

		
		SqlParameter[] params=new SqlParameter[1];
		
		params[0]=new SqlParameter("datetime","2014-1-1");

		String sql="select * from SeekerInfo where LastLoginTime>?";
		
		PageListRow row=access.executeTableForPage(1, 10, sql, params, " SeekerUserID desc");
		
		System.out.println(row.ItemCount);
		
		System.out.println(row.RowList.size());
		
	}
}
