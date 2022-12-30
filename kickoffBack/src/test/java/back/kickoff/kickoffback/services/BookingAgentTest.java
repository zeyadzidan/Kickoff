package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.BookCommand;
import back.kickoff.kickoffback.Commands.SetPendingCommand;
import back.kickoff.kickoffback.model.*;
import back.kickoff.kickoffback.repositories.*;
import com.google.gson.Gson;
import org.aspectj.lang.annotation.Before;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    PlayerRepository playerRepository ;

    ScheduleAgent scheduleAgent ;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        bookingAgent = new BookingAgent(courtRepository, scheduleRepository, courtOwnerRepository, reservationRepository,
                reservationService, playerRepository);
        scheduleAgent = new ScheduleAgent(scheduleRepository, reservationRepository) ;



    }

    BookCommand getBookCommand() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("reservationId", 1) ;
        jsonObject.put("moneyPaid", 100) ;
        return new BookCommand(jsonObject.toString()) ;
    }
    Reservation createPending(){
        Player mainPlayer = new Player();
        Long courtID = 1L;
        Long courtOwnerID = 1L;
        Date date = Date.valueOf(LocalDate.of(2023,2,3));
        Time timeFrom = Time.valueOf(LocalTime.of(14,0,0));
        Time timeTo = Time.valueOf(LocalTime.of(16,0,0));
        ReservationState state = ReservationState.Pending;
        int moneyPayed = 0;
        int totalCost = 300;

        Reservation reservation = new Reservation(mainPlayer,courtID, courtOwnerID, date, date, timeFrom,
                timeTo, state, moneyPayed, totalCost);
        reservation.setId(1L) ;
        return reservation ;
    }

    Reservation createBooked(){
        Reservation reservation = createPending();
        reservation.setMoneyPayed(150);
        reservation.setState(ReservationState.Booked);
        return reservation ;
    }
    Court createCourt(){
        Court court = new Court() ;
        court.setId(1L) ;
        //create court schedule
        CourtSchedule courtSchedule = new CourtSchedule() ;
        courtSchedule.setBookedReservations(new ArrayList<>());
        courtSchedule.setPendingReservations(new ArrayList<>());
        courtSchedule.setHistory(new ArrayList<>());
        courtSchedule.setId(1L);
        courtSchedule.setStartWorkingHours(Time.valueOf(LocalTime.of(9,0,0)));
        courtSchedule.setEndWorkingHours(Time.valueOf(LocalTime.of(23,0,0)));
        courtSchedule.setEndMorning(Time.valueOf(LocalTime.of(16,0,0)));
        courtSchedule.setMorningCost(150);
        courtSchedule.setNightCost(400);

        court.setCourtSchedule(courtSchedule);
        court.setState(CourtState.Active);

        return court;
    }



    @Test
    void bookTest() throws Exception {
        BookCommand command = getBookCommand();
        Reservation reservation = createPending();
        Court court = createCourt();
        court.getCourtSchedule().getPendingReservations().add(reservation) ;

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        when(courtRepository.findById(1L)).thenReturn(Optional.of(court));
        when(scheduleRepository.save(court.getCourtSchedule())).thenReturn(court.courtSchedule);

        bookingAgent.book(command);
        assertEquals(reservation.getState(), ReservationState.Booked);
        assertEquals(court.getCourtSchedule().getBookedReservations().get(0), reservation);
    }

    @Test
    void cancelBookedReservationTest() throws Exception {
        String information = new JSONObject().put("id", 1L).toString() ;
        Reservation reservation = createBooked();
        Court court = createCourt();
        court.getCourtSchedule().getBookedReservations().add(reservation) ;

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        when(courtRepository.findById(1L)).thenReturn(Optional.of(court));
        when(scheduleRepository.save(court.getCourtSchedule())).thenReturn(court.courtSchedule);


        String res;
        try {
            res= bookingAgent.cancelBookedReservation(information);
        }catch (Exception e){
            res = e.getMessage() ;
        }
        assertEquals(res, "150");
    }
    @Test
    void cancelBookedReservationWithErrorTest() throws Exception {
        String information = new JSONObject().put("id", 1L).toString() ;
        Reservation reservation = createPending();
        Court court = createCourt();
        court.getCourtSchedule().getPendingReservations().add(reservation) ;

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        when(courtRepository.findById(1L)).thenReturn(Optional.of(court));
        when(scheduleRepository.save(court.getCourtSchedule())).thenReturn(court.courtSchedule);


        String res;
        try {
            res= bookingAgent.cancelBookedReservation(information);
        }catch (Exception e){
            res = e.getMessage() ;
        }
        assertEquals(res, "Reservation not booked");
    }



    @Test
    void cancelPendingReservationTest() throws JSONException {
        String information = new JSONObject().put("id", 1L).toString() ;
        Reservation reservation = createPending();
        Court court = createCourt();
        court.getCourtSchedule().getPendingReservations().add(reservation) ;

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        when(courtRepository.findById(1L)).thenReturn(Optional.of(court));
        when(scheduleRepository.save(court.getCourtSchedule())).thenReturn(court.courtSchedule);


        String res = "";
        try {
            bookingAgent.cancelPendingReservation(information);
        }catch (Exception e){
            res = e.getMessage() ;
        }
        assertEquals(res, "");
        assertEquals(reservation.getState(), ReservationState.Expired);
        assertEquals(court.getCourtSchedule().getHistory().get(0), reservation);
    }

    Player createPlayer(PlayerType type){
        Player player = new Player() ;
        player.setPlayerType(type);
        player.setName("Gad");
        player.setReservations(new ArrayList<>());
        return player;
    }


    SetPendingCommand createSetPendingComandWithPlayerId() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("courtId",1L) ;
        jsonObject.put("courtOwnerId",1L) ;
        jsonObject.put("startDate","2/3/2023") ;
        jsonObject.put("endDate","2/3/2023") ;
        jsonObject.put("startHour",14) ;
        jsonObject.put("finishHour",16) ;
        jsonObject.put("playerId",1L) ;
        //jsonObject.put("playerName",1L) ;
        //jsonObject.put("phoneNumber",1L) ;
        Player player = createPlayer(PlayerType.Registered) ;
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(playerRepository.save(player)).thenReturn(player);

        return new SetPendingCommand(jsonObject.toString(), playerRepository);
    }
    CourtOwner createCourtOwner(Court court){
        CourtOwner courtOwner = new CourtOwner();
        courtOwner.setId(1L);
        courtOwner.setCourts(new ArrayList<Court>());
        courtOwner.getCourts().add(court) ;
        court.setCourtOwner(courtOwner);
        return courtOwner ;
    }

    @Test
    void setPendingTest() throws Exception {
        SetPendingCommand command = createSetPendingComandWithPlayerId();
        Court court = createCourt();
        CourtOwner courtOwner = createCourtOwner(court);

        when(courtOwnerRepository.findById(1L)).thenReturn(Optional.of(courtOwner));
        when(courtRepository.findById(1L)).thenReturn(Optional.of(court));
        when(scheduleRepository.save(court.getCourtSchedule())).thenReturn(court.getCourtSchedule());
        when(courtRepository.save(court)).thenReturn(court);
        when(playerRepository.save(command.getPlayer())).thenReturn(command.getPlayer());

        String res = "";
        try {
            bookingAgent.setPending(command);
        }catch (Exception e){
            res = e.getMessage();
        }

        assertEquals(court.getCourtSchedule().getPendingReservations().size(), 1);
        assertEquals(res, "");
    }


}