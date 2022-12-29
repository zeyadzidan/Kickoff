package back.kickoff.kickoffback.Commands;

import lombok.*;
import org.json.JSONException;
import org.json.JSONObject;

@Setter
@Getter
public class AddAnnouncmentCommand {
    Long courtOwnerId ;
    String title ;
    String body;
    String attachmentsURL ;
    String dateString ;

    public AddAnnouncmentCommand(String informationJSON) throws JSONException {
        JSONObject jsonObject = new JSONObject(informationJSON);
        courtOwnerId = jsonObject.getLong("courtOwnerId");
        title = jsonObject.getString("title");
        body = jsonObject.getString("body");
        attachmentsURL = null;
        if (jsonObject.has("attachments")) {
            attachmentsURL = jsonObject.getString("attachments");
        }
        dateString = jsonObject.getString("date");

    }

}
