package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.services.BookingAgent;
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
@RequestMapping("/BookingAgent")
public class BookingAgentController {
    private final BookingAgent bookingAgent;

    public BookingAgentController(BookingAgent bookingAgent) {
        this.bookingAgent = bookingAgent;
    }

    @PostMapping("/setPending")
    public ResponseEntity<String> setPending(@RequestBody String information) throws JSONException {
        String responseBody = bookingAgent.setPending(information);
        if (!responseBody.equals("Success"))
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PostMapping("/booking")
    public ResponseEntity<String> book(@RequestBody String information) throws JSONException {
        String responseBody = bookingAgent.book(information);
        if (!responseBody.equals("Success"))
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/cancelBooking")
    public ResponseEntity<String> cancelReservation(@RequestBody String information) throws JSONException {
        String responseBody = bookingAgent.cancelBookedReservation(information);
        try {
            Integer cost = Integer.parseInt(responseBody);
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/cancelPending")
    public ResponseEntity<String> cancelPending(@RequestBody String information) throws JSONException {
        String responseBody = bookingAgent.cancelPendingReservation(information);
        if (!responseBody.equals("Success"))
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/reservationsOnDate")
    public ResponseEntity<String> getReservations(@RequestBody String information) throws JSONException {
        String responseBody = bookingAgent.getReservations(information);
        if (responseBody.charAt(0) != 'S')
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        responseBody = responseBody.substring(2);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }


}
