package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.services.CourtOwnerAgent;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/courtOwnerAgent")
public class CourtOwnerAgentController {
    private final CourtOwnerAgent courtOwnerAgent;

    public CourtOwnerAgentController(CourtOwnerAgent courtOwnerAgent) {
        this.courtOwnerAgent = courtOwnerAgent;
    }

    @GetMapping("/CourtOwner/{courtOwnerId}/Courts")
    public ResponseEntity<String> listCourts(@PathVariable String courtOwnerId)
    {
        return new ResponseEntity<>(courtOwnerAgent.
                findCourtOwnerCourts(Long.valueOf(courtOwnerId)),
                HttpStatus.OK);
    }
    @PostMapping("/CourtOwner/CreateCourt")
    public ResponseEntity<String> createCourt(@RequestBody String information) throws JSONException
    {
        if(courtOwnerAgent.createCourt(information).equals("Success"))
            return new ResponseEntity<>("Court added", HttpStatus.CREATED);
        return new ResponseEntity<>("Court Owner not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/CourtOwner/addImage")
    public ResponseEntity<String> addImage(@RequestBody String information) throws JSONException
    {
        String responseBody = courtOwnerAgent.addImage(information);

        if(responseBody.equals("Success"))
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    
}
