package restclient;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import restclient.DailyRecord;
import restclient.Doctor;
import restclient.Registration;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-28T15:42:39")
@StaticMetamodel(Patient.class)
public class Patient_ { 

    public static volatile SingularAttribute<Patient, String> userAddress;
    public static volatile SingularAttribute<Patient, String> occupation;
    public static volatile SingularAttribute<Patient, String> gender;
    public static volatile SingularAttribute<Patient, Doctor> docId;
    public static volatile SingularAttribute<Patient, Date> dob;
    public static volatile SingularAttribute<Patient, Double> weight;
    public static volatile CollectionAttribute<Patient, DailyRecord> dailyRecordCollection;
    public static volatile SingularAttribute<Patient, String> userFirstname;
    public static volatile CollectionAttribute<Patient, Registration> registrationCollection;
    public static volatile SingularAttribute<Patient, Integer> userId;
    public static volatile SingularAttribute<Patient, String> userSurname;
    public static volatile SingularAttribute<Patient, Double> height;

}