package back.kickoff.kickoffback.Commands.Operation;

import back.kickoff.kickoffback.model.Player;
import lombok.Getter;

@Getter
public class PlayerFrontEndParty {
    private final Long pid;
    private final String Pname;
    private final String Pimg;

    public PlayerFrontEndParty(Player player) {
        pid = player.getId();
        Pname = player.getName();

        if (player.getImage() == null) {
            Pimg = "";
        } else {
            Pimg = player.getImage();
        }

    }
}
