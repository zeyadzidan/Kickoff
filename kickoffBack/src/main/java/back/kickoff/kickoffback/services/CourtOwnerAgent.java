package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.Court;
import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.CourtSchedule;
import back.kickoff.kickoffback.model.CourtState;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.repositories.CourtRepository;
import back.kickoff.kickoffback.repositories.ScheduleRepository;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourtOwnerAgent {

    static class FrontEndCourt{
        Long id;
        String name;
        String state ;
        String description;
        int morningCost ;
        int nightCost ;
        int minBookingHours ;
        int startingWorkingHours ;
        int finishWorkingHours ;
        int finishMorning ;
        FrontEndCourt(Court court){
            this.id = court.getId();
            this.name = court.getCourtName();
            if(court.getState()==CourtState.Active)
                this.state = "Active" ;
            else
                this.state = "OutOfOrder" ;
            this.description = court.getDescription();
            this.morningCost = court.getCourtSchedule().getMorningCost();
            this.nightCost = court.getCourtSchedule().getNightCost();
            this.minBookingHours = court.getCourtSchedule().getMinBookingHours();
            this.startingWorkingHours = court.getCourtSchedule().getStartWorkingHours().getHours();
            this.finishWorkingHours = court.getCourtSchedule().getEndWorkingHours().getHours();
            this.finishMorning = court.getCourtSchedule().getEndMorning().getHours();


        }
    }

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
        /*
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

         */
        List<Court> courts = source.getCourts() ;
        List<FrontEndCourt> data = new ArrayList<FrontEndCourt>() ;
        for (Court c: courts){
            data.add(new FrontEndCourt(c));
        }
        System.out.println(data);

        return new Gson().toJson(data);
    }


    public String addImage(String information) throws JSONException{
        JSONObject jsonObject = new JSONObject(information);
        Long ownerId ;
        try {
            ownerId  =  jsonObject.getLong("ownerID");
        }catch (Exception e){
            return "ownerID is required";
        }
        Optional<CourtOwner> optionalCourtOwner = courtOwnerRepository.findById(ownerId) ;
        if(optionalCourtOwner.isEmpty()){
            return "CourtOwner does not exist";
        }
        CourtOwner courtOwner = optionalCourtOwner.get() ;

        String imageURL ;
        try {
            imageURL  =  jsonObject.getString("imageURL");
            if(imageURL == null){
                throw new NullPointerException() ;
            }
        }catch (Exception e){
            return "imageURL is required";
        }
        courtOwner.setImage(imageURL);
        courtOwnerRepository.save(courtOwner) ;
        return "Success" ;
    }

    public String createCourt(String information) throws JSONException {
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
        String description ;
        try {
            description = jsonObject.getString("description");
        }catch (Exception e){
            description = "";
        }

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

        CourtSchedule courtSchedule = new CourtSchedule(startWorkingHours, endWorkingHours, endMorning, morningCost, nightCost, minBookingHours);
        scheduleRepository.save(courtSchedule) ;   // heree
        Court newCourt = new Court(courtName, courtOwnerOptional.get(), CourtState.Active, description, courtSchedule);
        courtRepository.save(newCourt);
        CourtOwner courtOwner = courtOwnerOptional.get() ;
        courtOwner.addCourt(newCourt);
        courtOwnerRepository.save(courtOwner) ;
        return "Success";
    }
}
