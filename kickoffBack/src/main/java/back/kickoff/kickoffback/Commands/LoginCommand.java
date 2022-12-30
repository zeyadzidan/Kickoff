package back.kickoff.kickoffback.Commands;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.json.JSONException;
import org.json.JSONObject;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode
@AllArgsConstructor
public class LoginCommand {
    @NotNull
    public String email;
    @NotNull
    public String password ;

//    public LoginCommand(String JsonInfo) throws JSONException {
//        JSONObject jsonObject = new JSONObject(JsonInfo);
//        email = jsonObject.getString("email");
//        password = jsonObject.getString("password");
//    }
}
