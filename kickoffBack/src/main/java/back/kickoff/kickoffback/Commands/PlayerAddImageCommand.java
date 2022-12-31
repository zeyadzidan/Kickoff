package back.kickoff.kickoffback.Commands;

import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.repositories.PlayerRepository;
import lombok.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Optional;

import java.util.jar.JarException;


@Setter
@Getter
@ToString
public class PlayerAddImageCommand {
    private Player player;
    private String imageURL ;

    public PlayerAddImageCommand(String informationJSON, PlayerRepository playerRepository) throws JSONException {
        JSONObject jsonObject = new JSONObject(informationJSON);
        long playerId;
        try {
            playerId = jsonObject.getLong("playerId");
        } catch (Exception e) {
            throw new JSONException("playerId is required") ;
        }
        Optional<Player> playerOptional = playerRepository.findById(playerId);
        if (playerOptional.isEmpty()) {
            throw new JSONException("player does not exist");
        }
        player = playerOptional.get();

        try {
            imageURL = jsonObject.getString("imageURL");
            if (imageURL == null) {
                throw new JSONException("imageURL is required");
            }
        } catch (Exception e) {
            throw new JSONException("imageURL is required");
        }

    }



}

