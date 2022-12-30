package back.kickoff.kickoffback.cotrollers;


import back.kickoff.kickoffback.services.PartyServices;
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
@RequestMapping("/party")
public class PartyController {
    private final PartyServices partyServices;

    public PartyController(PartyServices partyServices) {
        this.partyServices = partyServices;
    }

    @PostMapping("/createparty")
    public ResponseEntity<Boolean> CreateParty(@RequestBody String jsonParty) throws JSONException {
        return (partyServices.CreateParty(jsonParty))
                ? new ResponseEntity<>(Boolean.TRUE, HttpStatus.CREATED)
                : new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/joinparty")
    public ResponseEntity<Boolean> JoinParty(@RequestBody String jsonParty) throws JSONException {
        return (partyServices.joinParty(jsonParty))
                ? new ResponseEntity<>(Boolean.TRUE, HttpStatus.CREATED)
                : new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/leaveparty")
    public ResponseEntity<Boolean> LeaveParty(@RequestBody String jsonParty) throws JSONException {
        return (partyServices.leaveParty(jsonParty))
                ? new ResponseEntity<>(Boolean.TRUE, HttpStatus.CREATED)
                : new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/deleteparty")
    public ResponseEntity<Boolean> DeleteParty(@RequestBody String jsonParty) throws JSONException {
        return (partyServices.deleteParty(jsonParty))
                ? new ResponseEntity<>(Boolean.TRUE, HttpStatus.CREATED)
                : new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/get_parties_of_courtOwner")
    public ResponseEntity<String> GetPartiesCourtowner(@RequestBody String jsonParty) throws JSONException {
        return new ResponseEntity<>(partyServices.getCourtOwnerParties(jsonParty),
                HttpStatus.OK);
    }
    @PostMapping("/get_parties_created_by_player")
    public ResponseEntity<String> PartiesCreatedbyPlayer(@RequestBody String jsonParty) throws JSONException {
        return new ResponseEntity<>(partyServices.getPlayerCreatedParties(jsonParty),
                HttpStatus.OK);
    }
    @PostMapping("/get_parties_joined_by_player")
    public ResponseEntity<String> PartiesJoinedbyPlayer(@RequestBody String jsonParty) throws JSONException {
        return new ResponseEntity<>(partyServices. getPlayerJoinedParties(jsonParty),
                HttpStatus.OK);
    }
}
