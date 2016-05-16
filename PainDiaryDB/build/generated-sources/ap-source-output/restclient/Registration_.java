package restclient;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import restclient.Patient;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-28T15:42:39")
@StaticMetamodel(Registration.class)
public class Registration_ { 

    public static volatile SingularAttribute<Registration, Date> regDatetime;
    public static volatile SingularAttribute<Registration, String> password;
    public static volatile SingularAttribute<Registration, Integer> regId;
    public static volatile SingularAttribute<Registration, Patient> userId;
    public static volatile SingularAttribute<Registration, String> username;

}