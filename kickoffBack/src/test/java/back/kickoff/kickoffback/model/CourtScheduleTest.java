package back.kickoff.kickoffback.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.time.LocalTime;
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
    void getIdTest() {
        Long id = 22L;
        courtSchedule.setId(id);
        assertEquals(id, courtSchedule.getId());
    }

    @Test
    void getBookedReservationsTest() {
        List<Reservation> reservationList = new ArrayList<Reservation>();
        reservationList.add(new Reservation());
        courtSchedule.setBookedReservations(reservationList);
        assertEquals(reservationList, courtSchedule.getBookedReservations());

    }

    @Test
    void getPendingReservationsTest() {
        List<Reservation> reservationList = new ArrayList<Reservation>();
        reservationList.add(new Reservation());
        courtSchedule.setPendingReservations(reservationList);
        assertEquals(reservationList, courtSchedule.getPendingReservations());
    }

    @Test
    void getStartWorkingHoursTest() {
        Time t =  Time.valueOf(LocalTime.of(12, 0, 0));
        courtSchedule.setStartWorkingHours(t);
        assertEquals(t, courtSchedule.getStartWorkingHours());

    }

    @Test
    void getEndWorkingHoursTest() {
        Time t =  Time.valueOf(LocalTime.of(12, 0, 0));
        courtSchedule.setEndWorkingHours(t);
        assertEquals(t, courtSchedule.getEndWorkingHours());
    }

    @Test
    void getEndMorningTest() {
        Time t =  Time.valueOf(LocalTime.of(12, 0, 0));
        courtSchedule.setEndMorning(t);
        assertEquals(t, courtSchedule.getEndMorning());
    }

    @Test
    void getMinBookingHoursTest() {
        Integer h = 2;
        courtSchedule.setMinBookingHours(h);
        assertEquals(h, courtSchedule.getMinBookingHours());
    }

    @Test
    void getMorningCostTest() {
        Integer c = 200;
        courtSchedule.setMorningCost(c);
        assertEquals(c, courtSchedule.getMorningCost());

    }

    @Test
    void getNightCostTest() {
        Integer c = 200;
        courtSchedule.setNightCost(c);
        assertEquals(c, courtSchedule.getNightCost());
    }
}