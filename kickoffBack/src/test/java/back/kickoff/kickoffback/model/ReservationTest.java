package back.kickoff.kickoffback.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservationTest {

    Reservation reservation;

    @BeforeEach
    void setUp() {
        reservation = new Reservation();
    }


    @Test
    void getId() {
        Long id = 1L;
        reservation.setId(id);
        assertEquals(id, reservation.getId());

    }

//    @Test
//    void getMainPlayer() {
//        Long id = 22L ;
//        String name = "Gad" ;
//        Player player = new Player() ;
//        player.setName(name);
//        player.setId(id);
//
//
//        assertEquals(player, reservation.getMainPlayer());
//    }

    @Test
    void getCourtID() {
        Long id = 11L;
        reservation.setCourtID(id);
        assertEquals(id, reservation.getCourtID());
    }

    @Test
    void getCourtOwnerID() {
        Long id = 143L;
        reservation.setCourtOwnerID(id);
        assertEquals(id, reservation.getCourtOwnerID());
    }

    @Test
    void getStartDate() {
        Date d = new Date(2022, 11, 8);
        reservation.setStartDate(d);
        assertEquals(d, reservation.getStartDate());

    }

    @Test
    void getEndDate() {
        Date d = new Date(2022, 11, 8);
        reservation.setEndDate(d);
        assertEquals(d, reservation.getEndDate());
    }

    @Test
    void getTimeFrom() {
        Time t = new Time(12, 0, 0);
        reservation.setTimeFrom(t);
        assertEquals(t, reservation.getTimeFrom());

    }

    @Test
    void getTimeTo() {
        Time t = new Time(12, 0, 0);
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
    void getMoneyPayed() {
        Integer c = 100;
        reservation.setMoneyPayed(c);
        assertEquals(c, reservation.getMoneyPayed());

    }

    @Test
    void getTotalCost() {
        Integer c = 400;
        reservation.setTotalCost(c);
        assertEquals(c, reservation.getTotalCost());
    }

    @Test
    void getReceiptUrl() {
        String receiptUrl = "receipt.png";
        reservation.setReceiptUrl(receiptUrl);
        assertEquals(receiptUrl, reservation.getReceiptUrl());
    }

    @Test
    void getDateReserved() {
        Date d = new Date(2022, 12, 20);
        reservation.setDateReserved(d);
        assertEquals(d, reservation.getDateReserved());
    }

    @Test
    void getTimeReserved() {
        Time t = new Time(16, 14, 9);
        reservation.setTimeReserved(t);
        assertEquals(t, reservation.getTimeReserved());
    }
}