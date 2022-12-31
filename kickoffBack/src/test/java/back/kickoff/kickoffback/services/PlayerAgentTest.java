package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.Operation.PlayerAddImageCommand;
import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.repositories.PlayerRepository;
import com.google.gson.Gson;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class PlayerAgentTest {
    @Mock
    PlayerRepository playerRepository;
    PlayerAgent playerAgent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        playerAgent = new PlayerAgent(playerRepository);
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
    }
}