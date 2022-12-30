package back.kickoff.kickoffback.Commands;

import back.kickoff.kickoffback.model.Party;
import back.kickoff.kickoffback.model.Player;

import java.util.ArrayList;
import java.util.List;

public class PartyFrontEnd {

    public Long partyid;
    public CourtOwnerFrontEnd courtOnwer;
    public CourtFrontEnd court;
    public String emptyplaces;
    public String fullplaces;
    public PlayerFrontEnd playerCreated;
    public List<PlayerFrontEnd> playerJoined;
    public FrontEndReservation reservation;

    public PartyFrontEnd(Party party) {
        partyid =party.getPartyId();
        courtOnwer = new CourtOwnerFrontEnd(party.getCourtOwner());
        court = new CourtFrontEnd(party.getCourt());
        emptyplaces = party.getNeededNumbers();
        int diff = Math.abs(Integer.parseInt(party.getNeededNumbers())-Integer.parseInt(party.getAvailableNumbers()));
        fullplaces = diff+"/"+party.getAvailableNumbers();
        playerCreated = new PlayerFrontEnd(party.getPlayerCreated());
        playerJoined = new ArrayList<>();
        for(Player p : party.getPlayerJoined())
        {
            playerJoined.add(new PlayerFrontEnd(p));
        }
        reservation = new FrontEndReservation(party.getReservation());
    }
}
