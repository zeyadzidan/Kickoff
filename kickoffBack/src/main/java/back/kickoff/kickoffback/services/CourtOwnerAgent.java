package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.Court;
import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.CourtSchedule;
import back.kickoff.kickoffback.model.CourtState;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.repositories.CourtRepository;
import back.kickoff.kickoffback.repositories.ScheduleRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourtOwnerAgent {

    private final CourtOwnerRepository courtOwnerRepository;
    private final CourtRepository courtRepository;
    private final ScheduleRepository scheduleRepository;

    public CourtOwnerAgent(CourtOwnerRepository courtOwnerRepository, CourtRepository courtRepository, ScheduleRepository scheduleRepository) {
        this.courtOwnerRepository = courtOwnerRepository;
        this.courtRepository = courtRepository;
        this.scheduleRepository = scheduleRepository;
    }

    private CourtOwner findCourtOwner(Long id) {
        Optional<CourtOwner> courtOwnerOptional = courtOwnerRepository.findById(id);
        if (courtOwnerOptional.isEmpty())
            throw new RuntimeException("CourtOwner Not Found");
        return courtOwnerOptional.get();
    }

    public String findCourtOwnerCourts(Long courtOwnerId) throws JSONException {
        CourtOwner source = findCourtOwner(courtOwnerId);
        List<Court> courts = source.getCourts();
        List<JSONObject> data = new ArrayList<>();
        for (Court c : courts) {
            data.add(
                    new JSONObject()
                            .put("id", c.getId())
                            .put("cname", c.getCourtName())
                            .put("state", c.getState())
                            .put("description", c.getDescription())
                            .put("swh", c.getCourtSchedule().getStartWorkingHours())
                            .put("ewh", c.getCourtSchedule().getEndWorkingHours())
                            .put("minBookingHours", c.getCourtSchedule().getMinBookingHours())
                            .put("morningCost", c.getCourtSchedule().getMorningCost())
                            .put("nightCost", c.getCourtSchedule().getNightCost())
                            .put("endMorning", c.getCourtSchedule().getEndMorning())
            );
        }
        return data.toString();
    }

    public String addImage(String information) throws JSONException {
        JSONObject jsonObject = new JSONObject(information);
        Long ownerId;
        try {
            ownerId = jsonObject.getLong("ownerID");
        } catch (Exception e) {
            return "ownerID is required";
        }
        Optional<CourtOwner> optionalCourtOwner = courtOwnerRepository.findById(ownerId);
        if (optionalCourtOwner.isEmpty()) {
            return "CourtOwner does not exist";
        }
        CourtOwner courtOwner = optionalCourtOwner.get();

        String imageURL;
        try {
            imageURL = jsonObject.getString("imageURL");
            if (imageURL == null) {
                throw new NullPointerException();
            }
        } catch (Exception e) {
            return "imageURL is required";
        }
        courtOwner.setImage(imageURL);
        courtOwnerRepository.save(courtOwner);
        return "Success";
    }

    public String createCourt(String information) throws JSONException {
        JSONObject jsonObject = new JSONObject(information);
        Long ownerId;
        try {
            ownerId = jsonObject.getLong("ownerID");
        } catch (Exception e) {
            return "ownerID is required";

        }

        String courtName;
        try {
            courtName = jsonObject.getString("courtName");
            if (courtName == null)
                throw new NullPointerException();
        } catch (Exception e) {
            return "courtName is required";
        }
        String description;
        try {
            description = jsonObject.getString("description");
        } catch (Exception e) {
            description = "";
        }

        Integer morningCost;
        try {
            morningCost = jsonObject.getInt("morningCost");
        } catch (Exception e) {
            return "morningCost is required";
        }

        Integer nightCost;
        try {
            nightCost = jsonObject.getInt("nightCost");
        } catch (Exception e) {
            nightCost = morningCost;
        }

        int minBookingHours;
        try {
            minBookingHours = jsonObject.getInt("minBookingHours");
        } catch (Exception e) {
            minBookingHours = 1;
        }

        Time startWorkingHours, endWorkingHours, endMorning;
        try {
            int startHour = jsonObject.getInt("startWorkingHours");
            int finishHour = jsonObject.getInt("finishWorkingHours");
            startWorkingHours = new Time(startHour, 0, 0);
            endWorkingHours = new Time(finishHour, 0, 0);
        } catch (Exception e) {
            return "In valid Time";
        }
        try {
            int endMorningHour = jsonObject.getInt("endMorningHours");
            endMorning = new Time(endMorningHour, 0, 0);
        } catch (Exception e) {
            endMorning = endWorkingHours;
            ;
        }


        Optional<CourtOwner> courtOwnerOptional = courtOwnerRepository.findById(ownerId);
        if (courtOwnerOptional.isEmpty())
            return "CourtOwner does not exist";

        CourtSchedule courtSchedule = new CourtSchedule(startWorkingHours, endWorkingHours, endMorning, morningCost, nightCost, minBookingHours);
        scheduleRepository.save(courtSchedule);   // heree
        Court newCourt = new Court(courtName, courtOwnerOptional.get(), CourtState.Active, description, courtSchedule);
        courtRepository.save(newCourt);
        CourtOwner courtOwner = courtOwnerOptional.get();
        courtOwner.addCourt(newCourt);
        courtOwnerRepository.save(courtOwner);
        return "Success";
    }

    static class FrontEndCourt {
        Long id;
        String name;

        FrontEndCourt(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
