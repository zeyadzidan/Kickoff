package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.AddPenaltyCommand;
import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.repositories.PlayerRepository;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.time.temporal.ChronoUnit;


@Service
public class PenaltyService {
    private final PlayerRepository playerRepository ;
    private final CourtOwnerRepository courtOwnerRepository;


    public PenaltyService(PlayerRepository playerRepository, CourtOwnerRepository courtOwnerRepository) {
        this.playerRepository = playerRepository;
        this.courtOwnerRepository = courtOwnerRepository;
    }

    public boolean addReport(AddPenaltyCommand command) throws Exception {
        if(command.isCourtOwnerReport){
            Optional<CourtOwner> courtOwner = courtOwnerRepository.findById(command.fromId);
            if(courtOwner.isEmpty()){
                throw new Exception("CourtOwner does not exist") ;
            }
        }else {
            Optional<Player> playerOptional = playerRepository.findById(command.fromId);
            if (playerOptional.isEmpty()) {
                throw new Exception("Player reporting does not exist");
            }
        }
        Optional<Player> playerOptionalReported = playerRepository.findById(command.onId);
        if (playerOptionalReported.isEmpty()) {
            throw new Exception("Player reported does not exist");
        }
        Player player = playerOptionalReported.get() ;
        player.setWarnings(player.getWarnings()+1);
        return checkAndAddRestriction(player) ;
    }

    boolean checkAndAddRestriction(Player player){
        long old = 0 ;
        if(player.isRestricted()){
            LocalDate lastWarning = player.getLastWarning().toLocalDate() ;
            LocalDate now = LocalDate.now();
            long days  = now.until(lastWarning, ChronoUnit.DAYS);
            if(player.getPenaltyDays()> days){
                old = player.getPenaltyDays()-days ;
            }
        }
        if(player.getWarnings()>=5){
            old += Math.ceilDiv(7*player.getWarnings(),5) ;
        }
        player.setPenaltyDays((int) old);
        player.setLastWarning(Date.valueOf(LocalDate.now()));
        return (old>0) ;
    }

}
