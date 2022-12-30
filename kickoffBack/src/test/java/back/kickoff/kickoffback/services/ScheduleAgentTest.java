package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.*;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.repositories.PlayerRepository;
import back.kickoff.kickoffback.repositories.ReservationRepository;
import back.kickoff.kickoffback.repositories.ScheduleRepository;
import ch.qos.logback.core.encoder.EchoEncoder;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ScheduleAgentTest {
    ScheduleAgent scheduleAgent;

    @Mock
    ScheduleRepository sr;
    @Mock
    ReservationRepository rr;

    @BeforeEach
    void startUp() {
        MockitoAnnotations.openMocks(this);
        scheduleAgent = new ScheduleAgent(sr, rr);
    }
/*
    Reservation createPending(Date stDate, Date endDate){
        Player player = new Player();
        player.setName("Abdelrahman Gad");
        player.setPlayerType(PlayerType.Lite);
        Time timeFrom = new Time(10,0,0) ;
        Time timeTo = new Time(12,0,0) ;

        return new Reservation(player.getId(),player.getName(), 0L, 0L, stDate, endDate, timeFrom, timeTo, ReservationState.Pending , 0, 300);
    }

    Reservation createBooked(Date stDate, Date endDate){
        Player player = new Player();
        player.setName("Abdelrahman Gad");
        player.setPlayerType(PlayerType.Lite);
        Time timeFrom = new Time(16,0,0) ;
        Time timeTo = new Time(14,0,0) ;

        return new Reservation(player.getId(),player.getName(), 0L, 0L, stDate, endDate, timeFrom, timeTo, ReservationState.Booked , 100, 300);
    }

    @Test
    void getScheduleOverlapped() throws ParseException {
        CourtSchedule schedule = new CourtSchedule();
        schedule.setStartWorkingHours(new Time(9,0,0));
        schedule.setEndWorkingHours(new Time(23,0,0));
        Date stDate;
        Date endDate ;
        SimpleDateFormat obj = new SimpleDateFormat("MM/dd/yyyy");
        long date1 = obj.parse("12/25/2022").getTime();
        stDate = new Date(date1);
        endDate = new Date(date1);

        Reservation pendingR = createPending(stDate, endDate) ;
        Reservation bookedR = createBooked(stDate, endDate) ;
        ArrayList<Reservation> pending = new ArrayList<>() ;
        pending.add(pendingR) ;
        ArrayList<Reservation> booked = new ArrayList<>() ;
        booked.add(bookedR) ;
        schedule.setPendingReservations(pending);
        schedule.setBookedReservations(booked);
        schedule.setHistory(new ArrayList<>());

        List<Reservation> compare = new ArrayList<Reservation>();
        compare.add(bookedR);
        compare.add(pendingR) ;

        List<Reservation> result = scheduleAgent.getScheduleOverlapped(stDate,endDate,new Time(9,0,0) , new Time(23,0,0), schedule);
        assertEquals(compare, result);


    }

    @Test
    void checkPendingConstraintfalse() throws ParseException {
        Date stDate;
        Date endDate ;
        SimpleDateFormat obj = new SimpleDateFormat("MM/dd/yyyy");
        long date1 = obj.parse("12/25/2022").getTime();
        long date2 = obj.parse("12/16/2022").getTime();
        stDate = new Date(date1);
        endDate = new Date(date1);
        Date reserved = new Date(date2);
        Reservation pending = createPending(stDate, endDate) ;
        pending.setDateReserved(reserved);
        pending.setTimeReserved(new Time(5,0,0));

        assertTrue(scheduleAgent.checkPendingConstraint(pending));

    }
    @Test
    void checkPendingConstraint() throws ParseException {
        Date stDate;
        Date endDate ;
        SimpleDateFormat obj = new SimpleDateFormat("MM/dd/yyyy");
        long date1 = obj.parse("12/28/2022").getTime();
        long date2 = obj.parse("12/20/2022").getTime();
        stDate = new Date(date1);
        endDate = new Date(date1);
        Date reserved = new Date(date2);
        Reservation pending = createPending(stDate, endDate) ;
        pending.setDateReserved(reserved);
        pending.setTimeReserved(new Time(5,0,0));

        assertFalse(scheduleAgent.checkPendingConstraint(pending));

    }
*/
}