package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.services.SearchAgent;
import back.kickoff.kickoffback.services.SignupService;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/search")
public class SearchAgentController {
    private final SearchAgent searchAgent;

    public SearchAgentController(SearchAgent searchAgent) {
        this.searchAgent = searchAgent;
    }
    @GetMapping("/courtOwner/distance")
    public ResponseEntity<String>  searchNearestCourtOwner(@RequestBody String information) throws JSONException {
        return new ResponseEntity<>(searchAgent.getNearestCourtOwners(information),
                HttpStatus.OK);
    }
}
