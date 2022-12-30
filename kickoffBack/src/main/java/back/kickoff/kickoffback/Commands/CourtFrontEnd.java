package back.kickoff.kickoffback.Commands;

import back.kickoff.kickoffback.model.Court;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
public class CourtFrontEnd {

    public long id ;
    public String cname ;
    public String state ;
    public String description ;
    public Time swh ;
    public Time ewh ;
    public int minBookingHours ;
    public int morningCost ;
    public int nightCost ;
    public Time endMorning ;

    public CourtFrontEnd(Court c){
        id = c.getId() ;
        cname = c.getCourtName() ;
        state = c.getState().toString() ;
        description = c.getDescription() ;
        swh = c.getCourtSchedule().getStartWorkingHours();
        ewh = c.getCourtSchedule().getEndWorkingHours();
        minBookingHours = c.getCourtSchedule().getMinBookingHours();
        morningCost = c.getCourtSchedule().getMorningCost();
        nightCost = c.getCourtSchedule().getNightCost();
        endMorning = c.getCourtSchedule().getEndMorning();
    }

}
