package back.kickoff.kickoffback.Commands;

import back.kickoff.kickoffback.model.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Optional;
@NoArgsConstructor
@Getter
public class BookCommand {
    // in JSON
    Long reservationId ;
    Integer moneyPaid ;


    public BookCommand(String JSONString) throws Exception {
        JSONObject jsonObject = new JSONObject(JSONString);
        reservationId = jsonObject.getLong("reservationId");
        moneyPaid = jsonObject.getInt("moneyPaid");

    }
}
