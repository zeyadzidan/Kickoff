package back.kickoff.kickoffback.Commands;

import org.json.JSONException;
import org.json.JSONObject;

public class ReceiptCommand {
    public Long reservationId ;
    public String receiptUrl ;

    public ReceiptCommand(String jsonInfo) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonInfo);
        reservationId = jsonObject.getLong("reservationId");
        receiptUrl = jsonObject.getString("receiptUrl");
    }
}
