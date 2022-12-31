package back.kickoff.kickoffback.Commands;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.json.JSONException;
import org.json.JSONObject;

import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.time.LocalTime;

@EqualsAndHashCode
@AllArgsConstructor
public class CreateCourtCommand {
    @NotNull
    public long ownerID ;
    @NotNull
    public String courtName ;
    @NotNull
    public String description  ;
    @NotNull
    public int morningCost;
    @NotNull
    public int nightCost;
    @NotNull
    public int minBookingHours;
    @NotNull
    public int startWorkingHours;
    @NotNull
    public int finishWorkingHours;
//    @NotNull
    public int endMorningHours;



    // not in JSON
    public Time startWorkingHourTime, endWorkingHours, endMorning;

    public CreateCourtCommand(String JSONinfo) throws JSONException {
        JSONObject jsonObject = new JSONObject(JSONinfo);
        try {
            ownerID = jsonObject.getLong("ownerID");
        } catch (Exception e) {
            throw new JSONException("ownerID is required");

        }

        try {
            courtName = jsonObject.getString("courtName");
            if (courtName == null)
                throw new NullPointerException();
        } catch (Exception e) {
            throw new JSONException("courtName is required");
        }
        try {
            description = jsonObject.getString("description");
        } catch (Exception e) {
            description = "";
        }
        try {
            morningCost = jsonObject.getInt("morningCost");
        } catch (Exception e) {
            throw new JSONException("morningCost is required");
        }
        try {
            nightCost = jsonObject.getInt("nightCost");
        } catch (Exception e) {
            nightCost = morningCost;
        }
        try {
            minBookingHours = jsonObject.getInt("minBookingHours");
        } catch (Exception e) {
            minBookingHours = 1;
        }
        try {
            startWorkingHours = jsonObject.getInt("startWorkingHours");
            finishWorkingHours = jsonObject.getInt("finishWorkingHours");


            LocalTime ltFrom = LocalTime.of(startWorkingHours,0,0)  ;
            LocalTime ltTo = LocalTime.of(finishWorkingHours,0,0)  ;
            startWorkingHourTime = Time.valueOf(ltFrom) ;
            endWorkingHours = Time.valueOf(ltTo) ;

        } catch (Exception e) {
            throw new JSONException("In valid Time");
        }
        try {
            endMorningHours = jsonObject.getInt("endMorningHours");
            LocalTime t = LocalTime.of(endMorningHours,0,0)  ;
            endMorning = Time.valueOf(t) ;
        } catch (Exception e) {
            endMorning = endWorkingHours;

        }
    }
    public CreateCourtCommand(long ownerID, String courtName, String description,
                              int morningCost, int nightCost, int minBookingHours, int startWorkingHours, int finishWorkingHours) {
        this.ownerID = ownerID;
        this.courtName = courtName;
        this.description = description;
        this.morningCost = morningCost;
        this.nightCost = nightCost;
        this.minBookingHours = minBookingHours;
        this.startWorkingHours = startWorkingHours;
        this.finishWorkingHours = finishWorkingHours;
    }

}
