package zhaopin.highend.utilities;

import java.util.ArrayList;
import java.util.List;

public class PageListRow
{
	/**
	 * 数据列表
	 */
	public List<DataRow> RowList;
	
	/**
	 * 总行数
	 */
	public int ItemCount;
	
	public PageListRow()
	{
		this.ItemCount=0;
		
		this.RowList=new ArrayList<DataRow>();
		
	}

}
