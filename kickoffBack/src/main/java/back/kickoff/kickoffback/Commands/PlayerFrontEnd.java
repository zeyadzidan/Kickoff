package back.kickoff.kickoffback.Commands;

import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.model.PlayerType;
import lombok.Getter;

@Getter
public class PlayerFrontEnd {

    private Long playerId;
    private String name;
    private String email;
    private String phoneNumber;
    private String playerType;
    private String location;
    private String image;

    public PlayerFrontEnd(Player player) {
      playerId =player.getId();
      name = player.getName();
      email = player.getEmail();
      phoneNumber = player.getPhoneNumber();
      playerType = player.getPlayerType().toString();
      location = player.getLocation();
      image = player.getImage();

    }
}
