package back.kickoff.kickoffback.Commands.Add;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.json.JSONException;
import org.json.JSONObject;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode
@AllArgsConstructor
public class CreateParty {

    @NotNull
    public Long reservationId;
    @NotNull
    public String neededNumbers;
    @NotNull
    public String availableNumbers;
    public CreateParty(String jsonParty) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonParty);
         reservationId = jsonObject.getLong("reservationId");
         neededNumbers = jsonObject.getString("emptyplaces");
         availableNumbers = jsonObject.getString("fullplaces");
    }

}
