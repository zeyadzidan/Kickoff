package back.kickoff.kickoffback.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourtScheduleTest {
    CourtSchedule courtSchedule;

    @BeforeEach
    void setUp() {
        courtSchedule = new CourtSchedule();
    }

    @Test
    void getId() {
        Long id = 22L;
        courtSchedule.setId(id);
        assertEquals(id, courtSchedule.getId());
    }

    @Test
    void getBookedReservations() {
        List<Reservation> reservationList = new ArrayList<Reservation>();
        reservationList.add(new Reservation());
        courtSchedule.setBookedReservations(reservationList);
        assertEquals(reservationList, courtSchedule.getBookedReservations());

    }

    @Test
    void getPendingReservations() {
        List<Reservation> reservationList = new ArrayList<Reservation>();
        reservationList.add(new Reservation());
        courtSchedule.setPendingReservations(reservationList);
        assertEquals(reservationList, courtSchedule.getPendingReservations());
    }

    @Test
    void getStartWorkingHours() {
        Time t = new Time(12, 0, 0);
        courtSchedule.setStartWorkingHours(t);
        assertEquals(t, courtSchedule.getStartWorkingHours());

    }

    @Test
    void getEndWorkingHours() {
        Time t = new Time(12, 0, 0);
        courtSchedule.setEndWorkingHours(t);
        assertEquals(t, courtSchedule.getEndWorkingHours());
    }

    @Test
    void getEndMorning() {
        Time t = new Time(12, 0, 0);
        courtSchedule.setEndMorning(t);
        assertEquals(t, courtSchedule.getEndMorning());
    }

    @Test
    void getMinBookingHours() {
        Integer h = 2;
        courtSchedule.setMinBookingHours(h);
        assertEquals(h, courtSchedule.getMinBookingHours());
    }

    @Test
    void getMorningCost() {
        Integer c = 200;
        courtSchedule.setMorningCost(c);
        assertEquals(c, courtSchedule.getMorningCost());

    }

    @Test
    void getNightCost() {
        Integer c = 200;
        courtSchedule.setNightCost(c);
        assertEquals(c, courtSchedule.getNightCost());
    }
}