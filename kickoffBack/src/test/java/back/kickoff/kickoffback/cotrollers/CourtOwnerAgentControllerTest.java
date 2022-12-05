package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.model.Court;
import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.services.CourtOwnerAgent;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CourtOwnerAgentControllerTest {
    CourtOwnerAgentController courtOwnerAgentController;
    @Mock
    CourtOwnerAgent courtOwnerAgent;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        courtOwnerAgentController = new CourtOwnerAgentController(courtOwnerAgent);
    }

    @Test
    void listCourts() {
        Mockito.when(courtOwnerAgent.findCourtOwnerCourts(1L)).thenReturn(String.valueOf(new ArrayList<Court>()));
        ResponseEntity<String> res = courtOwnerAgentController.listCourts("1");
        assertEquals(res, new ResponseEntity<>("[]", HttpStatus.OK));
    }

    @Test
    void createCourt() throws JSONException {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("ownerID", 1L);
        hm.put("courtName", "A");
        hm.put("description", "grass with 5 players in each team");
        hm.put("morningCost", 100);
        hm.put("nightCost", 150);
        hm.put("minBookingHours", 1);
        hm.put("startWorkingHours", 1);
        hm.put("finishWorkingHours", 21);
        String information = new Gson().toJson(hm);
        Mockito.when(courtOwnerAgent.createCourt(information)).thenReturn("Success");
        ResponseEntity<String> res = courtOwnerAgentController.createCourt(information);
        assertEquals(res, new ResponseEntity<>("Court added", HttpStatus.CREATED));
    }

    @Test
    void addImage() throws JSONException {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("ownerID", 1L) ;
        hm.put("imageURL", "thisIsAnImage.com") ;
        String information = new Gson().toJson(hm);
        Mockito.when(courtOwnerAgent.addImage(information)).thenReturn("Success");

        ResponseEntity<String> res = courtOwnerAgentController.addImage(information);

        assertEquals(res, new ResponseEntity<>("Success", HttpStatus.OK));
    }
}