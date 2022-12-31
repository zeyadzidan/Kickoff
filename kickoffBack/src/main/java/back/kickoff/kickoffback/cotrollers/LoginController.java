package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.Commands.CourtOwnerFrontEnd;
import back.kickoff.kickoffback.Commands.LoginCommand;
import back.kickoff.kickoffback.Commands.PlayerFrontEnd;
import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.services.EmptyJsonResponse;
import back.kickoff.kickoffback.services.LoginService;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService signupService) {
        this.loginService = signupService;
//        Optional<CourtOwner> cc = courtOwnerRepository.findByEmail()
    }

    @PostMapping("/courtOwner")
    public ResponseEntity courtOwnerLoginRequest(@RequestBody String information) throws JSONException {
        try {
            LoginCommand command = new LoginCommand(information) ;
            CourtOwnerFrontEnd courtOwner = loginService.courtOwnerLogin(command) ;
            return new ResponseEntity<>(new Gson().toJson(courtOwner)  , HttpStatus.OK);

        } catch (Exception e) {
            String ans = e.getMessage();
            if (ans.equals("Not found Password")) {
                JSONObject jsonObject = new JSONObject();
                System.out.println("not correct");
                jsonObject.put("Password", "not found");
                return new ResponseEntity<>(jsonObject, HttpStatus.BAD_REQUEST);
            }else{
                System.out.println(new EmptyJsonResponse());
                return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping("/player")
    public ResponseEntity playerLoginRequest(@RequestBody String information) throws JSONException {
        try {
            LoginCommand command = new LoginCommand(information) ;
            PlayerFrontEnd player = loginService.playerLogin(command) ;
            return new ResponseEntity<>(new Gson().toJson(player)  , HttpStatus.OK);

        } catch (Exception e) {
            String ans = e.getMessage();
            if (ans.equals("Not found Password")) {
                JSONObject jsonObject = new JSONObject();
                System.out.println("not correct");
                jsonObject.put("Password", "not found");
                return new ResponseEntity<>(jsonObject, HttpStatus.BAD_REQUEST);
            }else{
                System.out.println(new EmptyJsonResponse());
                return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.BAD_REQUEST);
            }
        }
    }
}
