package zhaopin.highend.utilities;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class configAdapter {
	
	static Map<String,JDBCConnection> _ConnectionMap =null;
	
	static Object _locakPad=new Object();
	
	public static SqlAccess getAccessByDBKey(String dbKey)
	{
		SqlAccess accss=null;
		
		if(_ConnectionMap==null)
		{
			synchronized(_locakPad)
			{
				if(_ConnectionMap==null)
				{
					initConnectionMap();
				}
			}
			
		}
		
		if(_ConnectionMap.containsKey(dbKey.toLowerCase()))
		{
			JDBCConnection conn=_ConnectionMap.get(dbKey.toLowerCase());
			
			accss=new SqlAccess(conn);
		}
		
		
		return accss;

		
	}
	
	
	private static void initConnectionMap()
	{

		String sql="Select * from DBConnections";
		
		SqlAccess access=new SqlAccess("dbconfig.properties");
		
		List<DataRow> list=null;
		
		try 
		{
			list=access.getDataTable(sql, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(list!=null&&list.size()>0)
		{
			Map<String,JDBCConnection> map=new HashMap<String,JDBCConnection>();

			for(int i=0;i<list.size();i++)
			{

				DataRow dr=list.get(i);
				
				String dbKey=dr.getData("DBKey").toString().toLowerCase();
				
				String connStr=dr.getData("ConnectionString").toString().toLowerCase();
				
				JDBCConnection jdbc=initJDBCConnectionFromStr(connStr);
				
				map.put(dbKey, jdbc);
				
				
			}
			
			_ConnectionMap=map;
		}
	}
	
	
	private static JDBCConnection initJDBCConnectionFromStr(String connStr)
	{

		JDBCConnection conn=null;
		
		String[] array=connStr.split(";");
		
		if(array!=null&&array.length>0)
		{
			conn=new JDBCConnection();
			
			String ip=null;
			
			String dbName=null;
			
			for(int i=0;i<array.length;i++)
			{
				String[] single=array[i].split("=");
				
				if(single!=null&&single.length>1)
				{
					String key=single[0].toLowerCase();
					
					switch(key)
					{
						case "data source":
							ip=single[1];
						break;
						case "initial catalog":
							dbName=single[1];
						break;
						case "user":
							conn.UserName=single[1];
						break;
						case "password":
							conn.Password=single[1];
						break;
					}
				}
				
				conn.Url="jdbc:sqlserver://"+ip+":1433;DatabaseName="+dbName;
				
				conn.DriverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
			}
			
		}
		
		
		return conn;
	}
	
	

}
