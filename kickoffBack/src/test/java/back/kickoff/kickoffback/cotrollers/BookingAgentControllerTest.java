package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.services.BookingAgent;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

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

//    @Test
//    void cancelBooking() {
//        Long id = 66L;
//        Mockito.when(bookingAgent.cancelBookedReservation(id)).thenReturn(String.valueOf(55));
//        ResponseEntity<String> res = bookingAgentController.cancelBooking(id);
//        assertEquals(res, new ResponseEntity<>("55", HttpStatus.OK));
//    }
//
//    @Test
//    void cancelPending() {
//        Long id = 66L;
//        Mockito.when(bookingAgent.cancelPendingReservation(id)).thenReturn("Success");
//        ResponseEntity<String> res = bookingAgentController.cancelPending(id);
//        assertEquals(res, new ResponseEntity<>("Success", HttpStatus.OK));
//    }
}