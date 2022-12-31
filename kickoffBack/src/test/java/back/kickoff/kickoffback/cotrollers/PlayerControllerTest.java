package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.Commands.PlayerAddImageCommand;
import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.model.Reservation;
import back.kickoff.kickoffback.repositories.PlayerRepository;
import back.kickoff.kickoffback.services.PlayerAgent;
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
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PlayerControllerTest {
    @Mock
    PlayerAgent playerAgent;
    @Mock
    PlayerRepository playerRepository;
    PlayerController playerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        playerController = new PlayerController(playerAgent, playerRepository);
    }

    @Test
    void addImage() throws JSONException {
        Map<String, Object> hm = new HashMap<>();
        hm.put("playerId", 14L);
        hm.put("imageURL", "zizo.png");
        Player player = new Player();
        Mockito.when(playerRepository.findById(14L)).thenReturn(Optional.of(player));
        String information = new Gson().toJson(hm);
        PlayerAddImageCommand command = new PlayerAddImageCommand(information, playerRepository);
        ResponseEntity<String> response = playerController.addImage(information);
        assertEquals(new ResponseEntity<>("Success", HttpStatus.OK), response);
    }
}