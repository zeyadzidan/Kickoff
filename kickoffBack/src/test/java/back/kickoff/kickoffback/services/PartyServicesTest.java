package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.Add.CreateParty;
import back.kickoff.kickoffback.model.*;
import back.kickoff.kickoffback.repositories.*;
import com.google.gson.Gson;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    void createParty() throws JSONException {
        Reservation reservation = new Reservation();
        CourtOwner courtOwner = new CourtOwner();
        courtOwner.setId(20L);
        reservation.setCourtOwnerID(20L);
        Court court = new Court();
        court.setId(25L);
        reservation.setCourtID(25L);
        Player player = new Player();
        player.setId(7L);
        reservation.setMainPlayer(player);
        Mockito.when(reservationRepository.findById(120L)).thenReturn(Optional.of(reservation));
        Mockito.when(partyRepository.existsByReservation(reservation)).thenReturn(false);
        Mockito.when(courtOwnerRepository.findById(20L)).thenReturn(Optional.of(courtOwner));
        Mockito.when(courtRepository.findById(25L)).thenReturn(Optional.of(court));
        Mockito.when(playerRepository.findById(7L)).thenReturn(Optional.of(player));
        CreateParty command = new CreateParty(120L, "20", "5");
        assertEquals(true, partyServices.CreateParty(command));
    }

    @Test
    void deleteParty() throws JSONException {
        Map<String, Object> hm = new HashMap<>();
        hm.put("id", 14L);
        Party party = new Party();
        Mockito.when(partyRepository.findById(14L)).thenReturn(Optional.of(party));
        assertEquals(true, partyServices.deleteParty(new Gson().toJson(hm)));
    }

    @Test
    void joinParty() throws JSONException {
        Map<String, Object> hm = new HashMap<>();
        hm.put("id", 77L);
        hm.put("pid", 14L);
        Party party = new Party();
        party.setNeededNumbers("2");
        party.setPlayerJoined(new ArrayList<>());
        Player player = new Player();
        Mockito.when(partyRepository.findById(77L)).thenReturn(Optional.of(party));
        Mockito.when(playerRepository.findById(14L)).thenReturn(Optional.of(player));
        assertEquals(true, partyServices.joinParty(new Gson().toJson(hm)));
    }

    @Test
    void leaveParty() throws JSONException {
        Map<String, Object> hm = new HashMap<>();
        hm.put("id", 77L);
        hm.put("pid", 14L);
        Party party = new Party();
        party.setNeededNumbers("2");
        party.setPlayerJoined(new ArrayList<>());
        Player player = new Player();
        player.setId(14L);
        party.addJoinedPlayers(player);
        Mockito.when(partyRepository.findById(77L)).thenReturn(Optional.of(party));
        Mockito.when(playerRepository.findById(14L)).thenReturn(Optional.of(player));
        assertEquals(true, partyServices.leaveParty(new Gson().toJson(hm)));
    }

    @Test
    void getCourtOwnerParties() throws JSONException {
        Map<String, Object> hm = new HashMap<>();
        hm.put("id", 77L);
        hm.put("pid", 14L);
        CourtOwner courtOwner = new CourtOwner();
        courtOwner.setParties(new ArrayList<>());
        Player player = new Player();
        Mockito.when(courtOwnerRepository.findById(77L)).thenReturn(Optional.of(courtOwner));
        Mockito.when(playerRepository.findById(14L)).thenReturn(Optional.of(player));
        assertEquals(new ArrayList<>(), partyServices.getCourtOwnerParties(new Gson().toJson(hm)));

    }

    @Test
    void getPlayerCreatedParties() throws JSONException {
        Map<String, Object> hm = new HashMap<>();
        hm.put("id", 14L);
        Player player = new Player();
        player.setPartiesCreated(new ArrayList<>());
        Mockito.when(playerRepository.findById(14L)).thenReturn(Optional.of(player));
        assertEquals(new ArrayList<>(), partyServices.getPlayerCreatedParties(new Gson().toJson(hm)));
    }

    @Test
    void getPartiesofplayerJoined() throws JSONException {
        Map<String, Object> hm = new HashMap<>();
        hm.put("id", 14L);
        Player player = new Player();
        player.setPartiesJoined(new ArrayList<>());
        Mockito.when(playerRepository.findById(14L)).thenReturn(Optional.of(player));
        assertEquals(new ArrayList<>(), partyServices.getPartiesofplayerJoined(new Gson().toJson(hm)));
    }

    @Test
    void getplayersofParties() throws JSONException {
        Map<String, Object> hm = new HashMap<>();
        hm.put("id", 44L);
        Party party = new Party();
        party.setPlayerJoined(new ArrayList<>());
        Mockito.when(partyRepository.findById(44L)).thenReturn(Optional.of(party));
        assertEquals(new ArrayList<>(), partyServices.getplayersofParties(new Gson().toJson(hm)));
    }

    @Test
    void getPartiesSubscribed() throws JSONException {
        Map<String, Object> hm = new HashMap<>();
        hm.put("id", 14L);
        Player player = new Player();
        player.setId(14L);
        Mockito.when(playerRepository.findById(14L)).thenReturn(Optional.of(player));
        Mockito.when(subscriptionsRepository.findByPid(14L)).thenReturn(new ArrayList<>());
        assertEquals(new ArrayList<>(), partyServices.getPartiesSubscribed(new Gson().toJson(hm)));
    }

    @Test
    void getpartiesappearCourtOwner() throws JSONException {
        Map<String, Object> hm = new HashMap<>();
        hm.put("id", 14L);
        CourtOwner courtOwner = new CourtOwner();
        courtOwner.setParties(new ArrayList<>());
        Mockito.when(courtOwnerRepository.findById(14L)).thenReturn(Optional.of(courtOwner));
        assertEquals(new ArrayList<>(), partyServices.getpartiesappearCourtOwner(new Gson().toJson(hm)));
    }
}