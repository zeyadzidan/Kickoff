package back.kickoff.kickoffback.Commands.Add;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.json.JSONException;
import org.json.JSONObject;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@EqualsAndHashCode
public class SignupCommand {
    @NotNull
    public String email ;
    @NotNull
    public String password ;
    @NotNull
    public String username ;
    @NotNull
    public String location;
    @NotNull
    public String phoneNumber ;
    @NotNull
    public Double xAxis ;
    @NotNull
    public Double yAxis ;


    public SignupCommand(String JsonInfo) throws JSONException {
        JSONObject jsonObject = new JSONObject(JsonInfo);
        email = jsonObject.getString("email");
        password = jsonObject.getString("password");
        username = jsonObject.getString("name");
        location = jsonObject.getString("location");
        phoneNumber = jsonObject.getString("phoneNumber");
        xAxis = jsonObject.getDouble("xAxis");
        yAxis = jsonObject.getDouble("yAxis");
    }
}
