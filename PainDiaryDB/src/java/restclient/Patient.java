/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restclient;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bryanyhy
 */
@Entity
@Table(name = "PATIENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p"),
    @NamedQuery(name = "Patient.findByUserId", query = "SELECT p FROM Patient p WHERE p.userId = :userId"),
    @NamedQuery(name = "Patient.findByUserFirstname", query = "SELECT p FROM Patient p WHERE UPPER(p.userFirstname) = UPPER(:userFirstname)"),
    @NamedQuery(name = "Patient.findByUserSurname", query = "SELECT p FROM Patient p WHERE UPPER(p.userSurname) = UPPER(:userSurname)"),
    @NamedQuery(name = "Patient.findByDob", query = "SELECT p FROM Patient p WHERE p.dob = :dob"),
    @NamedQuery(name = "Patient.findByHeight", query = "SELECT p FROM Patient p WHERE p.height = :height"),
    @NamedQuery(name = "Patient.findByWeight", query = "SELECT p FROM Patient p WHERE p.weight = :weight"),
    @NamedQuery(name = "Patient.findByGender", query = "SELECT p FROM Patient p WHERE UPPER(p.gender) = UPPER(:gender)"),
    @NamedQuery(name = "Patient.findByOccupation", query = "SELECT p FROM Patient p WHERE UPPER(p.occupation) = UPPER(:occupation)"),
    @NamedQuery(name = "Patient.findByUserAddress", query = "SELECT p FROM Patient p WHERE UPPER(p.userAddress) = UPPER(:userAddress)"),
    @NamedQuery(name = "Patient.findByPainLevel", query = "SELECT DISTINCT p FROM Patient p INNER JOIN p.dailyRecordCollection d WHERE d.painLevel = :painLevel")})

public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "USER_ID")
    private Integer userId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "USER_FIRSTNAME")
    private String userFirstname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "USER_SURNAME")
    private String userSurname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DOB")
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HEIGHT")
    private double height;
    @Basic(optional = false)
    @NotNull
    @Column(name = "WEIGHT")
    private double weight;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "GENDER")
    private String gender;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "OCCUPATION")
    private String occupation;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "USER_ADDRESS")
    private String userAddress;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<DailyRecord> dailyRecordCollection;
    @JoinColumn(name = "DOC_ID", referencedColumnName = "DOC_ID")
    @ManyToOne(optional = false)
    private Doctor docId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<Registration> registrationCollection;

    public Patient() {
    }

    public Patient(Integer userId) {
        this.userId = userId;
    }

    public Patient(Integer userId, String userFirstname, String userSurname, Date dob, double height, double weight, String gender, String occupation, String userAddress) {
        this.userId = userId;
        this.userFirstname = userFirstname;
        this.userSurname = userSurname;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.occupation = occupation;
        this.userAddress = userAddress;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
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

    @XmlTransient
    public Collection<DailyRecord> getDailyRecordCollection() {
        return dailyRecordCollection;
    }

    public void setDailyRecordCollection(Collection<DailyRecord> dailyRecordCollection) {
        this.dailyRecordCollection = dailyRecordCollection;
    }

    public Doctor getDocId() {
        return docId;
    }

    public void setDocId(Doctor docId) {
        this.docId = docId;
    }

    @XmlTransient
    public Collection<Registration> getRegistrationCollection() {
        return registrationCollection;
    }

    public void setRegistrationCollection(Collection<Registration> registrationCollection) {
        this.registrationCollection = registrationCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Patient)) {
            return false;
        }
        Patient other = (Patient) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restclient.Patient[ userId=" + userId + " ]";
    }
    
}
