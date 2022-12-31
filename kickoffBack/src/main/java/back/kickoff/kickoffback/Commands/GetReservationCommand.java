package back.kickoff.kickoffback.Commands;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
@NoArgsConstructor
@Data
@Getter
public class GetReservationCommand {
    Long courtId ;
    Long courtOwnerId ;
    boolean ascending ;
    String strDate ;
    String filter ;

    Date date ;

    public GetReservationCommand(String JSONinfo) throws Exception {
        JSONObject jsonObject = new JSONObject(JSONinfo);
        courtId = jsonObject.getLong("courtId");
        courtOwnerId = jsonObject.getLong("courtOwnerId");
        strDate = jsonObject.getString("date");

        filter = (jsonObject.has("filter")) ? jsonObject.getString("filter") : "";
        ascending = (jsonObject.has("ascending")) ? jsonObject.getBoolean("ascending") : Boolean.TRUE;

        String[] tempArrS = strDate.split("/");
        if (tempArrS.length != 3)
            throw new Exception("In valid date " + strDate);
        try
        {
            SimpleDateFormat obj = new SimpleDateFormat("MM/dd/yyyy");
            long date1 = obj.parse(strDate).getTime();
            date = new Date(date1);
        } catch (Exception e) {
            throw new Exception("In valid date format " + strDate);
        }
    }
}
