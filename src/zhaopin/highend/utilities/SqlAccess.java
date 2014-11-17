package zhaopin.highend.utilities;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.io.IOException;

/**
 * SQL���ʿ�
 * @author Administrator
 *
 */
public class SqlAccess 
{

	/**
	 * ��ǰ����
	 */
	private JDBCConnection _connection=null;
	
	
	/**
	 * ��ȡ��ǰ���ݿ����ʵ��
	 * @param sqlhostIP IP
	 * @param dbName	���ݿ�����
	 * @param userName	�û���
	 * @param password	����
	 */
	public SqlAccess(String sqlhostIP,String dbName,String userName,String password)
	{
		initJDBCConnection(sqlhostIP,dbName,userName,password);
		
	}
	
	/**
	 * ��ʼ������
	 * @param sqlhostIP IP
	 * @param dbName	���ݿ�����
	 * @param userName	�û���
	 * @param password	����
	 */
	private void initJDBCConnection(String sqlhostIP,String dbName,String userName,String password)
	{
		JDBCConnection conn=new JDBCConnection();
		
		conn.Url="jdbc:sqlserver://"+sqlhostIP+":1433;DatabaseName="+dbName;
		
		conn.UserName=userName;
		
		conn.Password=password;
		
		conn.DriverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		
		this._connection=conn;
		
		
	}
	
	/**
	 * ��ȡ��ǰ���ݿ����ʵ��
	 * @param jdbc ���Ӵ�
	 */
	public SqlAccess(JDBCConnection jdbc)
	{
		this._connection=jdbc;
		
	}
	
