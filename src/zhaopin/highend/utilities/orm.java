package zhaopin.highend.utilities;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map.Entry;


public class orm 
{
	
	
	@SuppressWarnings("unchecked")
	public static Object getInstanceByDataRow(DataRow dr,String path)
	{

		Object obj=null;
		
		@SuppressWarnings("rawtypes")
		Class c=null;
		
		try 
		{
			c = Class.forName(path);
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		
		@SuppressWarnings("rawtypes")
		Constructor m=null;
		
		try 
		{
			m = c.getConstructor();
			
		} catch (NoSuchMethodException | SecurityException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		obj=getInstanceByDataRow(dr,m);
		
		return obj;
	}
	
	private static Object getInstanceByDataRow(DataRow dr,Constructor<?> conn)
	{
		Object obj=null;
		
		if(conn==null)
		{
			return obj;
		}
		
		try {
			obj=conn.newInstance();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(obj!=null)
		{

			for (Entry<String, Object> entry: dr.Data.entrySet())
			{

			    String key = entry.getKey();

			    Object value = entry.getValue();
			    
			    Field f=null;
			    
				try 
				{
					//conn.getDeclaringClass();
					f =conn.getDeclaringClass().getDeclaredField(key);
				} catch (NoSuchFieldException e) 
				{
					
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(f!=null)
				{
					try 
					{
						f.set(obj, value);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				


			}
		}
		
		return obj;
		
	}

}
