package zhaopin.highend.utilities;

public class StringOper 
{

	
	public static String GetLastClassName(String className)
	{
		
		String lastName=null;
		
		String[] array=className.split(".");
		
		lastName=array[array.length-1];
		
		return lastName;
	}
}
