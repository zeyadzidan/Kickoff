package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.CourtOwnerFrontEnd;
import back.kickoff.kickoffback.Commands.LoginCommand;
import back.kickoff.kickoffback.Commands.PlayerFrontEnd;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class LoginServiceTest {
    LoginService loginService;

    @Mock
    CourtOwnerRepository courtOwnerRepository;
    @Mock
    PlayerRepository playerRepository;

    @Mock
    PenaltyService penaltyService;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        loginService = new LoginService(courtOwnerRepository, playerRepository, penaltyService);
    }

    @Test
    void courtOwnerLogin() throws Exception {
        CourtOwner newCourtOwner = new CourtOwner("Nasr CLub", "nasrClub@gmail.com", "12345678900",
                "01206555589", 44.5, 44.5);
        newCourtOwner.setRating(0);
        newCourtOwner.setLocation("Nasr CLub");
        newCourtOwner.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png");
        List list = new ArrayList();
        newCourtOwner.setCourts(list);
        LoginCommand loginCommand = new LoginCommand("nasrClub@gmail.com", "12345678900");
        when(courtOwnerRepository.save(new CourtOwner())).thenReturn(new CourtOwner());
        when(courtOwnerRepository.findByEmail("nasrClub@gmail.com")).thenReturn(java.util.Optional.of(newCourtOwner));
        CourtOwnerFrontEnd result = loginService.courtOwnerLogin(loginCommand);
        CourtOwnerFrontEnd courtOwnerFrontEnd = new CourtOwnerFrontEnd(newCourtOwner);
        assertEquals(courtOwnerFrontEnd, result);
    }

    @Test
    void playerLogin() throws Exception {
        Player newPlayer = new Player("Cristiano Ronaldo", "cr7@gmail.com", "01176553539",
                "12345678900", "Lisbon Portugal",34.5, 24.5);
        newPlayer.setPlayerType(PlayerType.Registered);
        LoginCommand loginCommand = new LoginCommand("cr7@gmail.com", "12345678900");
        when(playerRepository.save(new Player())).thenReturn(new Player());
        when(playerRepository.findByEmail("cr7@gmail.com")).thenReturn(java.util.Optional.of(newPlayer));
        PlayerFrontEnd result = loginService.playerLogin(loginCommand);
        PlayerFrontEnd playerFrontEnd = new PlayerFrontEnd(newPlayer);

        assertEquals(playerFrontEnd, result);
    }


}