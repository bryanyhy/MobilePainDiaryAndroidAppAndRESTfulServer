package bryan.mobilepaindiary.ObjectClass;


import java.sql.Date;

import bryan.mobilepaindiary.ObjectClass.Doctor;

/**
 * Created by Bryanyhy on 25/4/16.
 */
// patient class for storing patient info from rest server
public class Patient {

    private int userId;
    private String userFirstname;
    private String userSurname;
    private Date dob;
    private Double height;
    private Double weight;
    private String gender;
    private String occupation;
    private String userAddress;
    private Doctor docId;

    public Patient (int userId)
    {
        this.userId = userId;
    }

    public Patient (int userId, String fname, String sname, Double height, Double weight, String gender, String occupation, String address, Doctor docId)
    {
        this.userId = userId;
        this.userFirstname = fname;
        this.userSurname = sname;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.occupation = occupation;
        this.userAddress = address;
        this.docId = docId;
    }

    public String getUserFirstname() {
        return userFirstname;
    }

    public void setUserFirstname(String userFirstname) {
        this.userFirstname = userFirstname;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public Doctor getDocId() {
        return docId;
    }

    public void setDocId(Doctor docId) {
        this.docId = docId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
