package back.kickoff.kickoffback.Commands;

import org.json.JSONObject;

public class AddRatingCommand {
    public long courtOwnerId ;
    public long playerId ;
    public String review ;
    public int stars ;

    public AddRatingCommand(String JsonInfo) throws Exception {
        JSONObject jsonObject = new JSONObject(JsonInfo);
        courtOwnerId = jsonObject.getLong("courtOwnerId");
        playerId = jsonObject.getLong("playerId");
        review = jsonObject.getString("review");
        stars = jsonObject.getInt("stars");
    }

}
