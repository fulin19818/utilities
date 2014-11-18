package zhaopin.highend.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTimeOper 
{
	
	/**
	 * 获取SQL日期
	 * @param date java日期
	 * @return
	 */
	public static java.sql.Date ConvertToSqlDate(java.util.Date date)
	{
		return new java.sql.Date(date.getTime());
	}
	
	/**
	 * 
	 * @return
	 */
	public static String ConverttoString(java.util.Date date,String format)
	{
		SimpleDateFormat mt=new SimpleDateFormat(format);
		
		return mt.format(date);
	}
	
	
	/**
	 * 获取日期
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @return
	 */
	public static java.util.Date GetDate(int year,int month,int day)
	{
		java.util.Date date=new java.util.Date();
		
		SimpleDateFormat mt=new SimpleDateFormat("yyyy-MM-dd");
		
		try 
		{
			date=mt.parse(((Integer)year).toString()+"-"+((Integer)month).toString()+"-"+((Integer)day).toString());
		} catch (ParseException e) 
		{
			e.printStackTrace();
		}
		
		return date;
		
	}

}
