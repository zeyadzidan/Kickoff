package back.kickoff.kickoffback.Commands;

import org.json.JSONException;
import org.json.JSONObject;

public class AddPenaltyCommand {
    public long fromId ;
    public long onId ;
    public boolean isCourtOwnerReport;

    public AddPenaltyCommand(String JsonString, boolean isCourtOwnerReport) throws JSONException {
        JSONObject jsonObject = new JSONObject(JsonString);
        this.isCourtOwnerReport = isCourtOwnerReport ;
        if(isCourtOwnerReport){
            fromId = jsonObject.getLong("coid");
            onId = jsonObject.getLong("pid");
        }else{
            fromId = jsonObject.getLong("pid1");
            onId = jsonObject.getLong("pid2");
        }
    }

}
