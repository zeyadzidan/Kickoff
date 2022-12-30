package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.Party;
import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.repositories.PartyRepository;
import back.kickoff.kickoffback.repositories.PlayerRepository;
import back.kickoff.kickoffback.repositories.CourtRepository;
import back.kickoff.kickoffback.repositories.ReservationRepository;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

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
    public boolean CreateParty(String jsonParty) throws JSONException {

        try {
            JSONObject jsonObject = new JSONObject(jsonParty);
            Long playerId = jsonObject.getLong("playerId");
            Long courtOnwerId = jsonObject.getLong("courtOnwerId");
            Long courtId = jsonObject.getLong("courtId");
            Long reservationId = jsonObject.getLong("reservationId");
            String neededNumbers = jsonObject.getString("neededNumbers");
            String availableNumbers = jsonObject.getString("availableNumbers");
            Party newParty = new Party(courtOnwerId, courtId, neededNumbers, availableNumbers, playerId, reservationId);
            Map<String, Object> res = new HashMap<>();
            res.put("id", newParty.getId());
            res.put("CourtOwnerId", newParty.getCourtOwnerId());
            res.put("CourtId", newParty.getCourtId());
            res.put("PlayerCreatedId", newParty.getPlayerCreatedId());
            res.put("AvailableNumbers", newParty.getAvailableNumbers());
            res.put("NeededNumbers", newParty.getNeededNumbers());
            res.put("ReservationId", newParty.getReservationId());
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
//        else if(PartyOptional.)

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
        return true;
    }
    public String getCourtOwnerParties(String information) throws JSONException {

        JSONObject jsonObject = new JSONObject(information);
        Long CourtOwnerid = jsonObject.getLong("id");
        Optional<CourtOwner> courtOwnerOptional = courtOwnerRepository.findById(CourtOwnerid);
        if (courtOwnerOptional.isEmpty())
            throw new RuntimeException("CourtOwner Not Found");
        CourtOwner source = courtOwnerOptional.get();
        List<Party> parties = source.getParties();
        List<JSONObject> data = new ArrayList<>();
        for (Party p : parties) {
            data.add(
                    new JSONObject()
                            .put("id", p.getId())
                            .put("cid", p.getCourtId())
                            .put("avalibleNumbers", p.getAvailableNumbers())
                            .put("pid", p.getPlayerCreatedId())
                            .put("rid", p.getReservationId())
            );
        }
        return data.toString();
    }

    public  String getPlayerCreatedParties(String information) throws JSONException{
        JSONObject jsonObject = new JSONObject(information);
        Long Playerid = jsonObject.getLong("id");
        Optional<Player> PlayerOptional = playerRepository.findById(Playerid);
        if (PlayerOptional.isEmpty())
            throw new RuntimeException("Player Not Found");
        Player source = PlayerOptional.get();
        List<Party> parties = source.getPartiesCreated();
        List<JSONObject> data = new ArrayList<>();
        int i=0;
        for (Party p : parties) {
            data.add(
                    new JSONObject()
                            .put("id", p.getId())
                            .put("CourtOwner", courtOwnerRepository.findById(p.getCourtOwnerId()))
                            .put("Court",courtRepository.findById(p.getCourtId()))
                            .put("PlayersJoined", playerRepository.findById(p.getPlayerJoinedId().get(i)))
                            .put("avalibleNumbers", p.getAvailableNumbers())
                            .put("resrvations", reservationRepository.findById(p.getReservationId()))
            );
            i++;
        }
        return data.toString();
    }

    public  String getPlayerJoinedParties(String information) throws JSONException{
        JSONObject jsonObject = new JSONObject(information);
        Long Playerid = jsonObject.getLong("id");
        Optional<Player> PlayerOptional = playerRepository.findById(Playerid);
        if (PlayerOptional.isEmpty())
            throw new RuntimeException("Player Not Found");
        Player source = PlayerOptional.get();
        List<Party> parties = source.getPartiesJoined();
        List<JSONObject> data = new ArrayList<>();
        for (Party p : parties) {
            data.add(
                    new JSONObject()
                            .put("id", p.getId())
                            .put("CourtOwner", courtOwnerRepository.findById(p.getCourtOwnerId()))
                            .put("Court",courtRepository.findById(p.getCourtId()))
                            .put("PlayerCreated", playerRepository.findById(p.getPlayerCreatedId()))
                            .put("avalibleNumbers", p.getAvailableNumbers())
                            .put("resrvations", reservationRepository.findById(p.getReservationId()))
            );
        }
        return data.toString();
    }
}