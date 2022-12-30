package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.CreateParty;
import back.kickoff.kickoffback.Commands.PartyFrontEnd;
import back.kickoff.kickoffback.Commands.PlayerFrontEnd;
import back.kickoff.kickoffback.model.*;
import back.kickoff.kickoffback.repositories.*;
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
    private final SubscriptionsRepository subscriptionsRepository;
    public PartyServices(PartyRepository partyRepository,CourtOwnerRepository courtOwnerRepository,PlayerRepository playerRepository, CourtRepository courtRepository,ReservationRepository reservationRepository,SubscriptionsRepository subscriptionsRepository) {
        this.partyRepository = partyRepository;
        this.courtOwnerRepository = courtOwnerRepository;
        this.playerRepository =playerRepository;
        this.courtRepository=courtRepository;
        this.reservationRepository =reservationRepository;
        this.subscriptionsRepository =subscriptionsRepository;
    }
    public boolean CreateParty(CreateParty command) throws JSONException {
        try {

            Optional<Reservation> reservationOptional = reservationRepository.findById(command.reservationId);
            Reservation reservation = reservationOptional.get();
            if(partyRepository.existsByReservation(reservation))
            {
                return false;
            }
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
        Long playerid = jsonObject.getLong("pid");


        Optional<CourtOwner> courtOwnerOptional = courtOwnerRepository.findById(CourtOwnerid);
        if (courtOwnerOptional.isEmpty())
            throw new RuntimeException("CourtOwner Not Found");
        CourtOwner source = courtOwnerOptional.get();
        Optional<Player> playerOptional = playerRepository.findById(playerid);
         Player source2 = playerOptional.get();
        List<Party> parties = source.getParties();
        boolean check =false;
        List<PartyFrontEnd> data = new ArrayList<>();
        for (Party p : parties) {
            List<Player> playerss = p.getPlayerJoined();
            Player player = p.getPlayerCreated();
            for(Player p2 :playerss)
            {
                if(p2.getId() ==source2.getId())
                {
                    check = true;
                    break;
                }
            }
            if(source2.getId() == player.getId())
            {
                check = true;
            }
            if(!check)
            {
                data.add(new PartyFrontEnd(p));

            }
            else
            {
                check = false;
            }
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

    public  List<PartyFrontEnd> getPartiesofplayerJoined(String information) throws JSONException{
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
    public  List<PlayerFrontEnd> getplayersofParties(String information) throws JSONException{
        JSONObject jsonObject = new JSONObject(information);
        Long partyid = jsonObject.getLong("id");

        Optional<Party> PartyOptional = partyRepository.findById(partyid);
        if (PartyOptional.isEmpty())
            throw new RuntimeException("Player Not Found");
        Party source = PartyOptional.get();
        List<Player> parties = source.getPlayerJoined();
        List<PlayerFrontEnd> data = new ArrayList<>();
        for (Player p : parties) {
            data.add(new PlayerFrontEnd(p));
        }
        System.out.println(data);
        return data;
    }
    public  List<PartyFrontEnd> getPartiesSubscribed(String information) throws JSONException{
        JSONObject jsonObject = new JSONObject(information);
        Long Playerid = jsonObject.getLong("id");


        Optional<Player> PlayerOptional = playerRepository.findById(Playerid);
        if (PlayerOptional.isEmpty())
            throw new RuntimeException("Player Not Found");
        Player source = PlayerOptional.get();
        List<Subscription> subscribers =subscriptionsRepository.findByPid(source.getId());
        List<PartyFrontEnd> data = new ArrayList<>();
        boolean check =false;
        for (Subscription s:subscribers ){
            Long CourtOwnerid =s.getCoid();
            Optional<CourtOwner> courtOwnerOptional = courtOwnerRepository.findById(CourtOwnerid);
            CourtOwner source2 = courtOwnerOptional.get();
            List<Party> parties = source2.getParties();
            for (Party p : parties) {
                  List<Player> playerss = p.getPlayerJoined();
                  for(Player p2 :playerss)
                  {
                      if(p2.getId() ==source.getId())
                      {
                          check = true;
                          break;
                      }
                  }
                  if(!check)
                  {
                      data.add(new PartyFrontEnd(p));

                  }
                  else
                  {
                      check = false;
                  }
            }
        }
        System.out.println(data);
        return data;
    }
}