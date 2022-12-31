package back.kickoff.kickoffback.Commands.FrontEnd;

import back.kickoff.kickoffback.model.Party;
import back.kickoff.kickoffback.model.Player;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PartyFrontEnd {

    public Long id;
    public String COname;
    public String Cname;
    public String emptyplaces;
    public String fullplaces;
    public String Pname;
    public String Pimg;
    public String Date;
    public Time timeFrom;
    public  Time timeTo;
    public  int totalCost;
    public PartyFrontEnd(Party party) {
        id =party.getPartyId();
        COname = party.getCourtOwner().getUserName();
        Cname = party.getCourt().getCourtName();
        emptyplaces = party.getNeededNumbers();
        fullplaces = party.getAvailableNumbers();
        Pname =  party.getPlayerCreated().getName();
        Pimg = party.getPlayerCreated().getImage();
        System.out.println(Pimg);
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date = dateFormat.format(party.getReservation().getStartDate());;
        timeFrom = party.getReservation().getTimeFrom();
        timeTo = party.getReservation().getTimeTo();;
        totalCost = party.getReservation().getTotalCost();
    }
}
