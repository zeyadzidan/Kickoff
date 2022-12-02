package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.services.LoginService;
import back.kickoff.kickoffback.services.SignupService;
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
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;
    public LoginController(LoginService signupService)
    {
        this.loginService= signupService;
//        Optional<CourtOwner> cc = courtOwnerRepository.findByEmail()
    }
    @PostMapping("/courtOwner")
    public ResponseEntity<String> courtOwnerSignupRequest(@RequestBody String information) throws JSONException {
        System.out.println("ban7bk ya youssry");
        String ans = loginService.courtOwnerLogin(information);
        if(ans.equals("Not found"))
            return new ResponseEntity<>("0", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(ans, HttpStatus.OK);
    }
}
