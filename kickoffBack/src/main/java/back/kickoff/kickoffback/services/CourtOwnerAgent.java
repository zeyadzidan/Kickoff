package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.Court;
import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.CourtState;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.repositories.CourtRepository;
import back.kickoff.kickoffback.repositories.ScheduleRepository;
import com.google.gson.Gson;
import org.hibernate.sql.exec.ExecutionException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.HashMap;
import java.util.Optional;

@Service
public class CourtOwnerAgent {
    private final CourtOwnerRepository courtOwnerRepository;
    private final CourtRepository courtRepository;
    private final ScheduleRepository scheduleRepository ;

    public CourtOwnerAgent(CourtOwnerRepository courtOwnerRepository, CourtRepository courtRepository, ScheduleRepository scheduleRepository) {
        this.courtOwnerRepository = courtOwnerRepository;
        this.courtRepository = courtRepository;
        this.scheduleRepository = scheduleRepository ;
    }
    private CourtOwner findCourtOwner(Long id)
    {
        Optional<CourtOwner> courtOwnerOptional = courtOwnerRepository.findById(id);
        if(courtOwnerOptional.isEmpty())
            throw new RuntimeException("CourtOwner Not Found");
        return courtOwnerOptional.get();
    }
    public String findCourtOwnerCourts(Long courtOwnerId)
    {
        CourtOwner source = findCourtOwner(courtOwnerId);
        if(source == null)
            return "CourtOwner Not Found" ;
        int n = source.getCourts().size();
        HashMap<String, Object> hmAns = new HashMap<>();
        hmAns.put("courtNumber", n);
        HashMap<String, Object> hmCourt = new HashMap<>();
        Court[] courtsArr = source.getCourts().toArray(new Court[n]);
        for(int i = 0; i < n; i++)
        {
            hmCourt.put(Integer.toString(i), courtsArr[i]);
        }
        hmAns.put("courts", hmCourt);
        return new Gson().toJson(hmAns);
    }
    public String createCourt(String information) throws JSONException {
        //schedule missing
        JSONObject jsonObject = new JSONObject(information);
        Long ownerId ;
        try {
            ownerId  =  jsonObject.getLong("ownerID");
        }catch (Exception e){
            return "ownerID is required";

        }

        String courtName;
        try {
            courtName  =  jsonObject.getString("courtName");
            if (courtName== null)
                throw new NullPointerException();
        }catch (Exception e){
            return "courtName is required";
        }
        String description = jsonObject.getString("description");

        Integer morningCost ;
        try {
            morningCost = jsonObject.getInt("morningCost");
        }catch (Exception e){
            return "morningCost is required";
        }

        Integer nightCost ;
        try {
            nightCost = jsonObject.getInt("nightCost");
        }catch (Exception e){
            nightCost = morningCost ;
        }

        int minBookingHours ;
        try {
            minBookingHours = jsonObject.getInt("minBookingHours");
        }catch (Exception e){
            minBookingHours = 1 ;
        }

        Time startWorkingHours,endWorkingHours,endMorning ;
        try
        {
            int startHour = jsonObject.getInt("startWorkingHours");
            int finishHour = jsonObject.getInt("finishWorkingHours");
            startWorkingHours = new Time(startHour, 0, 0);
            endWorkingHours = new Time(finishHour, 0, 0);
        }
        catch (Exception e)
        {
            return "In valid Time";
        }

        try{
            int finishMorning = jsonObject.getInt("finishMorning");
            endMorning  = new Time(finishMorning, 0, 0);

        }catch (Exception e){
            endMorning=endWorkingHours ;
        }

        Optional<CourtOwner> courtOwnerOptional = courtOwnerRepository.findById(ownerId);
        if(courtOwnerOptional.isEmpty())
            return "CourtOwner does not exist";
        Court newCourt = new Court(courtName, courtOwnerOptional.get(), CourtState.Active, description, startWorkingHours,
                endWorkingHours, endMorning, morningCost, nightCost, minBookingHours);
        courtRepository.save(newCourt);
        CourtOwner courtOwner = courtOwnerOptional.get() ;
        courtOwner.addCourt(newCourt);
        courtOwnerRepository.save(courtOwner) ;
        scheduleRepository.save(newCourt.getCourtSchedule()) ;
        return "Success";
    }
}
