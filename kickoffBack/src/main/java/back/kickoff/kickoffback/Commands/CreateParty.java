package back.kickoff.kickoffback.Commands;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateParty {

    public Long reservationId;
    public String neededNumbers;
    public String availableNumbers;
    public CreateParty(String jsonParty) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonParty);
         reservationId = jsonObject.getLong("reservationId");
         neededNumbers = jsonObject.getString("emptyplaces");
         availableNumbers = jsonObject.getString("fullplaces");
    }

}
