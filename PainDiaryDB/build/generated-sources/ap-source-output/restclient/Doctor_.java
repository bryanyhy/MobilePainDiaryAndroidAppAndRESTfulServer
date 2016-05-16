package restclient;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import restclient.Patient;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-28T15:42:39")
@StaticMetamodel(Doctor.class)
public class Doctor_ { 

    public static volatile SingularAttribute<Doctor, String> clinicPhone;
    public static volatile SingularAttribute<Doctor, String> docFirstname;
    public static volatile SingularAttribute<Doctor, String> clinicAddress;
    public static volatile SingularAttribute<Doctor, String> docSurname;
    public static volatile SingularAttribute<Doctor, Integer> docId;
    public static volatile SingularAttribute<Doctor, String> docPhone;
    public static volatile CollectionAttribute<Doctor, Patient> patientCollection;

}