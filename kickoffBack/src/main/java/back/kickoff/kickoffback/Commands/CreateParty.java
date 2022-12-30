package back.kickoff.kickoffback.Commands;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateParty {
   public Long playerId;
   public Long courtOnwerId;
    public Long courtId;
    public Long reservationId;
    public String neededNumbers;
    public String availableNumbers;
    public CreateParty(String jsonParty) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonParty);
         playerId = jsonObject.getLong("playerId");
         courtOnwerId = jsonObject.getLong("courtOnwerId");
         courtId = jsonObject.getLong("courtId");
         reservationId = jsonObject.getLong("reservationId");
         neededNumbers = jsonObject.getString("neededNumbers");
         availableNumbers = jsonObject.getString("availableNumbers");
    }

}
