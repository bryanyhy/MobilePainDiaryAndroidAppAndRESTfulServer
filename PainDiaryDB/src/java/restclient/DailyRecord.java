/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restclient;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bryanyhy
 */
@Entity
@Table(name = "DAILY_RECORD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DailyRecord.findAll", query = "SELECT d FROM DailyRecord d"),
    @NamedQuery(name = "DailyRecord.findByRecordId", query = "SELECT d FROM DailyRecord d WHERE d.recordId = :recordId"),
    @NamedQuery(name = "DailyRecord.findByRecordDate", query = "SELECT d FROM DailyRecord d WHERE d.recordDate = :recordDate"),
    @NamedQuery(name = "DailyRecord.findByRecordTime", query = "SELECT d FROM DailyRecord d WHERE d.recordTime = :recordTime"),
    @NamedQuery(name = "DailyRecord.findByPainLevel", query = "SELECT d FROM DailyRecord d WHERE d.painLevel = :painLevel"),
    @NamedQuery(name = "DailyRecord.findByPainLocation", query = "SELECT d FROM DailyRecord d WHERE UPPER(d.painLocation) = UPPER(:painLocation)"),
    @NamedQuery(name = "DailyRecord.findByMoodLevel", query = "SELECT d FROM DailyRecord d WHERE UPPER(d.moodLevel) = UPPER(:moodLevel)"),
    @NamedQuery(name = "DailyRecord.findByPainTrigger", query = "SELECT d FROM DailyRecord d WHERE UPPER(d.painTrigger) = UPPER(:painTrigger)"),
    @NamedQuery(name = "DailyRecord.findByLatitude", query = "SELECT d FROM DailyRecord d WHERE d.latitude = :latitude"),
    @NamedQuery(name = "DailyRecord.findByLongitude", query = "SELECT d FROM DailyRecord d WHERE d.longitude = :longitude"),
    @NamedQuery(name = "DailyRecord.findByTemperature", query = "SELECT d FROM DailyRecord d WHERE d.temperature = :temperature"),
    @NamedQuery(name = "DailyRecord.findByHumidity", query = "SELECT d FROM DailyRecord d WHERE d.humidity = :humidity"),
    @NamedQuery(name = "DailyRecord.findByWindSpeed", query = "SELECT d FROM DailyRecord d WHERE d.windSpeed = :windSpeed"),
    @NamedQuery(name = "DailyRecord.findByAtmosphericPressure", query = "SELECT d FROM DailyRecord d WHERE d.atmosphericPressure = :atmosphericPressure"),
    @NamedQuery(name = "DailyRecord.findByUserId", query = "SELECT d FROM Patient p INNER JOIN p.dailyRecordCollection d WHERE p.userId = :userId")})
public class DailyRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RECORD_ID")
    private Integer recordId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RECORD_DATE")
    @Temporal(TemporalType.DATE)
    private Date recordDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RECORD_TIME")
    @Temporal(TemporalType.TIME)
    private Date recordTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAIN_LEVEL")
    private int painLevel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PAIN_LOCATION")
    private String painLocation;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "MOOD_LEVEL")
    private String moodLevel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "PAIN_TRIGGER")
    private String painTrigger;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "LATITUDE")
    private String latitude;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "LONGITUDE")
    private String longitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TEMPERATURE")
    private double temperature;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HUMIDITY")
    private double humidity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "WIND_SPEED")
    private double windSpeed;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ATMOSPHERIC_PRESSURE")
    private double atmosphericPressure;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne(optional = false)
    private Patient userId;

    public DailyRecord() {
    }

    public DailyRecord(Integer recordId) {
        this.recordId = recordId;
    }

    public DailyRecord(Integer recordId, Date recordDate, Date recordTime, int painLevel, String painLocation, String moodLevel, String painTrigger, String latitude, String longitude, double temperature, double humidity, double windSpeed, double atmosphericPressure) {
        this.recordId = recordId;
        this.recordDate = recordDate;
        this.recordTime = recordTime;
        this.painLevel = painLevel;
        this.painLocation = painLocation;
        this.moodLevel = moodLevel;
        this.painTrigger = painTrigger;
        this.latitude = latitude;
        this.longitude = longitude;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.atmosphericPressure = atmosphericPressure;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public int getPainLevel() {
        return painLevel;
    }

    public void setPainLevel(int painLevel) {
        this.painLevel = painLevel;
    }

    public String getPainLocation() {
        return painLocation;
    }

    public void setPainLocation(String painLocation) {
        this.painLocation = painLocation;
    }

    public String getMoodLevel() {
        return moodLevel;
    }

    public void setMoodLevel(String moodLevel) {
        this.moodLevel = moodLevel;
    }

    public String getPainTrigger() {
        return painTrigger;
    }

    public void setPainTrigger(String painTrigger) {
        this.painTrigger = painTrigger;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getAtmosphericPressure() {
        return atmosphericPressure;
    }

    public void setAtmosphericPressure(double atmosphericPressure) {
        this.atmosphericPressure = atmosphericPressure;
    }

    public Patient getUserId() {
        return userId;
    }

    public void setUserId(Patient userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recordId != null ? recordId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DailyRecord)) {
            return false;
        }
        DailyRecord other = (DailyRecord) object;
        if ((this.recordId == null && other.recordId != null) || (this.recordId != null && !this.recordId.equals(other.recordId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restclient.DailyRecord[ recordId=" + recordId + " ]";
    }
    
}
