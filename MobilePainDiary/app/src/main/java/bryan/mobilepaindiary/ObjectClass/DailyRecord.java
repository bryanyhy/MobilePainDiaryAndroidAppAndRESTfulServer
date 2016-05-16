package bryan.mobilepaindiary.ObjectClass;

import java.io.Serializable;

/**
 * Created by Bryanyhy on 27/4/16.
 */
public class DailyRecord implements Serializable {
    private int recordId;
    private String recordDate;
    private String recordTime;
    private int painLevel;
    private String painLocation;
    private String painTrigger;
    private String moodLevel;

    public DailyRecord (int recordId, String recordDate, String recordTime, int painLevel, String painLocation, String painTrigger, String mood)
    {
        this.recordId = recordId;
        this.recordDate = recordDate;
        this.recordTime = recordTime;
        this.painLevel = painLevel;
        this.painLocation = painLocation;
        this.painTrigger = painTrigger;
        this.moodLevel = mood;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
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

    public String getPainTrigger() {
        return painTrigger;
    }

    public void setPainTrigger(String painTrigger) {
        this.painTrigger = painTrigger;
    }

    public String getMood() {
        return moodLevel;
    }

    public void setMood(String mood) {
        this.moodLevel = mood;
    }
}
