package back.kickoff.kickoffback.Commands;

import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.Player;
import lombok.EqualsAndHashCode;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@EqualsAndHashCode
public class PlayerFrontEnd {
    public Long id;
    public String name;
    public String email;
    public String image;
    public String phoneNumber;
    public String location;
    public Double xAxis ;
    public Double yAxis;

    public boolean restricted;
    public int warnings;
    public String lastWarning  ;
    public int penaltyDaysLeft ;

    public PlayerFrontEnd(Player player){
        id = player.getId();
        name = player.getName();
        email = player.getEmail();
        image = player.getImage();
        phoneNumber = player.getPhoneNumber();
        xAxis = player.getXAxis();
        yAxis = player.getYAxis();

        restricted = player.isRestricted();
        warnings = player.getWarnings();
        penaltyDaysLeft = 0 ;

        if(player.getLastWarning()!= null){
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

            lastWarning = dateFormat.format(player.getLastWarning());
            LocalDate lastWarning = player.getLastWarning().toLocalDate() ;
            LocalDate now = LocalDate.now();
            int days  = (int) now.until(lastWarning, ChronoUnit.DAYS);
            if(player.getPenaltyDays()> days){
                penaltyDaysLeft = player.getPenaltyDays()-days ;
            }
        }
    }



}
