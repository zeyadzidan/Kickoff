package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class SignupService
{
    private final CourtOwnerRepository courtOwnerRepository;

    public SignupService(CourtOwnerRepository courtOwnerRepository) {
        this.courtOwnerRepository = courtOwnerRepository;
    }

    public String courtOwnerSignup(String information) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(information);
        String email  =  jsonObject.getString("email");
        String password  =  jsonObject.getString("password");
        String username = jsonObject.getString("username");
        String location = jsonObject.getString("location");
        String phoneNumber = jsonObject.getString("phoneNumber");
        Double xAxis = jsonObject.getDouble("xAxis");
        Double yAxis = jsonObject.getDouble("yAxis");
        Optional<CourtOwner> courtOwner = courtOwnerRepository.findByEmail(email);
        String regex = "^(.+)@(.+)$";
       boolean isvalid =Pattern.compile(regex).matcher(email).matches();
        if(courtOwner.isPresent())
            return "Email exist";
        if( !isvalid)
            return "invalid" ;
        CourtOwner newCourtOwner = new CourtOwner(username, email, password, phoneNumber, xAxis, yAxis);
        newCourtOwner.setRating(0);
        newCourtOwner.setLocation(location);
        courtOwnerRepository.save(newCourtOwner);

        Map<String, Object> res = new HashMap<>() ;
        res.put("id", newCourtOwner.getId());
        res.put("userName", newCourtOwner.getUserName());
        res.put("email", newCourtOwner.getEmail());
        res.put("location", newCourtOwner.getLocation());
        res.put("rating", String.valueOf(newCourtOwner.getRating()));
        res.put("image", newCourtOwner.getImage());
        res.put("phoneNumber", newCourtOwner.getPhoneNumber());
        res.put("xAxis", newCourtOwner.getXAxis());
        res.put("yAxis", newCourtOwner.getYAxis());

        return new Gson().toJson(res);
    }
    
}
