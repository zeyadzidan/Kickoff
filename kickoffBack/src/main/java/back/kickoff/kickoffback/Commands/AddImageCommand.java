package back.kickoff.kickoffback.Commands;

import lombok.Getter;
import org.json.JSONException;
import org.json.JSONObject;

@Getter
public class AddImageCommand {
    public long ownerId;
    public String imageURL ;

    public AddImageCommand(String JSONinfo) throws Exception {
        JSONObject jsonObject = new JSONObject(JSONinfo);
        try {
            ownerId = jsonObject.getLong("ownerID");
        } catch (Exception e) {
            throw new JSONException("ownerID is required");
        }
        try {
            imageURL = jsonObject.getString("imageURL");
            if (imageURL == null) {
                throw new NullPointerException();
            }
        } catch (Exception e) {
            throw new JSONException("imageURL is required");
        }

    }


}
