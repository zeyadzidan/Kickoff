package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.PlayerAddImageCommand;
import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.repositories.PlayerRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.Optional;

@Service
public class PlayerAgent {

    private final PlayerRepository playerRepository ;

    public PlayerAgent(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public void addImage(PlayerAddImageCommand command) {
        Player player = command.getPlayer();
        player.setImage(command.getImageURL());
        playerRepository.save(player);
    }
}
