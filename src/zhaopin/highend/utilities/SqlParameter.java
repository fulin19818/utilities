package zhaopin.highend.utilities;

public class SqlParameter {

	
    public SqlParameter(String type, Object value) 
    {

        this.type = type;

        this.value = value;

     }

  

     String type;

     Object value;

  

     public String getType() {

        return type;

     }

  

     public Object getValue() {

        return value;

     }

  

     public void setType(String type) {

        this.type = type;

     }

  

     public void setValue(Object value) {

        this.value = value;

     }

}
