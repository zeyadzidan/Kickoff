package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.Operation.PlayerAddImageCommand;
import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.repositories.PlayerRepository;
import org.springframework.stereotype.Service;

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
