package back.kickoff.kickoffback.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservationTest {

    Reservation reservation;

    @BeforeEach
    void setUp() {
        reservation = new Reservation();
    }


    @Test
    void getIdTest() {
        Long id = 1L;
        reservation.setId(id);
        assertEquals(id, reservation.getId());

    }

    @Test
    void getMainPlayerTest() {
        Long id = 22L ;
        String name = "Gad" ;
        Player player = new Player() ;
        player.setName(name);
        player.setId(id);
        player.setReservations(new ArrayList<>(1));
        reservation.setMainPlayer(player);
        player.getReservations().add(reservation) ;


        assertEquals(player, reservation.getMainPlayer());
    }

    @Test
    void getCourtIDTest() {
        Long id = 11L;
        reservation.setCourtID(id);
        assertEquals(id, reservation.getCourtID());
    }

    @Test
    void getCourtOwnerIDTest() {
        Long id = 143L;
        reservation.setCourtOwnerID(id);
        assertEquals(id, reservation.getCourtOwnerID());
    }

    @Test
    void getStartDateTest() {
        Date d = Date.valueOf(LocalDate.of(2022, 11, 8));
        reservation.setStartDate(d);
        assertEquals(d, reservation.getStartDate());

    }

    @Test
    void getEndDateTest() {
        Date d = Date.valueOf(LocalDate.of(2022, 11, 8));
        reservation.setEndDate(d);
        assertEquals(d, reservation.getEndDate());
    }

    @Test
    void getTimeFromTest() {
        Time t =  Time.valueOf(LocalTime.of(12, 0, 0));
        reservation.setTimeFrom(t);
        assertEquals(t, reservation.getTimeFrom());

    }

    @Test
    void getTimeToTest() {
        Time t =  Time.valueOf(LocalTime.of(12, 0, 0));
        reservation.setTimeTo(t);
        assertEquals(t, reservation.getTimeTo());
    }

    @Test
    void getState() {
        ReservationState s = ReservationState.Expired;
        reservation.setState(s);
        assertEquals(s, reservation.getState());
    }

    @Test
    void getMoneyPayedTest() {
        Integer c = 100;
        reservation.setMoneyPayed(c);
        assertEquals(c, reservation.getMoneyPayed());

    }

    @Test
    void getTotalCostTest() {
        Integer c = 400;
        reservation.setTotalCost(c);
        assertEquals(c, reservation.getTotalCost());
    }

    @Test
    void getReceiptUrlTest() {
        String receiptUrl = "receipt.png";
        reservation.setReceiptUrl(receiptUrl);
        assertEquals(receiptUrl, reservation.getReceiptUrl());
    }

    @Test
    void getDateReservedTest() {
        Date d = Date.valueOf(LocalDate.of(2022, 11, 8));
        reservation.setDateReserved(d);
        assertEquals(d, reservation.getDateReserved());
    }

    @Test
    void getTimeReservedTest() {
        Time t =  Time.valueOf(LocalTime.of(16, 9, 16));
        reservation.setTimeReserved(t);
        assertEquals(t, reservation.getTimeReserved());
    }
}