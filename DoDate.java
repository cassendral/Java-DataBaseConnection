package homework3;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DoDate {

	public static java.sql.Date stringToSQLDate(String string) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date utilDate = sdf.parse(string);
        java.sql.Date sqlDate=new java.sql.Date(utilDate.getTime());
        return sqlDate;
    }
	
}
