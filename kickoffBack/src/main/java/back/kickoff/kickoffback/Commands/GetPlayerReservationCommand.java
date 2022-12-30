package back.kickoff.kickoffback.Commands;

import org.json.JSONException;
import org.json.JSONObject;

public class GetPlayerReservationCommand {

    public long pid ;
    public String  filter ;
    public boolean ascending;

    public GetPlayerReservationCommand(String jsonImfo) throws Exception {
        JSONObject object = new JSONObject(jsonImfo);
        pid = object.has("pid") ? object.getLong("pid") : -1L;
        filter = object.has("filter") ? object.getString("filter") : "Booked";
        ascending = !object.has("ascending") || object.getBoolean("ascending");
    }

}
