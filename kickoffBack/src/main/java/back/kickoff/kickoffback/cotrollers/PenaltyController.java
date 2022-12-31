package back.kickoff.kickoffback.cotrollers;


import back.kickoff.kickoffback.Commands.Add.AddPenaltyCommand;
import back.kickoff.kickoffback.services.PenaltyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("/report")
public class PenaltyController {
    private final PenaltyService penaltyService;

    public PenaltyController(PenaltyService penaltyService) {
        this.penaltyService = penaltyService;
    }



    @PostMapping("/CtoP")
    public ResponseEntity<String> addReportFromCourtOwner(@RequestBody String information) {
        try {
            AddPenaltyCommand command = new AddPenaltyCommand(information, true) ;
            boolean res = penaltyService.addReport(command);
            return new ResponseEntity<>(String.valueOf(res), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }
    }

    @PostMapping("/PtoP")
    public ResponseEntity<String> addReportFromPlayer(@RequestBody String information) {
        try {
            AddPenaltyCommand command = new AddPenaltyCommand(information, false) ;
            boolean res = penaltyService.addReport(command);
            return new ResponseEntity<>(String.valueOf(res), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }
    }
}
