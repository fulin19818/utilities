package zhaopin.highend.utilities;


import java.util.HashMap;
import java.util.Map;

public class DataRow 
{
    public Map<String,Object> Data=new HashMap<String,Object>();
    
    
    public Object getData(String columnName)
    {
    	Object obj=null;
    	
    	if(Data!=null&&Data.containsKey(columnName))
    	{
    		obj=Data.get(columnName);
    			
    	}
    	
    	return obj;
    }
}
