package back.kickoff.kickoffback.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourtTest {
    Court court;

    @BeforeEach
    void setUp() {
        court = new Court();
    }

    @Test
    void getIdTest() {
        Long idValue = 4L;
        court.setId(idValue);
        assertEquals(idValue, court.getId());
    }

    @Test
    void getCourtNameTest() {
        String courtName = "Court 1";
        court.setCourtName(courtName);
        assertEquals(courtName, court.getCourtName());
    }

    @Test
    void getCourtOwnerTest() {
        CourtOwner courtOwner = new CourtOwner();
        court.setCourtOwner(courtOwner);
        assertEquals(courtOwner, court.getCourtOwner());
    }

    @Test
    void getStateTest() {
        court.setState(CourtState.Active);
        assertEquals(CourtState.Active, court.getState());
    }

    @Test
    void getDescriptionTest() {
        String description = "five players";
        court.setDescription(description);
        assertEquals(description, court.getDescription());
    }

    @Test
    void getCourtScheduleTest() {
        CourtSchedule schedule = new CourtSchedule();
        court.setCourtSchedule(schedule);
        assertEquals(schedule, court.getCourtSchedule());
    }
}