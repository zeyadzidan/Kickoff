package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;
@Service
public class LoginService {
    private final CourtOwnerRepository courtOwnerRepository;

    public LoginService(CourtOwnerRepository courtOwnerRepository) {
        this.courtOwnerRepository = courtOwnerRepository;
    }
    public String courtOwnerLogin(String information) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(information);
        String email  =  jsonObject.getString("email");
        String password  =  jsonObject.getString("password");
        System.out.println(email);
        Optional<CourtOwner> courtOwner = courtOwnerRepository.findByEmail(email);
        if(courtOwner.isEmpty())
            return "Not found";
       String password2 =courtOwner.get().getPassword();
       if(!password.equals(password2))
       {
           return "Not found Password";
       }
        return new Gson().toJson(courtOwner.get());
    }
}
