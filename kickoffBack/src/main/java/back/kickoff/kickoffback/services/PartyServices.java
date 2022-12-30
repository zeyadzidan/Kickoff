package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.CreateParty;
import back.kickoff.kickoffback.Commands.PartyFrontEnd;
import back.kickoff.kickoffback.model.*;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.repositories.PartyRepository;
import back.kickoff.kickoffback.repositories.PlayerRepository;
import back.kickoff.kickoffback.repositories.CourtRepository;
import back.kickoff.kickoffback.repositories.ReservationRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class PartyServices {
    private final PartyRepository partyRepository;
    private final CourtOwnerRepository courtOwnerRepository;
    private final PlayerRepository playerRepository;
    private final CourtRepository courtRepository;
    private final ReservationRepository reservationRepository;
    public PartyServices(PartyRepository partyRepository,CourtOwnerRepository courtOwnerRepository,PlayerRepository playerRepository, CourtRepository courtRepository,ReservationRepository reservationRepository) {
        this.partyRepository = partyRepository;
        this.courtOwnerRepository = courtOwnerRepository;
        this.playerRepository =playerRepository;
        this.courtRepository=courtRepository;
        this.reservationRepository =reservationRepository;
    }
    public boolean CreateParty(CreateParty command) throws JSONException {
        try {
            Optional<Reservation> reservationOptional = reservationRepository.findById(command.reservationId);
            Reservation reservation = reservationOptional.get();
            Optional<CourtOwner> courtOwnerOptional = courtOwnerRepository.findById(reservation.getCourtOwnerID());
            CourtOwner courtOwner = courtOwnerOptional.get();
            Optional<Court> courtOptional = courtRepository.findById(reservation.getCourtID());
            Court court = courtOptional.get();
            Optional<Player> playerOptional = playerRepository.findById(reservation.getMainPlayer().getId());
            Player player = playerOptional.get();
            Party newParty = new Party(command.neededNumbers, command.availableNumbers,reservation,courtOwner,court,player);
            partyRepository.save(newParty);
            playerRepository.save(player);
            Map<String, Object> res = new HashMap<>();
            res.put("id", newParty.getPartyId());
            res.put("CourtOwner", newParty.getCourtOwner());
            res.put("Court", newParty.getCourt());
            res.put("PlayerCreated", newParty.getPlayerCreated());
            res.put("fullplaces", newParty.getAvailableNumbers());
            res.put("emptyplaces", newParty.getNeededNumbers());
            res.put("Reservation", newParty.getReservation());
            System.out.println(res);
            return true;
        } catch (Exception ignored) {
            return false;
        }

    }
    public boolean deleteParty(String information) throws JSONException {
        JSONObject jsonObject = new JSONObject(information);
        Long partyid = jsonObject.getLong("id");
        Optional<Party> PartyOptional = partyRepository.findById(partyid);
        if (PartyOptional.isEmpty()) {
            return false;
        }
        partyRepository.deleteById(partyid);
        return true;
    }

    public boolean joinParty(String information) throws JSONException{
        JSONObject jsonObject = new JSONObject(information);
        Long partyid = jsonObject.getLong("id");
        Long playerid = jsonObject.getLong("pid");

        Optional<Party> PartyOptional = partyRepository.findById(partyid);
        if (PartyOptional.isEmpty()) {
            return false;
        }
        Optional<Player> PlayerOptional = playerRepository.findById(playerid);
        if (PlayerOptional.isEmpty()) {
            return false;
        }
        Player player = PlayerOptional.get();
        Party party = PartyOptional.get();
        if(party.getNeededNumbers().equals("0"))
        {
            System.out.println("No places");
            return false;
        }
        party.addJoinedPlayers(player);
        party.dectementneededPlayer(party.getNeededNumbers());
        partyRepository.save(party);
        playerRepository.save(player);
        return true;
    }
    public boolean leaveParty(String information) throws JSONException{
        JSONObject jsonObject = new JSONObject(information);
        Long partyid = jsonObject.getLong("id");
        Long playerid = jsonObject.getLong("pid");



        Optional<Party> PartyOptional = partyRepository.findById(partyid);
        if (PartyOptional.isEmpty()) {
            return false;
        }
        Optional<Player> PlayerOptional = playerRepository.findById(playerid);
        if (PlayerOptional.isEmpty()) {
            return false;
        }
        Player player = PlayerOptional.get();
        Party party = PartyOptional.get();
        party.leaveJoinedPlayers(player);
        party.incrementneededPlayer(party.getNeededNumbers());
        partyRepository.save(party);
        playerRepository.save(player);
        return true;
    }
    public List<PartyFrontEnd> getCourtOwnerParties(String information) throws JSONException {

        JSONObject jsonObject = new JSONObject(information);
        Long CourtOwnerid = jsonObject.getLong("id");


        Optional<CourtOwner> courtOwnerOptional = courtOwnerRepository.findById(CourtOwnerid);
        if (courtOwnerOptional.isEmpty())
            throw new RuntimeException("CourtOwner Not Found");
        CourtOwner source = courtOwnerOptional.get();
        List<Party> parties = source.getParties();
        List<PartyFrontEnd> data = new ArrayList<>();
        for (Party p : parties) {
            data.add(new PartyFrontEnd(p));
        }
        System.out.println(data);
        return data;
    }

    public  List<PartyFrontEnd> getPlayerCreatedParties(String information) throws JSONException{
        JSONObject jsonObject = new JSONObject(information);
        Long Playerid = jsonObject.getLong("id");


        Optional<Player> PlayerOptional = playerRepository.findById(Playerid);
        if (PlayerOptional.isEmpty())
            throw new RuntimeException("Player Not Found");
        Player source = PlayerOptional.get();
        List<Party> parties = source.getPartiesCreated();
        List<PartyFrontEnd> data = new ArrayList<>();
        for (Party p : parties) {
            data.add(new PartyFrontEnd(p));
        }
        System.out.println(data);
        return data;
    }

    public  List<PartyFrontEnd> getPlayerJoinedParties(String information) throws JSONException{
        JSONObject jsonObject = new JSONObject(information);
        Long Playerid = jsonObject.getLong("id");
        Optional<Player> PlayerOptional = playerRepository.findById(Playerid);
        if (PlayerOptional.isEmpty())
            throw new RuntimeException("Player Not Found");
        Player source = PlayerOptional.get();
        List<Party> parties = source.getPartiesJoined();
        List<PartyFrontEnd> data = new ArrayList<>();
        for (Party p : parties) {
            data.add(new PartyFrontEnd(p));
        }
        System.out.println(data);
        return data;
    }
}