package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.Commands.*;
import back.kickoff.kickoffback.services.AnnouncementService;
import back.kickoff.kickoffback.services.CourtOwnerAgent;
import com.google.gson.Gson;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/courtOwnerAgent")
public class CourtOwnerAgentController {
    private final CourtOwnerAgent courtOwnerAgent;
    private final AnnouncementService announcementService;

    public CourtOwnerAgentController(CourtOwnerAgent courtOwnerAgent, AnnouncementService announcementService) {
        this.courtOwnerAgent = courtOwnerAgent;
        this.announcementService = announcementService;
    }


    @GetMapping("/CourtOwner/{courtOwnerId}/Courts")
    public ResponseEntity<String> listCourts(@PathVariable String courtOwnerId) {
        try {
            List<CourtFrontEnd> list = courtOwnerAgent.findCourtOwnerCourts(Long.valueOf(courtOwnerId)) ;
            return new ResponseEntity<>(new  Gson().toJson(list),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/CourtOwner/CreateCourt")
    public ResponseEntity<String> createCourt(@RequestBody String information) {
        try {
            CreateCourtCommand command = new CreateCourtCommand(information) ;
            courtOwnerAgent.createCourt(command);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

    @PostMapping("/CourtOwner/addImage")
    public ResponseEntity<String> addImage(@RequestBody String information) {
        try {
            AddImageCommand command = new AddImageCommand(information) ;
            courtOwnerAgent.addImage(command);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }


    @PostMapping("/CourtOwner/CreateAnnouncement")
    public ResponseEntity<String> CreateAnnouncement(@RequestBody String information) {
        AddAnnouncementCommand command ;
        try{
            command = new AddAnnouncementCommand(information) ;
            announcementService.addAnnouncement(command);

        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }


    @GetMapping("/CourtOwner/{courtOwnerId}/Announcements")
    public ResponseEntity<String> getAnnouncements(@PathVariable String courtOwnerId) {
        List<AnnouncmentFrontend> responseBody ;
        try {
            responseBody = announcementService.getAnnouncement(Long.valueOf(courtOwnerId));
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Gson().toJson(responseBody), HttpStatus.OK);
    }


    @PostMapping("/CourtOwner/{courtOwnerId}/deleteAnnouncement")
    public ResponseEntity<String> deleteAnnouncements(@PathVariable String courtOwnerId, @RequestBody String information) throws JSONException {
        String responseBody = announcementService.deleteAnnouncement(Long.valueOf(courtOwnerId), information);
        if (responseBody.equals("Success"))
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);

    }


}
