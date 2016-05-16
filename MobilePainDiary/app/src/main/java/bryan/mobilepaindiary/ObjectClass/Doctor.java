package bryan.mobilepaindiary.ObjectClass;

/**
 * Created by Bryanyhy on 25/4/16.
 */
// Doctor class to save the info we got from server
public class Doctor {
    private int docId;
    private String docFirstname;
    private String docSurname;
    private String docPhone;
    private String clinicAddress;
    private String clinicPhone;

    public Doctor(int docId, String docFirstname, String docSurname, String docPhone, String clinicAddress, String clinicPhone) {
        this.docId = docId;
        this.docFirstname = docFirstname;
        this.docSurname = docSurname;
        this.docPhone = docPhone;
        this.clinicAddress = clinicAddress;
        this.clinicPhone = clinicPhone;
    }

    public int getId() {
        return docId;
    }

    public void setId(int id) {
        this.docId = id;
    }

    public String getFirstname() {
        return docFirstname;
    }

    public void setFirstname(String firstname) {
        this.docFirstname = firstname;
    }

    public String getSurname() {
        return docSurname;
    }

    public void setSurname(String surname) {
        this.docSurname = surname;
    }

    public String getClinic() {
        return clinicAddress;
    }

    public void setClinic(String clinic) {
        this.clinicAddress = clinic;
    }

    public String getPhone() {
        return docPhone;
    }

    public void setPhone(String phone) {
        this.docPhone = phone;
    }

    public String getClinicPhone() {
        return clinicPhone;
    }

    public void setClinicPhone(String clinicPhone) {
        this.clinicPhone = clinicPhone;
    }
}
