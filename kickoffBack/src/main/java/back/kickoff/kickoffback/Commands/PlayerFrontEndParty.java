package back.kickoff.kickoffback.Commands;

import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.model.PlayerType;
import lombok.Getter;

@Getter
public class PlayerFrontEndParty {
    private final String Pname;
    private final String Pimg;
    public PlayerFrontEndParty(Player player) {
        Pname = player.getName();

      if( player.getImage()==null)
      {
          Pimg ="";
      }
      else
      {
          Pimg = player.getImage();
      }

    }
}
