package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.BookCommand;
import back.kickoff.kickoffback.Commands.FrontEndReservation;
import back.kickoff.kickoffback.Commands.GetReservationCommand;
import back.kickoff.kickoffback.Commands.SetPendingCommand;
import back.kickoff.kickoffback.model.*;
import back.kickoff.kickoffback.repositories.*;
import com.google.gson.Gson;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class BookingAgent {
    private final CourtRepository courtRepository;
    private final ScheduleRepository scheduleRepository;
    private final CourtOwnerRepository courtOwnerRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;
    private final PlayerRepository playerRepository;

    public BookingAgent(CourtRepository courtRepository, ScheduleRepository scheduleRepository, CourtOwnerRepository courtOwnerRepository, ReservationRepository reservationRepository, ReservationService reservationService, back.kickoff.kickoffback.repositories.PlayerRepository playerRepository) {
        this.courtRepository = courtRepository;
        this.scheduleRepository = scheduleRepository;
        this.courtOwnerRepository = courtOwnerRepository;
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
        this.playerRepository = playerRepository;
    }

    public void book(BookCommand command)  throws Exception {
        Optional<Reservation> reservationOptional = reservationRepository.findById(command.getReservationId());
        if (reservationOptional.isEmpty())
            throw new Exception("Not found");
        Reservation reservation = reservationOptional.get();
        if (command.getMoneyPaid() <= 0 || command.getMoneyPaid() > reservation.getTotalCost())
            throw new Exception("invalid amount of money");

        reservation.setMoneyPayed(command.getMoneyPaid());
        reservation.setState(ReservationState.Booked);
        Optional<Court> optionalCourtSchedule = courtRepository.findById(reservation.getCourtID());
        CourtSchedule courtSchedule = optionalCourtSchedule.get().getCourtSchedule();
        courtSchedule.getPendingReservations().remove(reservation);
        courtSchedule.getBookedReservations().add(reservation);
        reservationRepository.save(reservation);
        scheduleRepository.save(courtSchedule);
    }


    public String cancelBookedReservation(String information) throws Exception {
        JSONObject jsonObject = new JSONObject(information);
        Long id = jsonObject.getLong("id");

        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if (reservationOptional.isEmpty())
            throw new Exception("Reservation not found");

        Reservation reservation = reservationOptional.get();
        Optional<Court> optionalCourt = courtRepository.findById(reservation.getCourtID());

        if (optionalCourt.isEmpty())
            throw new Exception( "Court not found");
        if (!reservation.getState().equals(ReservationState.Booked)) {
            throw new Exception("Reservation not booked");
        }
        CourtSchedule courtSchedule = optionalCourt.get().getCourtSchedule();
        courtSchedule.getBookedReservations().remove(reservation);
        courtSchedule.getHistory().add(reservation);
        reservation.setState(ReservationState.Expired);

        scheduleRepository.save(courtSchedule);
        reservationRepository.save(reservation);

        int cost = reservation.getMoneyPayed();
        return Integer.toString(cost);
    }

    public void cancelPendingReservation(String information) throws Exception {
        JSONObject jsonObject = new JSONObject(information);
        long id;
        try {
            id = jsonObject.getLong("id");
        } catch (Exception e) {
            throw new Exception("bad request");
        }

        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if (reservationOptional.isEmpty())
            throw new Exception("Reservation not found") ;
        Reservation reservation = reservationOptional.get();
        Optional<Court> optionalCourt = courtRepository.findById(reservation.getCourtID());
        if (optionalCourt.isEmpty())
            throw new Exception("Court not found") ;

        if (!reservation.getState().equals(ReservationState.Pending)) {
            throw new Exception("Reservation is not pending") ;
        }

        CourtSchedule courtSchedule = optionalCourt.get().getCourtSchedule() ;
        courtSchedule.getPendingReservations().remove(reservation) ;
        courtSchedule.getHistory().add(reservation) ;
        reservation.setState(ReservationState.Expired);
        scheduleRepository.save(courtSchedule);
        reservationRepository.save(reservation);
    }


    public void setPending(SetPendingCommand command) throws Exception {
        Optional<Court> courtOptional = courtRepository.findById(command.getCourtId());
        Optional<CourtOwner> courtOwnerOptional = courtOwnerRepository.findById(command.getCourtOwnerId());
        if (courtOptional.isEmpty() || courtOwnerOptional.isEmpty())
            throw new Exception("Court Not found") ;
        Court court = courtOptional.get();
        if (!court.getCourtOwner().equals(courtOwnerOptional.get())) {
            throw new Exception("Court does not belong to the courtOwner") ;
        }
        //check
        CourtSchedule courtSchedule = court.getCourtSchedule();
        ScheduleAgent scheduleAgent = new ScheduleAgent(scheduleRepository, reservationRepository);
        DateTime startWorking = new DateTime(command.getStDate(), courtSchedule.getStartWorkingHours()) ;
        DateTime endWorking ;
        DateTime startDateTime = new DateTime(command.getStDate(),command.getTimeFrom()) ;
        DateTime endDateTime = new DateTime(command.getEndDate(), command.getTimeTo()) ;
        if(courtSchedule.getStartWorkingHours().after(courtSchedule.getEndWorkingHours())){
            Date temp = new Date(command.getStDate().getTime() + (1000 * 60 * 60 * 24)) ;
            endWorking = new DateTime(temp,courtSchedule.getEndWorkingHours()) ;
        }else{
            endWorking = new DateTime(command.getStDate(),courtSchedule.getEndWorkingHours()) ;
        }
        DateTime from = new DateTime(command.getStDate(), command.getTimeFrom());
        DateTime to = new DateTime(command.getEndDate(), command.getTimeTo());

        DateTime now = new DateTime(new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()));
        System.out.println("now " + now.toString());
        System.out.println("from " + from.toString());
        System.out.println("to " + to.toString());
        System.out.println("compare " + from.compareTo(now) + " " + to.compareTo(now) + " " + to.compareTo(from));

        //if(from.compareTo(now)<=0 || to.compareTo(now)<=0 ){
        //    return "Invalid date3 date in the past" ;
        //}
        //checks

        int c = to.compareTo(from);
        if (c <= 0) {
            throw new Exception( "invalid start and end times");
        }
        if (c < courtSchedule.getMinBookingHours()) {
            throw new Exception("must be more than or equal the minimum booking hours");
        }

        if (from.compareTo(startWorking) < 0 || to.compareTo(endWorking) > 0)
            throw new Exception("In that time the court is closed");

        List<Reservation> oldReservation = scheduleAgent.getScheduleOverlapped(command.getStDate(), command.getEndDate(),
                command.getTimeFrom(), command.getTimeTo(), courtSchedule);
        if (!oldReservation.isEmpty())
            throw new Exception("that time have reservation") ;

        /*
        Reservation reservation = new Reservation(player.getId(), player.getName(), courtId, courtOwnerId, stDate, endDate, timeFrom,
                timeTo, ReservationState.Pending, 0,
                reservationService.calcTotalCost(stDate, endDate, timeFrom, timeTo, courtOptional.get()));

//        player.getReservations().add(reservation);
*/
        Reservation reservation = new Reservation(command.getPlayer(), command.getCourtId(), command.getCourtOwnerId(), command.getStDate(),
                command.getEndDate(), command.getTimeFrom(), command.getTimeTo(), ReservationState.Pending, 0,
                reservationService.calcTotalCost(command.getStDate(), command.getEndDate(), command.getTimeFrom(), command.getTimeTo(), courtOptional.get()));

        reservationRepository.save(reservation);
        playerRepository.save(command.getPlayer());
        courtSchedule.getPendingReservations().add(reservation);
        scheduleRepository.save(courtSchedule);
        courtRepository.save(court);
    }

    public static class ReservationComparator implements Comparator<Reservation> {
        boolean ascending;

        ReservationComparator(boolean ascending) {
            this.ascending = ascending;
        }

        @Override
        public int compare(Reservation o1, Reservation o2) {
            DateTime stR1 = new DateTime(o1.getStartDate(), o1.getTimeFrom());
            DateTime stR2 = new DateTime(o2.getStartDate(), o2.getTimeFrom());
            return (ascending) ? stR1.compareTo(stR2) : stR2.compareTo(stR1);
        }
    }

    public Object getPlayerReservations(String information) throws JSONException {
        JSONObject object = new JSONObject(information);
        Long pid = object.has("pid") ? object.getLong("pid") : -1L;
        String filter = object.has("filter") ? object.getString("filter") : "Booked";
        boolean ascending = !object.has("ascending") || object.getBoolean("ascending");
        System.out.println(pid);
        System.out.println(filter);
        System.out.println(ascending);
        try {
            List<Reservation> reservations = reservationRepository.findAllByPid(pid);
            reservations.sort(new ReservationComparator(ascending));
            List<FrontEndReservation> frontEndReservations = new ArrayList<>();
            System.out.println(reservations.get(0).getState().toString());
            for (Reservation r : reservations) {
                if (r.getState().toString().equals(filter))
                    frontEndReservations.add(new FrontEndReservation(r));
            }
            return new Gson().toJson(frontEndReservations);
        } catch (Exception ignored) {
            return new ArrayList<>();
        }
    }


    public List<FrontEndReservation> getReservations(GetReservationCommand command) throws Exception{


        Optional<Court> courtOptional = courtRepository.findById(command.getCourtId());
        Optional<CourtOwner> courtOwnerOptional = courtOwnerRepository.findById(command.getCourtOwnerId());
        if (courtOptional.isEmpty() || courtOwnerOptional.isEmpty())
            throw new Exception("Court Not found");
        Court court = courtOptional.get();
        if (!court.getCourtOwner().equals(courtOwnerOptional.get())) {
            throw new Exception("Court does not belong to the courtOwner");
        }

        ScheduleAgent scheduleAgent = new ScheduleAgent(scheduleRepository, reservationRepository);
        Date endDate = command.getDate();
        if (court.getCourtSchedule().getEndWorkingHours().before(court.getCourtSchedule().getStartWorkingHours())) {
            endDate = new Date(command.getDate().getTime() + (1000 * 60 * 60 * 24));
        }

        System.out.println(command.getFilter());
        List<Reservation> reservations = scheduleAgent.getScheduleOverlapped(command.getDate(), endDate, court.getCourtSchedule().getStartWorkingHours(), court.getCourtSchedule().getEndWorkingHours(), court.getCourtSchedule());
        reservations.addAll(scheduleAgent.getExpiredOverlapped(command.getDate(), endDate, court.getCourtSchedule().getStartWorkingHours(), court.getCourtSchedule().getEndWorkingHours(), court.getCourtSchedule()));
        reservations.sort(new ReservationComparator(command.isAscending()));

        List<FrontEndReservation> frontEndReservations = new ArrayList<>(reservations.size());
        for (Reservation r : reservations) {
            frontEndReservations.add(new FrontEndReservation(r));
        }

        return frontEndReservations;
    }

    public String sendReceipt(String information) throws JSONException {
        JSONObject jsonObject = new JSONObject(information);
        Long reservationId = jsonObject.getLong("reservationId");
        String imageUrl = jsonObject.getString("receiptUrl");
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        if (optionalReservation.isEmpty())
            return "Not Found";
        optionalReservation.get().setReceiptUrl(imageUrl);
        optionalReservation.get().setState(ReservationState.Awaiting);
        Long courtID = optionalReservation.get().getCourtID();
        Court court = courtRepository.findById(courtID).get();
        CourtSchedule courtSchedule = court.getCourtSchedule();
        courtSchedule.getPendingReservations().remove(optionalReservation.get());
        courtSchedule.getBookedReservations().add(optionalReservation.get());
        reservationRepository.save(optionalReservation.get());
        scheduleRepository.save(courtSchedule);
        return "Receipt Sent";
    }

}
