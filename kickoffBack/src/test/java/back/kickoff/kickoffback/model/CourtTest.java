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
    void getId() {
        Long idValue = 4L;
        court.setId(idValue);
        assertEquals(idValue, court.getId());
    }

    @Test
    void getCourtName() {
        String courtName = "Court 1";
        court.setCourtName(courtName);
        assertEquals(courtName, court.getCourtName());
    }

    @Test
    void getCourtOwner() {
        CourtOwner courtOwner = new CourtOwner();
        court.setCourtOwner(courtOwner);
        assertEquals(courtOwner, court.getCourtOwner());
    }

    @Test
    void getState() {
        court.setState(CourtState.Active);
        assertEquals(CourtState.Active, court.getState());
    }

    @Test
    void getDescription() {
        String description = "five players";
        court.setDescription(description);
        assertEquals(description, court.getDescription());
    }

    @Test
    void getCourtSchedule() {
        CourtSchedule schedule = new CourtSchedule();
        court.setCourtSchedule(schedule);
        assertEquals(schedule, court.getCourtSchedule());
    }
}