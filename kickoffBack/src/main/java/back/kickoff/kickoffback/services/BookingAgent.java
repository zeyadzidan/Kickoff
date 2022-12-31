package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.Add.SetPendingCommand;
import back.kickoff.kickoffback.Commands.FrontEnd.FrontEndReservation;
import back.kickoff.kickoffback.Commands.Operation.BookCommand;
import back.kickoff.kickoffback.Commands.Operation.GetPlayerReservationCommand;
import back.kickoff.kickoffback.Commands.Operation.GetReservationCommand;
import back.kickoff.kickoffback.Commands.Operation.ReceiptCommand;
import back.kickoff.kickoffback.model.*;
import back.kickoff.kickoffback.repositories.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public boolean book(BookCommand command)  throws Exception {
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
        return true;
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

    public boolean cancelPendingReservation(String information) throws Exception {
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
        return true ;
    }


    public boolean setPending(SetPendingCommand command) throws Exception {
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

        LocalDate stDate = command.getStDate().toLocalDate() ;
        LocalDate endDate = command.getEndDate().toLocalDate();
        LocalTime timeFrom = command.getTimeFrom().toLocalTime() ;
        LocalTime timeTo = command.getTimeTo().toLocalTime() ;

        DateTime startWorking = new DateTime(stDate, courtSchedule.getStartWorkingHours().toLocalTime()) ;
        DateTime endWorking ;
        if(courtSchedule.getStartWorkingHours().after(courtSchedule.getEndWorkingHours())){
            LocalDate temp = stDate ;
            temp = temp.plusDays(1L) ;
            endWorking = new DateTime(temp,courtSchedule.getEndWorkingHours().toLocalTime()) ;
        }else{
            endWorking = new DateTime(stDate,courtSchedule.getEndWorkingHours().toLocalTime()) ;
        }
        DateTime from = new DateTime(stDate, timeFrom);
        DateTime to = new DateTime(endDate, timeTo);

        LocalDateTime now = LocalDateTime.now() ;
        System.out.println("now " + now.toString());
        System.out.println("from " + from.toString());
        System.out.println("to " + to.toString());
        System.out.println("compare " + from.dateTime.compareTo(now) + " " + to.dateTime.compareTo(now) + " " + to.compareTo(from));

        //checks
        if(from.dateTime.compareTo(now)<=0 || to.dateTime.compareTo(now)<=0 ){
            throw new Exception("Invalid date3 date in the past") ;
        }

        int c = to.compareTo(from);
        if (c <= 0) {
            throw new Exception( "invalid start and end times");
        }
        if (c < courtSchedule.getMinBookingHours()) {
            throw new Exception("must be more than or equal the minimum booking hours");
        }

        if (from.compareTo(startWorking) < 0 || to.compareTo(endWorking) > 0)
            throw new Exception("In that time the court is closed");

        List<Reservation> oldReservation = scheduleAgent.getScheduleOverlapped(stDate, endDate, timeFrom, timeTo,
                courtSchedule, "");
        if (!oldReservation.isEmpty())
            throw new Exception("that time have reservation") ;

        Reservation reservation = new Reservation(command.getPlayer(), command.getCourtId(), command.getCourtOwnerId(), command.getStDate(),
                command.getEndDate(), command.getTimeFrom(), command.getTimeTo(), ReservationState.Pending, 0,
                reservationService.calcTotalCost(stDate, endDate, timeFrom, timeTo, courtOptional.get()));
        command.getPlayer().getReservations().add(reservation);

        reservationRepository.save(reservation);
        playerRepository.save(command.getPlayer());
        courtSchedule.getPendingReservations().add(reservation);
        scheduleRepository.save(courtSchedule);
        courtRepository.save(court);
        return true ;
    }

    public static class ReservationComparator implements Comparator<Reservation> {
        boolean ascending;

        ReservationComparator(boolean ascending) {
            this.ascending = ascending;
        }

        @Override
        public int compare(Reservation o1, Reservation o2) {
            LocalDateTime stR1 = LocalDateTime.of(o1.getStartDate().toLocalDate(), o1.getTimeFrom().toLocalTime());
            LocalDateTime stR2 = LocalDateTime.of(o2.getStartDate().toLocalDate(), o2.getTimeFrom().toLocalTime());
            return (ascending) ? stR1.compareTo(stR2) : stR2.compareTo(stR1);
        }
    }

    public List<FrontEndReservation> getPlayerReservations(GetPlayerReservationCommand command) {

        System.out.println(command.pid);
        System.out.println(command.filter);
        System.out.println(command.ascending);
        Optional<Player> playerOptional = playerRepository.findById(command.pid) ;
        if (playerOptional.isEmpty()){
            return new ArrayList<>();
        }
        Player player = playerOptional.get() ;
        List<Reservation> reservations = player.getReservations() ;
        reservations.sort(new ReservationComparator(command.ascending));
        //System.out.println(reservations.get(0).getState().toString());
        ArrayList<FrontEndReservation> frontEndReservations = new ArrayList<>() ;
        for (Reservation r : reservations) {
            if (r.getState().toString().equals(command.filter))
                frontEndReservations.add(new FrontEndReservation(r));
        }
        return frontEndReservations ;
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

        LocalTime swh = court.getCourtSchedule().getStartWorkingHours().toLocalTime() ;
        LocalTime ewh = court.getCourtSchedule().getEndWorkingHours().toLocalTime() ;
        LocalDate stDate = command.getDate().toLocalDate() ;
        LocalDate endDate = command.getDate().toLocalDate();
        if (ewh.isBefore(swh)) {
            endDate = endDate.plusDays(1L);
        }

        System.out.println(swh.toString());
        System.out.println(ewh.toString());
        System.out.println(stDate.toString());
        System.out.println(endDate.toString());

        System.out.println(command.getFilter());

        List<Reservation> reservations;
        System.out.println("aaaaaaaaaaaaaaaaa");

        if(command.getFilter().equals("")){
            System.out.println("1");

            reservations = scheduleAgent.getScheduleOverlapped(stDate, endDate, swh, ewh, court.getCourtSchedule(), "");

        }else if(!command.getFilter().equals("Expired")){
            System.out.println("2");
            reservations = scheduleAgent.getScheduleOverlapped(stDate, endDate, swh, ewh, court.getCourtSchedule(), command.getFilter());
        }else {
            System.out.println("3");
            reservations = scheduleAgent.getExpiredOverlapped(stDate, endDate, swh, ewh, court.getCourtSchedule());
        }
        System.out.println(reservations.toString());

        reservations.sort(new ReservationComparator(command.isAscending()));

        List<FrontEndReservation> frontEndReservations = new ArrayList<>(reservations.size());
        for (Reservation r : reservations) {
            frontEndReservations.add(new FrontEndReservation(r));
        }
        return frontEndReservations;
    }

    public void sendReceipt(ReceiptCommand command) throws Exception {
        Optional<Reservation> optionalReservation = reservationRepository.findById(command.reservationId);
        if (optionalReservation.isEmpty())
            throw new Exception("Not Found");
        optionalReservation.get().setReceiptUrl(command.receiptUrl);
        optionalReservation.get().setState(ReservationState.Awaiting);
        Long courtID = optionalReservation.get().getCourtID();
        Court court = courtRepository.findById(courtID).get();
        CourtSchedule courtSchedule = court.getCourtSchedule();
        courtSchedule.getPendingReservations().remove(optionalReservation.get());
        courtSchedule.getBookedReservations().add(optionalReservation.get());
        reservationRepository.save(optionalReservation.get());
        scheduleRepository.save(courtSchedule);
    }

}
