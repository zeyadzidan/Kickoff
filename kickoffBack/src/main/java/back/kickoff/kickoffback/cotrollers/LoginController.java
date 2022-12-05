package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.services.EmptyJsonResponse;
import back.kickoff.kickoffback.services.LoginService;
import back.kickoff.kickoffback.services.SignupService;
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
    public LoginController(LoginService signupService)
    {
        this.loginService= signupService;
//        Optional<CourtOwner> cc = courtOwnerRepository.findByEmail()
    }
    @PostMapping("/courtOwner")
    public ResponseEntity courtOwnerLoginRequest(@RequestBody String information) throws JSONException {
        String ans = loginService.courtOwnerLogin(information);
        if(ans.equals("Not found"))
        {
            System.out.println(new EmptyJsonResponse());
            return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.BAD_REQUEST);
        }
        else if(ans.equals("Not found Password"))
        {
            JSONObject jsonObject = new JSONObject();
            System.out.println("not correct");
            jsonObject.put("Password", "not found");
            return new ResponseEntity<>(jsonObject, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ans, HttpStatus.OK);
    }
}
