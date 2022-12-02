package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.services.BookingAgent;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/BookingAgent")
public class BookingAgentController {
    private final BookingAgent bookingAgent;

    public BookingAgentController(BookingAgent bookingAgent) {
        this.bookingAgent = bookingAgent;
    }

    @PostMapping("/setPending")
    public ResponseEntity<String> setPending(@RequestBody String information) throws JSONException {
        String responseBody = bookingAgent.setPending(information);
        if(!responseBody.equals("Success"))
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }
    @PostMapping("/booking")
    public ResponseEntity<String> book(@RequestBody String information) throws JSONException
    {
        String responseBody = bookingAgent.book(information);
        if(!responseBody.equals("Success"))
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
    @GetMapping("/cancelBooking")
    public ResponseEntity<String> cancelBooking(@RequestParam Long reservationId)
    {
        String responseBody = bookingAgent.cancelBookedReservation(reservationId);
        try{
            Integer cost = Integer.parseInt(responseBody);
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cancelPending")
    public ResponseEntity<String> cancelPending(@RequestParam Long reservationId)
    {
        String responseBody = bookingAgent.cancelPendingReservation(reservationId);
        if(!responseBody.equals("Success"))
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
