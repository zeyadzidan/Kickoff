package back.kickoff.kickoffback.Commands;

import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.model.PlayerType;
import back.kickoff.kickoffback.model.Reservation;
import back.kickoff.kickoffback.repositories.PlayerRepository;
import lombok.Getter;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

@Getter
public class SetPendingCommand {
    Long courtId;
    Long courtOwnerId ;
    String dateStrS ;
    String dateStrF ;
    int startHour ;
    int finishHour ;
    //>>>>
    Long playerID ;
    // or
    String playerName;
    String phoneNumber;
    // <<<<

    // not in the JSON
    Date stDate, endDate;
    Time timeFrom, timeTo;
    Player player ;


    public SetPendingCommand(String JSONinfo, PlayerRepository playerRepository) throws Exception {
        JSONObject jsonObject = new JSONObject(JSONinfo);
        courtId = jsonObject.getLong("courtId");
        courtOwnerId = jsonObject.getLong("courtOwnerId");
        dateStrS = jsonObject.getString("startDate");
        dateStrF = jsonObject.getString("endDate");
        String[] tempArrS = dateStrS.split("/");
        String[] tempArrF = dateStrF.split("/");
        startHour = jsonObject.getInt("startHour");
        finishHour = jsonObject.getInt("finishHour");


        if (tempArrS.length != 3 || tempArrF.length != 3)
            throw new Exception("In valid date1");
        try
        {
            SimpleDateFormat obj = new SimpleDateFormat("MM/dd/yyyy");
            long date1 = obj.parse(dateStrS).getTime();
            long date2 = obj.parse(dateStrF).getTime();
            stDate = new Date(date1);
            endDate = new Date(date2);
        } catch (Exception e) {
            throw new Exception("In valid date2");
        }
        try {
            LocalTime ltFrom = LocalTime.of(startHour,0,0)  ;
            LocalTime ltTo = LocalTime.of(finishHour,0,0)  ;
            timeFrom = Time.valueOf(ltFrom) ;
            timeTo = Time.valueOf(ltTo) ;
        } catch (Exception e) {
            throw new Exception("In valid Time");
        }

        try {
            playerID = jsonObject.getLong("playerId");
            Optional<Player> optionalPlayer= playerRepository.findById(playerID) ;
            if(optionalPlayer.isPresent()){
                player = optionalPlayer.get();
            }else{
                throw new JSONException("Player ID") ;
            }

        }catch (Exception e){
            try {
                playerName = jsonObject.getString("playerName");
                phoneNumber = jsonObject.getString("phoneNumber");
                player = new Player() ;
                player.setPlayerType(PlayerType.Lite);
                player.setName(playerName);
                player.setPhoneNumber(phoneNumber);
                player.setReservations(new ArrayList<>());
                playerRepository.save(player);
            }catch (Exception e2){
                throw new Exception("one of 'playerId' or 'playerName' must be exist") ;
            }
        }

    }
}
