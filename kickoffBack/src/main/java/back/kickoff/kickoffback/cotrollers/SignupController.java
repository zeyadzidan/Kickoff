package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.Commands.CourtOwnerFrontEnd;
import back.kickoff.kickoffback.Commands.PlayerFrontEnd;
import back.kickoff.kickoffback.Commands.SignupCommand;
import back.kickoff.kickoffback.services.SignupService;
import com.google.gson.Gson;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("/signup")
public class SignupController {
    private final SignupService signupService;

    public SignupController(SignupService signupService) {
        this.signupService = signupService;
//        Optional<CourtOwner> cc = courtOwnerRepository.findByEmail()
    }

    @PostMapping("/courtOwner")
    public ResponseEntity<String> courtOwnerSignupRequest(@RequestBody String information) {
        try{
            SignupCommand command = new SignupCommand(information) ;
            CourtOwnerFrontEnd courtOwner = signupService.courtOwnerSignup(command) ;
            return new ResponseEntity<>(new Gson().toJson(courtOwner), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/player")
    public ResponseEntity<String> playerSignupRequest(@RequestBody String information) {
        try {
            SignupCommand command = new SignupCommand(information);
            PlayerFrontEnd player = signupService.playerSignup(command);
            return new ResponseEntity<>(new Gson().toJson(player), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
