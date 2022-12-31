package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.Commands.BookCommand;
import back.kickoff.kickoffback.Commands.FrontEndReservation;
import back.kickoff.kickoffback.Commands.GetReservationCommand;
import back.kickoff.kickoffback.Commands.SetPendingCommand;
import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.repositories.PlayerRepository;
import back.kickoff.kickoffback.services.BookingAgent;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BookingAgentControllerTest {
    BookingAgentController bookingAgentController;
    @Mock
    BookingAgent bookingAgent;
    @Mock
    PlayerRepository playerRepository ;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        bookingAgentController = new BookingAgentController(bookingAgent, playerRepository);
    }

    String setupSetPendingCommand() throws Exception {
        JSONObject jsonObject = new JSONObject()
                .put("courtId", 1L)
                .put("courtOwnerId", 1L)
                .put("startDate", "2/15/2023")
                .put("endDate", "2/15/2023")
                .put("startHour", 9)
                .put("finishHour", 23)
                .put("playerId", 1L);
        return jsonObject.toString();

    }

    @Test
    void setPendingTest() throws Exception {
        String information = setupSetPendingCommand();
        Player player = new Player();
        player.setId(1L);

        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        SetPendingCommand command = new SetPendingCommand(information,playerRepository);
        when(bookingAgent.setPending(command)).thenReturn(true);
        ResponseEntity<String> res = bookingAgentController.setPending(information);

        assertEquals(res, new ResponseEntity<>("Success", HttpStatus.CREATED));
    }
    String setUpBook() throws JSONException {
        JSONObject jsonObject = new JSONObject()
                .put("reservationId", 1L)
                .put("moneyPaid", 200);
        return jsonObject.toString();
    }

    @Test
    void bookTest() throws Exception {
        String information = setUpBook();
        BookCommand command = new BookCommand(information);

        when(bookingAgent.book(command)).thenReturn(true);
        ResponseEntity<String> res = bookingAgentController.book(information);

        assertEquals(res, new ResponseEntity<>("Success", HttpStatus.OK));
    }

    @Test
    void cancelBookingTest() throws Exception {
        JSONObject jsonObject = new JSONObject().put("id", 1L);
        String information = jsonObject.toString();

        Mockito.when(bookingAgent.cancelBookedReservation(information)).thenReturn(String.valueOf(55));
        ResponseEntity<String> res = bookingAgentController.cancelReservation(information);
        assertEquals(res, new ResponseEntity<>("55", HttpStatus.OK));
    }

    @Test
    void cancelPendingTest() throws Exception {
        JSONObject jsonObject = new JSONObject().put("id", 1L);
        String information = jsonObject.toString();

        Mockito.when(bookingAgent.cancelPendingReservation(information)).thenReturn(true);
        ResponseEntity<String> res = bookingAgentController.cancelPending(information);
        assertEquals(res, new ResponseEntity<>("Success", HttpStatus.OK));
    }

    String setUpgetReservations() throws JSONException {
        JSONObject jsonObject = new JSONObject()
                .put("courtId", 1L)
                .put("courtOwnerId", 1L)
                .put("ascending", true)
                .put("date", "2/24/2023")
                .put("filter", "");
        return jsonObject.toString();
    }

    List<FrontEndReservation> returnOfgetReservations(){
        List<FrontEndReservation> reservations = new ArrayList<>() ;
        reservations.add(new FrontEndReservation());
        reservations.add(new FrontEndReservation());
        return reservations;
    }

    @Test
    void getReservationsTest() throws Exception {
        String information = setUpgetReservations();
        GetReservationCommand command = new GetReservationCommand(information);
        List<FrontEndReservation> reservations = returnOfgetReservations();

        when(bookingAgent.getReservations(command)).thenReturn(reservations);
        ResponseEntity<String> res = bookingAgentController.getReservations(information);

        assertEquals(res, new ResponseEntity<>(new Gson().toJson(reservations), HttpStatus.OK));

    }


}