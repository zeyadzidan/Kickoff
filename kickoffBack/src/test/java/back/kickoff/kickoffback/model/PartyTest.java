package back.kickoff.kickoffback.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PartyTest {
    Party party;
    @BeforeEach
    void setUp() {
        party = new Party();
    }

    @Test
    void getPartyId() {
        Long id = 23L;
        party.setPartyId(id);
        assertEquals(id, party.getPartyId());
    }

    @Test
    void getPlayerJoined() {
        List<Player> players = new ArrayList<>();
        party.setPlayerJoined(players);
        assertEquals(players, party.getPlayerJoined());
    }

    @Test
    void getNeededNumbers() {
        String n = "22";
        party.setNeededNumbers(n);
        assertEquals(n, party.getNeededNumbers());
    }

    @Test
    void getAvailableNumbers() {
        String n = "2";
        party.setAvailableNumbers(n);
        assertEquals(n, party.getAvailableNumbers());
    }

    @Test
    void getReservation() {
        Reservation reservation = new Reservation();
        party.setReservation(reservation);
        assertEquals(reservation, party.getReservation());
    }

    @Test
    void getCourtOwner() {
        CourtOwner courtOwner = new CourtOwner();
        party.setCourtOwner(courtOwner);
        assertEquals(courtOwner, party.getCourtOwner());
    }

    @Test
    void getCourt() {
        Court court = new Court();
        party.setCourt(court);
        assertEquals(court, party.getCourt());
    }

    @Test
    void getPlayerCreated() {
        Player player = new Player();
        party.setPlayerCreated(player);
        assertEquals(player, party.getPlayerCreated());
    }
}