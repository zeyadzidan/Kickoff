package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.model.CourtOwner;
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
    public SignupController(SignupService signupService)
    {
        this.signupService = signupService;
//        Optional<CourtOwner> cc = courtOwnerRepository.findByEmail()
    }
    @PostMapping("/courtOwner")
    public ResponseEntity<String> courtOwnerSignupRequest(@RequestBody String information) throws JSONException {
        System.out.println("ban7bk ya youssry");
        String response = signupService.courtOwnerSignup(information) ;
        System.out.println(response);
        if(response.equals("Email exist") || response.equals("invalid") )
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
