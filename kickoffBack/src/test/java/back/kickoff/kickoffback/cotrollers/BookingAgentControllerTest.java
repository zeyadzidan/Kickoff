package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.services.BookingAgent;
import back.kickoff.kickoffback.services.SignupService;
import com.google.gson.Gson;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static reactor.core.publisher.Mono.when;

class BookingAgentControllerTest {
    BookingAgentController bookingAgentController;
    @Mock
    BookingAgent bookingAgent;
    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        bookingAgentController = new BookingAgentController(bookingAgent);
    }
    @Test
    void setPending() throws JSONException {
        HashMap<String, Object> hm = new HashMap<>();
        String information = new Gson().toJson(hm);
        Mockito.when(bookingAgent.setPending(information)).thenReturn("Success");
        ResponseEntity<String> res = bookingAgentController.setPending(information);
        assertEquals(res, new ResponseEntity<>("Success", HttpStatus.CREATED));
    }

    @Test
    void book() throws JSONException {
        HashMap<String, Object> hm = new HashMap<>();
        String information = new Gson().toJson(hm);
        Mockito.when(bookingAgent.book(information)).thenReturn("Success");
        ResponseEntity<String> res = bookingAgentController.book(information);
        assertEquals(res, new ResponseEntity<>("Success", HttpStatus.OK));
    }

    @Test
    void cancelBooking() throws JSONException {
        Long id = 66L;
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("id", id) ;
        String information = new Gson().toJson(hm);
        Mockito.when(bookingAgent.cancelBookedReservation(information)).thenReturn(String.valueOf(55));
        ResponseEntity<String> res = bookingAgentController.cancelReservation(information);
        assertEquals(res, new ResponseEntity<>("55", HttpStatus.OK));
    }

    @Test
    void cancelPending() throws JSONException {
        Long id = 66L;

        HashMap<String, Object> hm = new HashMap<>();
        hm.put("id", id) ;
        String information = new Gson().toJson(hm);
        Mockito.when(bookingAgent.cancelPendingReservation(information)).thenReturn("Success");
        ResponseEntity<String> res = bookingAgentController.cancelPending(information);
        assertEquals(res, new ResponseEntity<>("Success", HttpStatus.OK));
    }
}