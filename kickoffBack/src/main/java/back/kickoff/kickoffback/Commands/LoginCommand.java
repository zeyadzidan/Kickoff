package back.kickoff.kickoffback.Commands;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginCommand {

    public String email;
    public String password ;

    public LoginCommand(String JsonInfo) throws JSONException {
        JSONObject jsonObject = new JSONObject(JsonInfo);
        email = jsonObject.getString("email");
        password = jsonObject.getString("password");
    }
}
