package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.AddPenaltyCommand;
import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.model.PlayerType;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.repositories.PlayerRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PenaltyServiceTest {
    PenaltyService penaltyService ;

    @Mock
    PlayerRepository playerRepository ;
    @Mock
    CourtOwnerRepository courtOwnerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        penaltyService = new PenaltyService(playerRepository,courtOwnerRepository) ;

    }

    AddPenaltyCommand createAddPenaltyCommand() throws JSONException {
        JSONObject jsonObject = new JSONObject()
                .put("coid",1L)
                .put("pid", 1L);
        return new AddPenaltyCommand(jsonObject.toString(),true );
    }
    Player createGoodPlayer(){
        Player player = new Player();
        player.setPlayerType(PlayerType.Registered);
        player.setId(1L);
        return player;
    }

    Player createPlayerWillHavePenalty(){
        Player player = createGoodPlayer() ;
        player.setWarnings(5);
        player.setLastWarning(Date.valueOf(LocalDate.of(2022,12,25)));
        return player;
    }

    Player createPlayerhavePenalty(){
        Player player = createGoodPlayer() ;
        player.setRestricted(true);
        player.setWarnings(6);
        player.setLastWarning(Date.valueOf(LocalDate.of(2022,12,25)));
        player.setPenaltyDays(7);
        return player;
    }

    @Test
    void addReportTest() throws Exception {
        AddPenaltyCommand command = createAddPenaltyCommand();
        Player player = createGoodPlayer();

        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(playerRepository.save(player)).thenReturn(player);
        when(courtOwnerRepository.findById(1L)).thenReturn(Optional.of(new CourtOwner()));

        String message = "" ;
        boolean res = true;
        try{
            res = penaltyService.addReport(command);
        }catch (Exception e){
            message = e.getMessage() ;
        }
        assertFalse(res);
        assertEquals(message, "");
        assertEquals(player.getWarnings(), 1);
    }

    @Test
    void checkAndAddRestrictionTest() {
        Player player = createPlayerhavePenalty() ;
        boolean res = penaltyService.checkAndAddRestriction(player) ;

        assertTrue(res);
        assertTrue(player.isRestricted());
        assertEquals(player.getPenaltyDays(),7-5 + Math.ceil(7*6/5.0));
    }
    @Test
    void checkAndAddRestrictionTest2() {
        Player player = createPlayerWillHavePenalty() ;
        boolean res = penaltyService.checkAndAddRestriction(player) ;

        assertTrue(res);
        assertTrue(player.isRestricted());
        assertEquals(player.getPenaltyDays(),7);
    }


    Player createFinishPenaltyPlayer(){
        Player player = createGoodPlayer() ;
        player.setRestricted(true);
        player.setWarnings(5);
        player.setLastWarning(Date.valueOf(LocalDate.of(2022,12,22)));
        player.setPenaltyDays(7);
        return player;
    }
    Player createDownWarningsPlayer(){
        Player player = createGoodPlayer() ;
        player.setWarnings(5);
        player.setLastWarning(Date.valueOf(LocalDate.of(2022,12,15)));
        return player;
    }

    @Test
    void updatePenaltyTest() {
        Player player = createFinishPenaltyPlayer() ;
        penaltyService.updatePenalty(player) ;

        when(playerRepository.save(player)).thenReturn(player);

        assertFalse(player.isRestricted());
        assertEquals(player.getPenaltyDays(),0);
        assertEquals(player.getLastWarning(),Date.valueOf(LocalDate.of(2022,12,29)));
    }
    @Test
    void updatePenaltyTest2() {
        Player player = createPlayerhavePenalty() ;
        penaltyService.updatePenalty(player) ;

        when(playerRepository.save(player)).thenReturn(player);

        assertTrue(player.isRestricted());
        assertEquals(player.getPenaltyDays(),7);
    }
    @Test
    void updatePenaltyTest3() {
        Player player = createDownWarningsPlayer() ;
        penaltyService.updatePenalty(player) ;

        when(playerRepository.save(player)).thenReturn(player);

        assertFalse(player.isRestricted());
        assertEquals(player.getPenaltyDays(),0);
        assertEquals(player.getWarnings(),4);
    }
}