	/**
	 * ��ȡ��ǰ���ݿ����ʵ��
	 * @param dbConfigFileName ���ݿ������ļ�����
	 */
	public SqlAccess(String dbConfigFileName)
	{
		Properties list=null;
		try 
		{
			list = getDBProperties(dbConfigFileName);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  
		String url = list.getProperty("URL");   
		String username = list.getProperty("USER");   
		String password = list.getProperty("PWD");
		String driverName = list.getProperty("DRIVER");
		
		JDBCConnection conn=new JDBCConnection();
		
		
		
		conn.Url=url;
		
		conn.UserName=username;
		
		conn.Password=password;
		
		conn.DriverName=driverName;

		this._connection=conn;
	}

	/**
	 * ��ȡ����
	 * @return
	 */
	private Connection getConnection()
	{
		 Connection dbConn=null;
		  
		 try 
		 {
			 
		    Class.forName(this._connection.DriverName);
		    dbConn = DriverManager.getConnection(this._connection.Url,this._connection.UserName,this._connection.Password);
		 } 
		 catch (Exception e) 
		 {
			    e.printStackTrace();
		 }
		 return dbConn;
	}

	/**
	 * ִ�и���
	 * @param sql
	 * @param p
	 * @return
	 */
	public int executeUpdate(String sql, SqlParameter[] p)
	{
		
		Connection conn=this.getConnection();
		
		int rowCount=0;
		
		if(conn!=null)
		{
			PreparedStatement ps=null;
			
			try 
			{
				ps = conn.prepareStatement(sql);
				
				for (int j = 0; j < p.length; j++) 
		        {
					ps.setObject(j+1, p[j].getValue());
		        }
				
				rowCount=ps.executeUpdate();
				
				ps.close();
				
				conn.close();
				
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		
		return rowCount;
	}
	
	/**
	 * ִ�в����������ȡ�����ID
	 * @param sql SQL��䣬����
	 * @param p SQL����
	 * @return ����ID
	 */
	public Object executeScalarInsert(String sql, SqlParameter[] p,String identityFiledTypeName)
	{
		Object autoGeneratedKeys=0;
		
		Connection conn=this.getConnection();
		
		if(conn!=null)
		{
			PreparedStatement ps=null;
			
			try 
			{
				sql+=" Select cast(@@Identity as "+identityFiledTypeName+") as identitycolumn";

				ps = conn.prepareStatement(sql);
				
				for (int j = 0; j < p.length; j++) 
		        {
					ps.setObject(j+1, p[j].getValue());
		        }
				
				ps.execute();
				
				ResultSet rs=ps.getGeneratedKeys();
				
				while(rs.next())
				{
					autoGeneratedKeys=rs.getObject(1);
				}
				
				rs.close();
				
				conn.close();
				
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		
		return autoGeneratedKeys;
		
	}
	
	/**
	 * ��ȡ�����
	 * @param sql
	 * @param p
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> getDataTable(String sql, SqlParameter[] p) throws SQLException
	{
		List<DataRow> objArray=new ArrayList<DataRow>();

		ResultSet rs=null;

		Connection conn=this.getConnection();

		if(conn!=null)
		{

			if(p!=null&&p.length>0)
			{
				PreparedStatement ps = conn.prepareStatement(sql);
				
			       for (int j = 0; j < p.length; j++) 
		           {
			    	   Object pValue=p[j].getValue();
			    	   
			    	   if(pValue instanceof java.util.Date)
			    	   {
			    		   pValue=DateTimeOper.ConvertToSqlDate((java.util.Date)pValue);
			    	   }
			    	   
			    	   ps.setObject(j+1,pValue);
		           }
			       
			       rs=ps.executeQuery();
			}
			else
			{
				Statement stmt=conn.createStatement();
				rs=stmt.executeQuery(sql);
			}

			
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int columnCount = rsmd.getColumnCount();
			
			
			
			while(rs.next())
			{
				Map<String,Object> _dic=new HashMap<String,Object>();
				
				for(int i=0;i<columnCount;i++)
				{

					String colName = rsmd.getColumnLabel(i+1);
					
					Object v=rs.getObject(colName);
					
					_dic.put(colName, v);

				}

				DataRow dr=new DataRow();
				
				dr.Data=_dic;
				
				objArray.add(dr);

			}
			
			rs.close();
			
			conn.close();
		}
		
		return objArray;
	}
	
	/**
	 * ��ȡ��ǰ���ݿ������ļ�
	 * @param propertiesFileName
	 * @return
	 * @throws IOException
	 */
	private Properties getDBProperties(String propertiesFileName) throws IOException
	{

		Properties p = new Properties(); 

		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFileName);
		
		if(in!=null)
		{
			p.load(in);
		}
		else
		{

			in=new FileInputStream(propertiesFileName);
			
			p.load(in);
		}
		
		return p;
	}
	

	/**
	 * ��ҳ��ѯ
	 * @param pageIndex ��ǰҳ
	 * @param pageSize ÿҳ��ʾ����
	 * @param sql	SQL��䣨��Ҫ������
	 * @param dbParameterList
	 * @param orderbyPart
	 * @return ��ҳʵ��
	 */
    public PageListRow executeTableForPage(int pageIndex, int pageSize, String sql, SqlParameter[] dbParameterList, String orderbyPart)
    {
    	PageListRow pageListRow=new PageListRow();

        String fromPart = " from (" + sql + ")  a ";
        String sqlCount = "select count(*) as rowCounts  " + fromPart;
        
        List<DataRow> countList=new ArrayList<DataRow>();
		try 
		{
			countList = getDataTable(sqlCount,dbParameterList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if(countList!=null&&countList.size()>0)
        {
        	DataRow row=countList.get(0);
        	
        	pageListRow.ItemCount=Integer.parseInt(row.getData("rowCounts").toString());
        	
        	
        	String contentSQL=this.getSelectTopSql(pageIndex, pageSize, sql, "*", orderbyPart);
        	
        	List<DataRow> contentList=new ArrayList<DataRow>();
			try {
				contentList = getDataTable(contentSQL,dbParameterList);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	pageListRow.RowList=contentList;
        }

        return pageListRow;
    }
	
    
	/**
	 * ��ȡ��ҳSQL
	 * @param pageIndex	��ǰҳ
	 * @param pageSize	ÿҳ��ʾ����
	 * @param sql		SQL
	 * @param fileds	��ȡ�ֶ�
	 * @param orderbyPart	����
	 * @return
	 */
    private String getSelectTopSql(int pageIndex, int pageSize, String sql, String fileds, String orderbyPart)
    {
        String fromPart = " from (" + sql + ")  a ";
        orderbyPart = " order by " + orderbyPart;

        StringBuilder sbSelect = new StringBuilder();

        sbSelect.append("select top ");
        sbSelect.append(pageSize);
        sbSelect.append(" ");
        sbSelect.append(fileds);
        sbSelect.append(" from (select ");
        sbSelect.append(fileds);
        sbSelect.append(",row_number() over(");
        sbSelect.append(orderbyPart);
        sbSelect.append(") as rownumber ");
        sbSelect.append(fromPart);
        sbSelect.append(") aa where rownumber>");
        sbSelect.append((pageIndex - 1) * pageSize);
        sbSelect.append(" order by rownumber");

        return sbSelect.toString();
    }
	
	

}

