package back.kickoff.kickoffback.Commands;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.time.LocalTime;

public class CreateCourtCommand {
    public long ownerID ;
    public String courtName ;
    public String description  ;
    public int morningCost;
    public int nightCost;
    public int minBookingHours;
    public int startWorkingHours;
    public int finishWorkingHours;
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

}
