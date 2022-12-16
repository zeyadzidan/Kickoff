package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.*;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.repositories.CourtRepository;
import back.kickoff.kickoffback.repositories.ReservationRepository;
import back.kickoff.kickoffback.repositories.ScheduleRepository;
import com.google.gson.Gson;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BookingAgentTest {
    BookingAgent bookingAgent;

    @Mock
    CourtOwnerRepository courtOwnerRepository;
    @Mock
    ScheduleRepository scheduleRepository;
    @Mock
    CourtRepository courtRepository;
    @Mock
    ReservationRepository reservationRepository;
    @Mock
    ReservationService reservationService;

    @Mock
    ScheduleAgent scheduleAgent ;

    @BeforeEach
    public void setUp()  {
        MockitoAnnotations.openMocks(this);

        bookingAgent = new BookingAgent(courtRepository, scheduleRepository, courtOwnerRepository,
                reservationRepository, reservationService);
        scheduleAgent = new ScheduleAgent(scheduleRepository, reservationRepository) ;
    }
    @Test
    void book() throws JSONException {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("reservationId", 11L);
        hm.put("moneyPaid", 1000);
        String information = new Gson().toJson(hm);
        Reservation reservation = new Reservation();
        CourtSchedule courtSchedule = new CourtSchedule();
        reservation.setTotalCost(1000);
        reservation.setCourtID(121L);
        courtSchedule.setPendingReservations(new ArrayList<>());
        courtSchedule.setBookedReservations(new ArrayList<>());
        courtSchedule.getPendingReservations().add(reservation);
        courtSchedule.setId(100L);
        Court court = new Court();
        court.setCourtSchedule(courtSchedule);
        when(reservationRepository.findById(11L)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        when(scheduleRepository.findById(121L)).thenReturn(Optional.of(courtSchedule));
        String res = bookingAgent.book(information);
        assertEquals(res, "Success");
    }

    @Test
    void cancelBookedReservation() throws JSONException {

        Reservation reservation = new Reservation();
        reservation.setState(ReservationState.Pending);
        reservation.setCourtID(223L);
        when(reservationRepository.findById(11L)).thenReturn(Optional.of(reservation));
        when(scheduleRepository.findById(223L)).thenReturn(Optional.of(new CourtSchedule()));
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("id", 11L) ;
        String information = new Gson().toJson(hm);
        String res = bookingAgent.cancelBookedReservation(information);
        assertEquals(res, "Reservation not booked" );
    }

    @Test
    void cancelPendingReservation() throws JSONException {
        Reservation reservation = new Reservation();
        reservation.setState(ReservationState.Expired);
        reservation.setCourtID(223L);
        when(reservationRepository.findById(11L)).thenReturn(Optional.of(reservation));
        when(scheduleRepository.findById(223L)).thenReturn(Optional.of(new CourtSchedule()));
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("id", 11L) ;
        String information = new Gson().toJson(hm);
        String res = bookingAgent.cancelPendingReservation(information);
        assertEquals(res, "Reservation is not pending" );
    }

    @Test
    void setPending() throws JSONException, ParseException {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("courtId", 11L);
        hm.put("courtOwnerId", 1L);
        hm.put("startDate", "12/16/2022");
        hm.put("endDate", "12/16/2022");
        hm.put("startHour", 14);
        hm.put("finishHour", 16);
        hm.put("playerId", 44L);
        hm.put("playerName", "Abdelrahman Gad");
        Court court = new Court();
        CourtSchedule schedule = new CourtSchedule();
        schedule.setStartWorkingHours(new Time(9,0,0));
        schedule.setEndWorkingHours(new Time(23,0,0));
        schedule.setPendingReservations(new ArrayList<Reservation>());
        schedule.setBookedReservations(new ArrayList<Reservation>());
        CourtOwner courtOwner = new CourtOwner();
        court.setCourtOwner(courtOwner);
        court.setCourtSchedule(schedule);
        String information = new Gson().toJson(hm);
        when(courtOwnerRepository.findById(1L)).thenReturn(Optional.of(courtOwner));
        when(courtRepository.findById(11L)).thenReturn(Optional.of(court));
        Date stDate;
        Date endDate ;
        SimpleDateFormat obj = new SimpleDateFormat("MM/dd/yyyy");
        long date1 = obj.parse("12/16/2022").getTime();
        long date2 = obj.parse("12/16/2022").getTime();
        stDate = new Date(date1);
        endDate = new Date(date2);
        when(reservationService.calcTotalCost(stDate, endDate, new Time(14, 0, 0),new Time(16, 0, 0), court)).thenReturn(300);
        when(scheduleRepository.save(new CourtSchedule())).thenReturn(new CourtSchedule());
        when(courtRepository.save(court)).thenReturn(new Court());
        String res = bookingAgent.setPending(information);
        assertEquals(res, "Success");
    }
}