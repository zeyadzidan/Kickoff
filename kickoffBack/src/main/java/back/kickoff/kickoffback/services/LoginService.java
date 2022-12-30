package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.CourtOwnerFrontEnd;
import back.kickoff.kickoffback.Commands.LoginCommand;
import back.kickoff.kickoffback.Commands.PlayerFrontEnd;
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
import java.util.Optional;

@Service
public class LoginService {
    private final CourtOwnerRepository courtOwnerRepository;
    private final PlayerRepository playerRepository;

    public LoginService(CourtOwnerRepository courtOwnerRepository, PlayerRepository playerRepository) {
        this.courtOwnerRepository = courtOwnerRepository;
        this.playerRepository = playerRepository;
    }

    public CourtOwnerFrontEnd courtOwnerLogin(LoginCommand command) throws Exception {
        System.out.println(command.email);
        Optional<CourtOwner> courtOwner = courtOwnerRepository.findByEmail(command.email);
        if (courtOwner.isEmpty())
            throw new Exception("Not found");
        String password2 = courtOwner.get().getPassword();
        if (!command.password.equals(password2)) {
            throw new Exception("Not found Password");
        }
        CourtOwner co = courtOwner.get();
        return new CourtOwnerFrontEnd(co);
    }

    public PlayerFrontEnd playerLogin(LoginCommand command) throws Exception {
        Optional<Player> optionalPlayer = playerRepository.findByEmail(command.email);
        if(optionalPlayer.isEmpty())
            throw new Exception("Not found");
        String password2 =optionalPlayer.get().getPassword();
        if(!command.password.equals(password2))
        {
            throw new Exception("Incorrect Password");
        }
        Player player = optionalPlayer.get();

        return new PlayerFrontEnd(player);
    }
}
