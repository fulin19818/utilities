package zhaopin.highend.utilities;
import java.util.List;

public class DataTable {
	
    List<DataRow> row;

    

    public DataTable(List<DataRow> r) {

       row = r;

    }

 

    public List<DataRow> getRow() {

       return row;

    }

 

    public void setRow(List<DataRow> row) {

       this.row = row;

    }

}
