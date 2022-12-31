package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.Add.AddImageCommand;
import back.kickoff.kickoffback.Commands.FrontEnd.CourtFrontEnd;
import back.kickoff.kickoffback.Commands.Add.CreateCourtCommand;
import back.kickoff.kickoffback.model.Court;
import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.CourtSchedule;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.repositories.CourtRepository;
import back.kickoff.kickoffback.repositories.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourtOwnerAgentTest {
    CourtOwnerAgent courtOwnerAgent;
    @Mock
    CourtOwnerRepository courtOwnerRepository;
    @Mock
    CourtRepository courtRepository;
    @Mock
    ScheduleRepository scheduleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        courtOwnerAgent = new CourtOwnerAgent(courtOwnerRepository, courtRepository, scheduleRepository);
    }

    @Test
    void findCourtOwnerCourts() throws Exception {
        Long id = 22L;
        CourtOwner courtOwner = new CourtOwner();
        courtOwner.setCourts(new ArrayList<Court>());
        Mockito.when(courtOwnerRepository.findById(id)).thenReturn(Optional.of(courtOwner));
        List<CourtFrontEnd> res = courtOwnerAgent.findCourtOwnerCourts(id);
        assertEquals(res, new ArrayList<CourtFrontEnd>());
    }

    @Test
    void addImage() throws Exception {
        AddImageCommand command = new AddImageCommand(1L, "thisIsAnImage.com");
        CourtOwner courtOwner = new CourtOwner();
        Mockito.when(courtOwnerRepository.findById(1L)).thenReturn(Optional.of(courtOwner));
        Mockito.when(courtOwnerRepository.save(courtOwner)).thenReturn(courtOwner);
        courtOwnerAgent.addImage(command);
    }

    @Test
    void createCourt() throws Exception {
        CreateCourtCommand createCourtCommand = new CreateCourtCommand(1L, "A",
                "grass with 5 players in each team", 100, 150,
                1, 1 ,21);
        CourtOwner courtOwner = new CourtOwner();
        courtOwner.setCourts(new ArrayList<Court>());
        Mockito.when(courtOwnerRepository.findById(1L)).thenReturn(Optional.of(courtOwner));
        Mockito.when(scheduleRepository.save(new CourtSchedule())).thenReturn(new CourtSchedule());
        Mockito.when(courtRepository.save(new Court())).thenReturn(new Court());
        Mockito.when(courtOwnerRepository.save(courtOwner)).thenReturn(courtOwner);
        courtOwnerAgent.createCourt(createCourtCommand);
    }
}