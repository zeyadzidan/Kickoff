package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.services.PartyServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class PartyControllerTest {
    @Mock
    PartyServices partyServices;
    PartyController partyController;

    @BeforeEach
    void setUp() {
        partyController = new PartyController(partyServices);
    }

    @Test
    void createParty() {
    }

    @Test
    void joinParty() {
    }

    @Test
    void leaveParty() {
    }

    @Test
    void deleteParty() {
    }
}