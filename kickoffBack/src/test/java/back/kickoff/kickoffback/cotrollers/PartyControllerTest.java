package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.Commands.Add.CreateParty;
import back.kickoff.kickoffback.services.PartyServices;
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

import static org.junit.jupiter.api.Assertions.*;

class PartyControllerTest {
    @Mock
    PartyServices partyServices;
    PartyController partyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        partyController = new PartyController(partyServices);
    }

    @Test
    void createParty() throws JSONException {
        CreateParty createParty = new CreateParty(66L, "20", "5");
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("reservationId", 66L);
        hm.put("emptyplaces", "2");
        hm.put("fullplaces", "10");
        String information = new Gson().toJson(hm);
        CreateParty command = new CreateParty(information);
        Mockito.when(partyServices.CreateParty(command)).thenReturn(Boolean.TRUE);
        ResponseEntity<Boolean> response = partyController.CreateParty(information);
        assertEquals(new ResponseEntity<>(Boolean.TRUE, HttpStatus.CREATED), response);
    }

    @Test
    void joinParty() throws JSONException {
        String information = "";
        Mockito.when(partyServices.joinParty(information)).thenReturn(Boolean.FALSE);
        ResponseEntity<Boolean> response = partyController.JoinParty(information);
        assertEquals(new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST), response);
    }

    @Test
    void leaveParty() throws JSONException {
        String information = "";
        Mockito.when(partyServices.leaveParty(information)).thenReturn(Boolean.FALSE);
        ResponseEntity<Boolean> response = partyController.LeaveParty(information);
        assertEquals(new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST), response);
    }

    @Test
    void deleteParty() throws JSONException {
        String information = "";
        Mockito.when(partyServices.deleteParty(information)).thenReturn(Boolean.TRUE);
        ResponseEntity<Boolean> response = partyController.DeleteParty(information);
        assertEquals(new ResponseEntity<>(Boolean.TRUE, HttpStatus.CREATED), response);
    }

    @Test
    void getPartiesCourtowner() throws JSONException {
        String information = "";
        Mockito.when(partyServices.getCourtOwnerParties(information)).thenReturn(new ArrayList<>());
        ResponseEntity<String> response = partyController.GetPartiesCourtowner(information);
        assertEquals(new ResponseEntity<>(new Gson().toJson(new ArrayList<>()), HttpStatus.OK), response);
    }

    @Test
    void partiesCreatedbyPlayer() throws JSONException {
        String information = "";
        Mockito.when(partyServices.getPlayerCreatedParties(information)).thenReturn(new ArrayList<>());
        ResponseEntity<String> response = partyController.PartiesCreatedbyPlayer(information);
        assertEquals(new ResponseEntity<>(new Gson().toJson(new ArrayList<>()), HttpStatus.OK), response);
    }

    @Test
    void partiesJoinedbyPlayer() throws JSONException {
        String information = "";
        Mockito.when(partyServices.getPartiesofplayerJoined(information)).thenReturn(new ArrayList<>());
        ResponseEntity<String> response = partyController.PartiesJoinedbyPlayer(information);
        assertEquals(new ResponseEntity<>(new Gson().toJson(new ArrayList<>()), HttpStatus.OK), response);
    }

    @Test
    void playersJoinedParties() throws JSONException {
        String information = "";
        Mockito.when(partyServices.getplayersofParties(information)).thenReturn(new ArrayList<>());
        ResponseEntity<String> response = partyController.PlayersJoinedParties(information);
        assertEquals(new ResponseEntity<>(new Gson().toJson(new ArrayList<>()), HttpStatus.OK), response);
    }

    @Test
    void partiesNotJoinedbySubscribers() throws JSONException {
        String information = "";
        Mockito.when(partyServices.getPartiesSubscribed(information)).thenReturn(new ArrayList<>());
        ResponseEntity<String> response = partyController.PartiesNotJoinedbySubscribers(information);
        assertEquals(new ResponseEntity<>(new Gson().toJson(new ArrayList<>()), HttpStatus.OK), response);
    }
}