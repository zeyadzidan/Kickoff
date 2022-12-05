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

    @BeforeEach
    public void setUp()  {
        MockitoAnnotations.openMocks(this);

        bookingAgent = new BookingAgent(courtRepository, scheduleRepository, courtOwnerRepository,
                reservationRepository, reservationService);
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
    void cancelBookedReservation() {

        Reservation reservation = new Reservation();
        reservation.setState(ReservationState.Pending);
        reservation.setCourtID(223L);
        when(reservationRepository.findById(11L)).thenReturn(Optional.of(reservation));
        when(scheduleRepository.findById(223L)).thenReturn(Optional.of(new CourtSchedule()));
        String res = bookingAgent.cancelBookedReservation(11L);
        assertEquals(res, "Reservation not booked" );
    }

    @Test
    void cancelPendingReservation() {
        Reservation reservation = new Reservation();
        reservation.setState(ReservationState.Expired);
        reservation.setCourtID(223L);
        when(reservationRepository.findById(11L)).thenReturn(Optional.of(reservation));
        when(scheduleRepository.findById(223L)).thenReturn(Optional.of(new CourtSchedule()));
        String res = bookingAgent.cancelPendingReservation(11L);
        assertEquals(res, "Reservation is not pending" );
    }

    @Test
    void setPending() throws JSONException {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("courtId", 11L);
        hm.put("courtOwnerId", 1L);
        hm.put("startDate", "2022//12//16");
        hm.put("endDate", "2022//12//16");
        hm.put("startHour", 2);
        hm.put("finishHour", 4);
        Court court = new Court();
        CourtSchedule schedule = new CourtSchedule();
        schedule.setPendingReservations(new ArrayList<Reservation>());
        CourtOwner courtOwner = new CourtOwner();
        court.setCourtOwner(courtOwner);
        court.setCourtSchedule(schedule);
        String information = new Gson().toJson(hm);
        when(courtOwnerRepository.findById(1L)).thenReturn(Optional.of(courtOwner));
        when(courtRepository.findById(11L)).thenReturn(Optional.of(court));
        when(reservationService.calcTotalCost(new Date(22, 12, 16),
                new Date(22, 12, 16), new Time(2, 0,0),
                new Time(4, 0,0), court)).thenReturn(1000);
        when(scheduleRepository.save(new CourtSchedule())).thenReturn(new CourtSchedule());
        when(courtRepository.save(court)).thenReturn(new Court());
        String res = bookingAgent.setPending(information);
        assertEquals(res, "Success");
    }
}