package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignupService
{
    private final CourtOwnerRepository courtOwnerRepository;

    public SignupService(CourtOwnerRepository courtOwnerRepository) {
        this.courtOwnerRepository = courtOwnerRepository;
    }

    public Boolean courtOwnerSignup(String information) throws JSONException
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
        if(courtOwner.isPresent())
            return false;
        CourtOwner newCourtOwner = new CourtOwner(username, email, password, phoneNumber, xAxis, yAxis);
        newCourtOwner.setRating(0);
        newCourtOwner.setLocation(location);
        courtOwnerRepository.save(newCourtOwner);
        return true;
    }
    
}
