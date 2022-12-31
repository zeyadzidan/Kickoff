package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.FrontEnd.CourtOwnerFrontEnd;
import back.kickoff.kickoffback.Commands.FrontEnd.PlayerFrontEnd;
import back.kickoff.kickoffback.Commands.Add.SignupCommand;
import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.model.PlayerType;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.repositories.PlayerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SignupServiceTest {

    SignupService signupService;

    @Mock
    CourtOwnerRepository courtOwnerRepository;
    @Mock
    PlayerRepository playerRepository;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        signupService = new SignupService(courtOwnerRepository, playerRepository);
    }

    @Test
    void courtOwnerSignup() throws Exception {
        SignupCommand signupCommand = new SignupCommand("nasrClub@gmail.com", "12345678900",
                "Nasr CLub", "Nasr CLub green plaza", "01206555589", 44.5, 44.5);
        when(courtOwnerRepository.save(new CourtOwner())).thenReturn(new CourtOwner());
        CourtOwnerFrontEnd courtOwnerFrontEnd = signupService.courtOwnerSignup(signupCommand);
        CourtOwner newCourtOwner = new CourtOwner("Nasr CLub", "nasrClub@gmail.com", "12345678900",
                "01206555589", 44.5, 44.5);
        newCourtOwner.setRating(0);
        newCourtOwner.setLocation("Nasr CLub green plaza");
        CourtOwnerFrontEnd res = new CourtOwnerFrontEnd(newCourtOwner);
        assertEquals(res, courtOwnerFrontEnd);
    }

    @Test
    void playerSignup() throws Exception {
        SignupCommand signupCommand = new SignupCommand("cr7@gmail.com", "12345678900", "Cristiano Ronaldo", "Lisbon Portugal",
                "01176553539", 34.5, 24.5);
        when(playerRepository.save(new Player())).thenReturn(new Player());
        PlayerFrontEnd playerFrontEnd = signupService.playerSignup(signupCommand);
        Player newPlayer = new Player("Cristiano Ronaldo", "cr7@gmail.com", "01176553539",
                "12345678900", "Lisbon Portugal",34.5, 24.5);
        newPlayer.setPlayerType(PlayerType.Registered);
        PlayerFrontEnd res = new PlayerFrontEnd(newPlayer);
        assertEquals(res, playerFrontEnd);
    }

}
