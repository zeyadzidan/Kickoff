package back.kickoff.kickoffback.Commands;

import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.model.PlayerType;
import lombok.Getter;

@Getter
public class PlayerFrontEnd {


    private String name;

    private String image;

    public PlayerFrontEnd(Player player) {
      name = player.getName();
      image = player.getImage();

    }
}
