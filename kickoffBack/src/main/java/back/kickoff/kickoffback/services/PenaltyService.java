package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.Add.AddPenaltyCommand;
import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.repositories.PlayerRepository;
import org.springframework.stereotype.Service;

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
        updatePenalty(player);
        player.setWarnings(player.getWarnings()+1);
        boolean res = checkAndAddRestriction(player) ;
        playerRepository.save(player) ;
        return res;
    }

    boolean checkAndAddRestriction(Player player){
        long old = 0 ;
        if(player.isRestricted()){
            LocalDate lastWarning = player.getLastWarning().toLocalDate() ;
            LocalDate now = LocalDate.now();
            long days  = lastWarning.until(now, ChronoUnit.DAYS);
            if(player.getPenaltyDays()> days){
                old = player.getPenaltyDays()-days ;
            }
            System.out.println("old + "+ old + " + days +" + days);
        }
        if(player.getWarnings()>=5){
            old += Math.ceil(7*player.getWarnings()/5.0) ;
        }
        player.setPenaltyDays((int) old);
        player.setLastWarning(Date.valueOf(LocalDate.now()));
        player.setRestricted(old>0);
        return (old>0) ;
    }

    void updatePenalty(Player player){
        if(player.getWarnings()==0){
            return;
        }
        LocalDate lastWarning = player.getLastWarning().toLocalDate() ;
        LocalDate now = LocalDate.now();
        long days  = lastWarning.until(now, ChronoUnit.DAYS);
        System.out.println("days " + days);
        System.out.println("lastWarning " + lastWarning);
        if(player.isRestricted()){
            if(player.getPenaltyDays()< days){ // passed the penalty time
                lastWarning = lastWarning.plusDays(player.getPenaltyDays());
                System.out.println("lastWarning " + lastWarning);

                days -= player.getPenaltyDays() ;
                player.setPenaltyDays(0);
                player.setRestricted(false);
            }else{
                return;
            }
        }

        int down = (int) Math.floor(days/14.0);
        if(down <= player.getWarnings()){
            int noWarnings = player.getWarnings()-down ;
            lastWarning = lastWarning.plusDays(down* 14L);
            player.setWarnings(noWarnings);
        }else {
            down = player.getWarnings() ;
            int noWarnings = 0 ;
            lastWarning = lastWarning.plusDays(down* 14L);
            player.setWarnings(noWarnings);
        }


        player.setLastWarning(Date.valueOf(lastWarning));
        System.out.println("lastWarning " + lastWarning);

        playerRepository.save(player) ;

    }

}
