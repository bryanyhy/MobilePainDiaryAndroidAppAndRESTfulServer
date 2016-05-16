package restclient;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import restclient.Patient;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-28T15:42:39")
@StaticMetamodel(DailyRecord.class)
public class DailyRecord_ { 

    public static volatile SingularAttribute<DailyRecord, String> painTrigger;
    public static volatile SingularAttribute<DailyRecord, String> latitude;
    public static volatile SingularAttribute<DailyRecord, Integer> painLevel;
    public static volatile SingularAttribute<DailyRecord, Patient> userId;
    public static volatile SingularAttribute<DailyRecord, Integer> recordId;
    public static volatile SingularAttribute<DailyRecord, String> moodLevel;
    public static volatile SingularAttribute<DailyRecord, Date> recordTime;
    public static volatile SingularAttribute<DailyRecord, Double> temperature;
    public static volatile SingularAttribute<DailyRecord, Date> recordDate;
    public static volatile SingularAttribute<DailyRecord, Double> humidity;
    public static volatile SingularAttribute<DailyRecord, String> painLocation;
    public static volatile SingularAttribute<DailyRecord, Double> windSpeed;
    public static volatile SingularAttribute<DailyRecord, Double> atmosphericPressure;
    public static volatile SingularAttribute<DailyRecord, String> longitude;

}