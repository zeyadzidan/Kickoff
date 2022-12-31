package back.kickoff.kickoffback.Commands;

import lombok.*;
import org.json.JSONException;
import org.json.JSONObject;

@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class AddAnnouncementCommand {
    Long courtOwnerId ;
    String title ;
    String body;
    String attachmentsURL ;
    String dateString ;

    public AddAnnouncementCommand(String informationJSON) throws JSONException {
        JSONObject jsonObject = new JSONObject(informationJSON);
        courtOwnerId = jsonObject.getLong("courtOwnerId");
        body = jsonObject.getString("body");
        attachmentsURL = null;
        if (jsonObject.has("attachments")) {
            attachmentsURL = jsonObject.getString("attachments");
        }
        dateString = jsonObject.getString("date");
    }

}
