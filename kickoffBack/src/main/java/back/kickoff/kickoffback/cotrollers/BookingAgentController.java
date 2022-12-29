package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.Commands.*;
import back.kickoff.kickoffback.repositories.PlayerRepository;
import back.kickoff.kickoffback.services.BookingAgent;
import com.google.gson.Gson;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/BookingAgent")
public class BookingAgentController {
    private final BookingAgent bookingAgent;
    private final PlayerRepository playerRepository ;
    public BookingAgentController(BookingAgent bookingAgent, PlayerRepository playerRepository) {
        this.bookingAgent = bookingAgent;
        this.playerRepository = playerRepository;
    }

    @PostMapping("/setPending")
    public ResponseEntity<String> setPending(@RequestBody String information) {
        try {
            SetPendingCommand command = new SetPendingCommand(information, playerRepository) ;
            bookingAgent.setPending(command);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @PostMapping("/booking")
    public ResponseEntity<String> book(@RequestBody String information) throws JSONException {
        try {
            BookCommand command = new BookCommand(information) ;
            bookingAgent.book(command);

        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping("/cancelBooking")
    public ResponseEntity<String> cancelReservation(@RequestBody String information) throws JSONException {
        String responseBody ;
        try {
            responseBody = bookingAgent.cancelBookedReservation(information);
            Integer cost = Integer.parseInt(responseBody);
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/cancelPending")
    public ResponseEntity<String> cancelPending(@RequestBody String information) throws Exception {
        try {
            bookingAgent.cancelPendingReservation(information);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping("/reservationsOnDate")
    public ResponseEntity<String> getReservations(@RequestBody String information) throws JSONException {
        try {
            GetReservationCommand command = new GetReservationCommand(information );
            List<FrontEndReservation> response = bookingAgent.getReservations(command);
            return new ResponseEntity<>(new Gson().toJson(response), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }
    @PostMapping("/sendReceipt")
    public ResponseEntity<String> sendReceipt(@RequestBody String information) {
        try {
            ReceiptCommand receiptCommand= new ReceiptCommand(information) ;
            bookingAgent.sendReceipt(receiptCommand) ;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Receipt Sent", HttpStatus.OK);
    }

    @PostMapping("/playerBookings")
    public ResponseEntity<Object> getPlayerReservations(@RequestBody String information) throws JSONException {
        try {
            GetPlayerReservationCommand command = new GetPlayerReservationCommand(information) ;
            List<FrontEndReservation> reservations = bookingAgent.getPlayerReservations(command);
            return new ResponseEntity<>(new Gson().toJson(reservations), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
