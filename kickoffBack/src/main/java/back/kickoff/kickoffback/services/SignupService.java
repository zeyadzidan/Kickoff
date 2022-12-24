package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.model.PlayerType;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.repositories.PlayerRepository;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class SignupService {
    private final CourtOwnerRepository courtOwnerRepository;
    private final PlayerRepository playerRepository;

    public SignupService(CourtOwnerRepository courtOwnerRepository, PlayerRepository playerRepository) {
        this.courtOwnerRepository = courtOwnerRepository;
        this.playerRepository = playerRepository;
    }

    public String courtOwnerSignup(String information) throws JSONException {
        JSONObject jsonObject = new JSONObject(information);
        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");
        String username = jsonObject.getString("name");
        String location = jsonObject.getString("location");
        String phoneNumber = jsonObject.getString("phoneNumber");
        Double xAxis = jsonObject.getDouble("xAxis");
        Double yAxis = jsonObject.getDouble("yAxis");
        Optional<CourtOwner> courtOwner = courtOwnerRepository.findByEmail(email);
        String regex = "^(.+)@(.+)$";
        boolean isvalid = Pattern.compile(regex).matcher(email).matches();
        if (courtOwner.isPresent())
            return "Email exist";
        if (!isvalid)
            return "invalid";
        CourtOwner newCourtOwner = new CourtOwner(username, email, password, phoneNumber, xAxis, yAxis);
        newCourtOwner.setRating(0);
        newCourtOwner.setLocation(location);
        courtOwnerRepository.save(newCourtOwner);

        Map<String, Object> res = new HashMap<>();
        res.put("id", newCourtOwner.getId());
        res.put("name", newCourtOwner.getUserName());
        res.put("email", newCourtOwner.getEmail());
        res.put("location", newCourtOwner.getLocation());
        res.put("rating", String.valueOf(newCourtOwner.getRating()));
        res.put("image", newCourtOwner.getImage());
        res.put("phoneNumber", newCourtOwner.getPhoneNumber());
        res.put("xAxis", newCourtOwner.getXAxis());
        res.put("yAxis", newCourtOwner.getYAxis());

        return new Gson().toJson(res);
    }

    public String playerSignup(String information) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(information);
        String email  =  jsonObject.getString("email");
        String password  =  jsonObject.getString("password");
        String name = jsonObject.getString("name");
        String location = jsonObject.getString("location");
        String phoneNumber = jsonObject.getString("phoneNumber");
        Double xAxis = jsonObject.getDouble("xAxis");
        Double yAxis = jsonObject.getDouble("yAxis");
        Optional<Player> player = playerRepository.findByEmail(email);
        String regex = "^(.+)@(.+)$";
        boolean valid =Pattern.compile(regex).matcher(email).matches();
        System.out.println("youssryTaha 2adwtna");
        if(player.isPresent())
            return "Email exist";
        if(!valid)
            return "invalid" ;
        Player newPlayer = new Player(name, email, phoneNumber,password, location, xAxis, yAxis);
        newPlayer.setPlayerType(PlayerType.Registered);
        playerRepository.save(newPlayer);
        Map<String, Object> res = new HashMap<>() ;
        res.put("id", newPlayer.getId());
        res.put("name", newPlayer.getName());
        res.put("email", newPlayer.getEmail());
        res.put("location", newPlayer.getLocation());
        res.put("image", newPlayer.getImage());
        res.put("phoneNumber", newPlayer.getPhoneNumber());
        res.put("xAxis", newPlayer.getXAxis());
        res.put("yAxis", newPlayer.getYAxis());

        return new Gson().toJson(res);
    }
}
