package demo.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Random;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Entity
@Table(name = "running_analysis")
public class RunningInformation {

    public enum HealthWarningLevel {
        LOW, NORMAL, HIGH
    }





    @Id
    @GeneratedValue
    private Long id;

    // Create an embedded field
    @Embedded
    private final UserInfo userInfo;

    private String runningId;

    /*Test Feature*/


    private double longitude;
    private double latitude;

    private String runningDistance;
    private double totalRunningTime;

    private int heartRate = 0;
    private HealthWarningLevel healthWarningLevel;

    private Date timestamp = new Date();

    public RunningInformation() {

        this.userInfo = null;
    }

    public RunningInformation(String username, String address, int zipcode, String state) {

        this.userInfo = new UserInfo(username, address,zipcode,state);
    }

    @JsonCreator
    public RunningInformation(
            @JsonProperty("runningId") String runningId,
            @JsonProperty("totalRunningTime") String totalRunningTime,
            @JsonProperty("heartRate") int heartRate,
            @JsonProperty("timestamp") Date timestamp,
            @JsonProperty("userInfo") UserInfo userInfo) {

        this.runningId = runningId;
        this.totalRunningTime = Double.parseDouble(totalRunningTime);
        this.heartRate = getRandomHeartRate();
        this.timestamp = new Date();
        this.userInfo = userInfo;
        this.healthWarningLevel = getHealthWarningLevel();


    }

    public RunningInformation(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
    public HealthWarningLevel getHealthWarningLevel(){
        int lowBound = 60;
        int normalBound = 75;
        int highBound = 120;
        if (this.heartRate > highBound) {
            this.healthWarningLevel = HealthWarningLevel.HIGH;
        } else if (this.heartRate > normalBound) {
            this.healthWarningLevel = HealthWarningLevel.NORMAL;
        } else if (this.heartRate >= lowBound) {
            this.healthWarningLevel = HealthWarningLevel.LOW;
        }
        return this.healthWarningLevel;
    }

    public String getUsername() {
        return this.userInfo == null ? null : this.userInfo.getUsername();
    }

    public String getAddress() {
        return this.userInfo == null ? null : this.userInfo.getAddress();
    }

    public int getZipcode(){
        return this.userInfo == null ? null : this.userInfo.getZipcode();
    }
    public String getState(){
        return this.userInfo == null ? null : this.userInfo.getState();
    }

    private int getRandomHeartRate() {
        int maxBound = 200;
        int minBound = 60;
        Random rn = new Random();
        return minBound + rn.nextInt(maxBound - minBound + 1);
    }
}
