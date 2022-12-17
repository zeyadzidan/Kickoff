package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.model.PlayerType;
import back.kickoff.kickoffback.services.SignupService;

import com.google.gson.Gson;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SignupControllerTest {
    SignupController controller;
    @Mock
    SignupService signupService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        controller = new SignupController(signupService);
    }

    @Test
    void courtOwnerSignupRequest() throws JSONException {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("email", "nasrClub@gmail.com");
        hm.put("password", "12345678900");
        hm.put("username", "Nasr CLub");
        hm.put("location", "Nasr CLub green plaza");
        hm.put("phoneNumber", "01206555589");
        hm.put("xAxis", 44.5);
        hm.put("yAxis", 44.5);
        String information = new Gson().toJson(hm);
        CourtOwner newCourtOwner = new CourtOwner("Nasr CLub", "nasrClub@gmail.com", "12345678900",
                "01206555589", 44.5, 44.5);
        newCourtOwner.setRating(0);
        newCourtOwner.setLocation("Nasr CLub");
        when(signupService.courtOwnerSignup(information)).thenReturn(new Gson().toJson(newCourtOwner));
        ResponseEntity<String> res = controller.courtOwnerSignupRequest(information);
        assertEquals(res, new ResponseEntity<>(new Gson().toJson(newCourtOwner), HttpStatus.CREATED));
    }

    @Test
    void playerSignupRequest() throws JSONException {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("email", "cr7@gmail.com");
        hm.put("password", "12345678900");
        hm.put("name", "Cristiano Ronaldo");
        hm.put("location", "Lisbon Portugal");
        hm.put("phoneNumber", "01176553539");
        hm.put("xAxis", 34.5);
        hm.put("yAxis", 24.5);
        String information = new Gson().toJson(hm);
        Player newPlayer = new Player("Cristiano Ronaldo", "cr7@gmail.com", "01176553539",
                "12345678900", "Lisbon Portugal",34.5, 24.5);
        newPlayer.setPlayerType(PlayerType.Registered);
        when(signupService.playerSignup(information)).thenReturn(new Gson().toJson(newPlayer));
        ResponseEntity<String> res =controller.playerSignupRequest(information);
        assertEquals(res, new ResponseEntity<>(new Gson().toJson(newPlayer), HttpStatus.CREATED));
    }
}