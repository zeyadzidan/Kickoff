package back.kickoff.kickoffback.Commands;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupCommand {
    public String email ;
    public String password ;
    public String username ;
    public String location;
    public String phoneNumber ;
    public Double xAxis ;
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
