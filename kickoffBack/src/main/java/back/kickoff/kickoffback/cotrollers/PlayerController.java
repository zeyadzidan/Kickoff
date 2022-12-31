package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.Commands.Operation.PlayerAddImageCommand;
import back.kickoff.kickoffback.repositories.PlayerRepository;
import back.kickoff.kickoffback.services.PlayerAgent;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/player")
public class PlayerController {
    private final PlayerAgent playerAgent;
    private final PlayerRepository playerRepository;

    public PlayerController(PlayerAgent playerAgent, PlayerRepository playerRepository) {
        this.playerAgent = playerAgent;
        this.playerRepository = playerRepository;
    }

    @PostMapping("/addImage")
    public ResponseEntity<String> addImage(@RequestBody String information) throws JSONException {
        PlayerAddImageCommand command ;
        try {
            command = new PlayerAddImageCommand(information, playerRepository) ;
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        playerAgent.addImage(command);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}

