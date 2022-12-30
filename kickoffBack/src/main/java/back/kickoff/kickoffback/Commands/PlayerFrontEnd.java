package back.kickoff.kickoffback.Commands;

import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.model.PlayerType;
import lombok.Getter;

@Getter
public class PlayerFrontEnd {


    private String Pname;

    private String Pimg;

    public PlayerFrontEnd(Player player) {
        Pname = player.getName();
        
          Pimg = player.getImage();

    }
}
