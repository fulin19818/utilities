package zhaopin.highend.utilities;

import java.util.ArrayList;
import java.util.List;

public class PageListRow
{
	/**
	 * �����б�
	 */
	public List<DataRow> RowList;
	
	/**
	 * ������
	 */
	public int ItemCount;
	
	public PageListRow()
	{
		this.ItemCount=0;
		
		this.RowList=new ArrayList<DataRow>();
		
	}

}
