package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class PartyServicesTest {
    @Mock
    PartyRepository partyRepository;
    @Mock
    CourtOwnerRepository courtOwnerRepository;
    @Mock
    PlayerRepository playerRepository;
    @Mock
    CourtRepository courtRepository;
    @Mock
    ReservationRepository reservationRepository;
    @Mock
    SubscriptionsRepository subscriptionsRepository;
    PartyServices partyServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        partyServices = new PartyServices(partyRepository, courtOwnerRepository, playerRepository,
                courtRepository, reservationRepository, subscriptionsRepository);
    }

    @Test
    void createParty() {

    }

    @Test
    void deleteParty() {
    }

    @Test
    void joinParty() {
    }

    @Test
    void leaveParty() {
    }

    @Test
    void getCourtOwnerParties() {
    }

    @Test
    void getPlayerCreatedParties() {
    }

    @Test
    void getPartiesofplayerJoined() {
    }

    @Test
    void getplayersofParties() {
    }

    @Test
    void getPartiesSubscribed() {
    }
}