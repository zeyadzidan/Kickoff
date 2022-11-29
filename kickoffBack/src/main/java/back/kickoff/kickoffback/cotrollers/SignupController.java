package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.services.SignupService;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public String courtOwnerSignupRequest(@RequestBody String information) throws JSONException {
        if(signupService.courtOwnerSignup(information))
            return "Success";
        return "failure";
    }
}
