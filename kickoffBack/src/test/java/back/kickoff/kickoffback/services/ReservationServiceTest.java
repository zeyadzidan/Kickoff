package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.Court;
import back.kickoff.kickoffback.model.CourtSchedule;
import back.kickoff.kickoffback.model.CourtState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservationServiceTest {
    ReservationService reservationService;

    Court court ;
    LocalDate stDay, endDay;
    LocalTime stTime, endTime;

    @BeforeEach
    void startUp() {
        reservationService = new ReservationService();

        court = new Court() ;
        //create court schedule
        CourtSchedule courtSchedule = new CourtSchedule() ;
        courtSchedule.setStartWorkingHours(Time.valueOf(LocalTime.of(9,0,0)));
        courtSchedule.setEndWorkingHours(Time.valueOf(LocalTime.of(23,0,0)));
        courtSchedule.setEndMorning(Time.valueOf(LocalTime.of(16,0,0)));
        courtSchedule.setMorningCost(150);
        courtSchedule.setNightCost(200);

        court.setCourtSchedule(courtSchedule);
        stDay = LocalDate.of(2023,2,3);
        endDay = stDay ;
    }

    void setUpMorning(){
        stTime = LocalTime.of(14,0,0);
        endTime = LocalTime.of(16,0,0);
    }
    void setUpMorning2(){
        stTime = LocalTime.of(9,0,0);
        endTime = LocalTime.of(11,0,0);
    }
    void setUpHalfandHalf(){
        stTime = LocalTime.of(15,0,0);
        endTime = LocalTime.of(17,0,0);
    }
    void setUpNight(){
        stTime = LocalTime.of(16,0,0);
        endTime = LocalTime.of(18,0,0);
    }
    void setUpNight2(){
        stTime = LocalTime.of(21,0,0);
        endTime = LocalTime.of(23,0,0);
    }


    @Test
    void calcTotalCostTest1() {
        setUpMorning();
        int cost = reservationService.calcTotalCost(stDay, endDay, stTime, endTime, court);
        assertEquals(cost, 300);
    }

    @Test
    void calcTotalCostTest2() {
        setUpMorning2();
        int cost = reservationService.calcTotalCost(stDay, endDay, stTime, endTime, court);
        assertEquals(cost, 300);
    }

    @Test
    void calcTotalCostTest3() {
        setUpHalfandHalf();
        int cost = reservationService.calcTotalCost(stDay, endDay, stTime, endTime, court);
        assertEquals(cost, 350);
    }
    @Test
    void calcTotalCostTest4() {
        setUpNight();
        int cost = reservationService.calcTotalCost(stDay, endDay, stTime, endTime, court);
        assertEquals(cost, 400);
    }
    @Test
    void calcTotalCostTest5() {
        setUpNight2();
        int cost = reservationService.calcTotalCost(stDay, endDay, stTime, endTime, court);
        assertEquals(cost, 400);
    }


}