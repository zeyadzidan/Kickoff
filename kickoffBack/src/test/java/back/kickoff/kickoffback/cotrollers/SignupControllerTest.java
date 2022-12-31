package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.Commands.CourtOwnerFrontEnd;
import back.kickoff.kickoffback.Commands.PlayerFrontEnd;
import back.kickoff.kickoffback.Commands.SignupCommand;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
    void courtOwnerSignupRequest() throws Exception {
        CourtOwner newCourtOwner = new CourtOwner("Nasr CLub", "nasrClub@gmail.com", "12345678900",
                "01206555589", 44.5, 44.5);
        newCourtOwner.setRating(0);
        newCourtOwner.setLocation("Nasr CLub");
        CourtOwnerFrontEnd courtOwnerFrontEnd = new CourtOwnerFrontEnd(newCourtOwner);
        SignupCommand signupCommand = new SignupCommand("nasrClub@gmail.com", "12345678900",
                "Nasr CLub", "Nasr CLub green plaza", "01206555589", 44.5, 44.5);
        when(signupService.courtOwnerSignup(signupCommand)).thenReturn(courtOwnerFrontEnd);
        ResponseEntity<String> res = controller.courtOwnerSignupRequest(signupCommand);
        assertEquals(res, new ResponseEntity<>(new Gson().toJson(courtOwnerFrontEnd), HttpStatus.CREATED));
    }

    @Test
    void playerSignupRequest() throws Exception
    {
        SignupCommand signupCommand = new SignupCommand("cr7@gmail.com", "12345678900", "Cristiano Ronaldo", "Lisbon Portugal",
                "01176553539", 34.5, 24.5);
        Player newPlayer = new Player("Cristiano Ronaldo", "cr7@gmail.com", "01176553539",
                "12345678900", "Lisbon Portugal",34.5, 24.5);
        newPlayer.setPlayerType(PlayerType.Registered);
        PlayerFrontEnd playerFrontEnd =new PlayerFrontEnd(newPlayer);
        when(signupService.playerSignup(signupCommand)).thenReturn(playerFrontEnd);
        ResponseEntity<String> res =controller.playerSignupRequest(signupCommand);
        assertEquals(res, new ResponseEntity<>(new Gson().toJson(playerFrontEnd), HttpStatus.CREATED));
    }
    

}