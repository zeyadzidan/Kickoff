package back.kickoff.kickoffback.Commands.Add;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.json.JSONException;
import org.json.JSONObject;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class AddImageCommand {
    @NotNull
    public long ownerId;
    @NotNull
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
