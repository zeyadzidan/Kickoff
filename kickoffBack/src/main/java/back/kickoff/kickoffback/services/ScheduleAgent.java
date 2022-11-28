package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.CourtSchedule;
import back.kickoff.kickoffback.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;

@Service
public class ScheduleAgent {

    @Autowired
    ScheduleRepository sr ;

    boolean setWorkingHours(Time start, Time end, Long Id){
        if(!sr.existsById(Id))
            return false ;

        CourtSchedule schedule = sr.getReferenceById(Id);
        schedule.setStartWorkingHours(start);
        schedule.setEndWorkingHours(end);
        sr.save(schedule) ;
        return true ;
    }

    boolean setMinTime(Integer minHours, Long Id){
        if(!sr.existsById(Id))
            return false ;

        CourtSchedule schedule = sr.getReferenceById(Id);
        schedule.setMinBookingHours(minHours);
        sr.save(schedule) ;
        return true ;
    }

    boolean setCostMorning(Integer cost, Long Id){
        if(!sr.existsById(Id))
            return false ;

        CourtSchedule schedule = sr.getReferenceById(Id);
        schedule.setMorningCost(cost);
        sr.save(schedule) ;
        return true ;
    }

    boolean setCostNight(Integer cost, Long Id){
        if(!sr.existsById(Id))
            return false ;

        CourtSchedule schedule = sr.getReferenceById(Id);
        schedule.setNightCost(cost);
        sr.save(schedule) ;
        return true ;
    }

    boolean setMorningEnd(Time morningEnd, Long Id){
        if(!sr.existsById(Id))
            return false ;

        CourtSchedule schedule = sr.getReferenceById(Id);
        schedule.setEndMorning(morningEnd);
        sr.save(schedule) ;
        return true ;
    }
/*
    boolean morningEnd(Time morningEnd, Long Id){
        if(!sr.existsById(Id))
            return false ;

        CourtSchedule schedule = sr.getReferenceById(Id);
        schedule.setEndMorning(morningEnd);
        sr.save(schedule) ;
        return true ;
    }

*/
}
