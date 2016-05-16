package restclient;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author Bryanyhy
 */
public class Result {   
    public int painLevel;
    public double attribute;
    public String recordDate;

    public Result() {
    }
    public Result(int painLevel, Date recordDate, double attribute) {
        this.painLevel = painLevel;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateInString = sdf.format(recordDate);
        this.recordDate = dateInString;
        this.attribute = attribute;
    }  
}

