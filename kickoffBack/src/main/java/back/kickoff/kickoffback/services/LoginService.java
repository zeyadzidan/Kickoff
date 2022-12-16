package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.repositories.PlayerRepository;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
@Service
public class LoginService {
    private final CourtOwnerRepository courtOwnerRepository;
    private final PlayerRepository playerRepository;

    public LoginService(CourtOwnerRepository courtOwnerRepository, PlayerRepository playerRepository) {
        this.courtOwnerRepository = courtOwnerRepository;
        this.playerRepository = playerRepository;
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
       Map<String, Object> res = new HashMap<>() ;
       CourtOwner co = courtOwner.get();
       res.put("id", co.getId());
       res.put("userName", co.getUserName());
       res.put("email", co.getEmail());
       res.put("location", co.getLocation());
       res.put("rating", String.valueOf(co.getRating()));
       res.put("image", co.getImage());
       res.put("phoneNumber", co.getPhoneNumber());
       res.put("xAxis", co.getXAxis());
       res.put("yAxis", co.getYAxis());

        return new Gson().toJson(res);
    }
    public String playerLogin(String information) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(information);
        String email  =  jsonObject.getString("email");
        String password  =  jsonObject.getString("password");
        Optional<Player> optionalPlayer = playerRepository.findByEmail(email);
        if(optionalPlayer.isEmpty())
            return "Not found";
        String password2 =optionalPlayer.get().getPassword();
        if(!password.equals(password2))
        {
            return "Incorrect Password";
        }
        Map<String, Object> hm = new HashMap<>() ;
        Player player = optionalPlayer.get();
        hm.put("email", player.getEmail());
        hm.put("id", player.getId());
        hm.put("name", player.getName());
        hm.put("image", player.getImage());
        hm.put("phoneNumber", player.getPhoneNumber());
        hm.put("location", player.getLocation());
        hm.put("xAxis", player.getXAxis());
        hm.put("yAxis", player.getYAxis());
        return new Gson().toJson(hm);
    }
}
