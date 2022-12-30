package back.kickoff.kickoffback.Commands;

import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.Player;

public class PlayerFrontEnd {
    public Long id;
    public String name;
    public String email;
    public String image;
    public String phoneNumber;
    public String location;
    public Double xAxis ;
    public Double yAxis;

    public PlayerFrontEnd(Player player){
        id = player.getId();
        name = player.getName();
        email = player.getEmail();
        image = player.getImage();
        phoneNumber = player.getPhoneNumber();
        xAxis = player.getXAxis();
        yAxis = player.getYAxis();
    }



}
