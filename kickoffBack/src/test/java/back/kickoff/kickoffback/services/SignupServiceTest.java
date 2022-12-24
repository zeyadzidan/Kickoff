package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.model.PlayerType;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.repositories.PlayerRepository;
import com.google.gson.Gson;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SignupServiceTest {

    SignupService signupService;

    @Mock
    CourtOwnerRepository courtOwnerRepository;
    @Mock
    PlayerRepository playerRepository;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        signupService = new SignupService(courtOwnerRepository, playerRepository);
    }

    @Test
    void courtOwnerSignup() throws JSONException {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("email", "nasrClub@gmail.com");
        hm.put("password", "12345678900");
        hm.put("name", "Nasr CLub");
        hm.put("location", "Nasr CLub");
        hm.put("phoneNumber", "01206555589");
        hm.put("xAxis", 44.5);
        hm.put("yAxis", 44.5);
        String information = new Gson().toJson(hm);
        when(courtOwnerRepository.save(new CourtOwner())).thenReturn(new CourtOwner());
        String result = signupService.courtOwnerSignup(information);
        CourtOwner newCourtOwner = new CourtOwner("Nasr CLub", "nasrClub@gmail.com", "12345678900",
                "01206555589", 44.5, 44.5);
        newCourtOwner.setRating(0);
        newCourtOwner.setLocation("Nasr CLub");

        Map<String, Object> res = new HashMap<>();
        res.put("id", newCourtOwner.getId());
        res.put("name", newCourtOwner.getUserName());
        res.put("email", newCourtOwner.getEmail());
        res.put("location", newCourtOwner.getLocation());
        res.put("rating", String.valueOf(newCourtOwner.getRating()));
        res.put("image", newCourtOwner.getImage());
        res.put("phoneNumber", newCourtOwner.getPhoneNumber());
        res.put("xAxis", newCourtOwner.getXAxis());
        res.put("yAxis", newCourtOwner.getYAxis());

        assertEquals(new Gson().toJson(res), result);
    }

    @Test
    void playerSignup() throws JSONException {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("email", "cr7@gmail.com");
        hm.put("password", "12345678900");
        hm.put("name", "Cristiano Ronaldo");
        hm.put("location", "Lisbon Portugal");
        hm.put("phoneNumber", "01176553539");
        hm.put("xAxis", 34.5);
        hm.put("yAxis", 24.5);
        String information = new Gson().toJson(hm);
        when(playerRepository.save(new Player())).thenReturn(new Player());
        String result = signupService.playerSignup(information);
        Player newPlayer = new Player("Cristiano Ronaldo", "cr7@gmail.com", "01176553539",
                "12345678900", "Lisbon Portugal",34.5, 24.5);
        newPlayer.setPlayerType(PlayerType.Registered);
        Map<String, Object> res = new HashMap<>() ;
        res.put("id", newPlayer.getId());
        res.put("name", newPlayer.getName());
        res.put("email", newPlayer.getEmail());
        res.put("location", newPlayer.getLocation());
        res.put("image", newPlayer.getImage());
        res.put("phoneNumber", newPlayer.getPhoneNumber());
        res.put("xAxis", newPlayer.getXAxis());
        res.put("yAxis", newPlayer.getYAxis());
        assertEquals(new Gson().toJson(res), result);
    }
}
