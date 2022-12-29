package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.AddImageCommand;
import back.kickoff.kickoffback.Commands.CourtFrontEnd;
import back.kickoff.kickoffback.Commands.CreateCourtCommand;
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

    private CourtOwner findCourtOwner(Long id) throws Exception {
        Optional<CourtOwner> courtOwnerOptional = courtOwnerRepository.findById(id);
        if (courtOwnerOptional.isEmpty())
            throw new Exception("CourtOwner Not Found");
        return courtOwnerOptional.get();
    }

    public List<CourtFrontEnd> findCourtOwnerCourts(Long courtOwnerId) throws Exception {
        CourtOwner source = findCourtOwner(courtOwnerId);
        List<Court> courts = source.getCourts();
        List<CourtFrontEnd> data = new ArrayList<>();
        for (Court c : courts) {
            data.add(new CourtFrontEnd(c));
        }
        return data;
    }

    public void addImage(AddImageCommand command) throws Exception {

        Optional<CourtOwner> optionalCourtOwner = courtOwnerRepository.findById(command.getOwnerId());
        if (optionalCourtOwner.isEmpty()) {
            throw new Exception("CourtOwner does not exist");
        }
        CourtOwner courtOwner = optionalCourtOwner.get();
        courtOwner.setImage(command.imageURL);
        courtOwnerRepository.save(courtOwner);
    }


    public void createCourt(CreateCourtCommand command) throws Exception {

        Optional<CourtOwner> courtOwnerOptional = courtOwnerRepository.findById(command.ownerID);
        if (courtOwnerOptional.isEmpty())
            throw new Exception("CourtOwner does not exist");

        CourtSchedule courtSchedule = new CourtSchedule(command.startWorkingHourTime, command.endWorkingHours, command.endMorning,
                command.morningCost, command.nightCost, command.minBookingHours);
        scheduleRepository.save(courtSchedule);

        CourtOwner courtOwner = courtOwnerOptional.get();
        Court newCourt = new Court(command.courtName, courtOwner, CourtState.Active, command.description, courtSchedule);
        courtRepository.save(newCourt);
        courtOwner.addCourt(newCourt);
        courtOwnerRepository.save(courtOwner);
    }

}
