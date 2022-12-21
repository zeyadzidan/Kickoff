package back.kickoff.kickoffback.services;

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
import java.text.SimpleDateFormat;
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

    static class FrontEndReservation{

        Long id;
        Long playerID;
        String playerName;
        //Player mainPlayer;
        Long courtID;
        Long courtOwnerID;
        Date startDate ;
        Date endDate;
        Time timeFrom;
        Time timeTo;
        ReservationState state;
        int moneyPayed ;
        int totalCost ;
        String receiptUrl;
        //Set<Player> players;

        public FrontEndReservation(Reservation reservation){
            this.id = reservation.getId();
            this.playerID = reservation.getMainPlayer().getId();
            this.playerName = reservation.getMainPlayer().getName();
            //this.mainPlayer = reservation.getMainPlayer();
            this.courtID = reservation.getCourtID();
            this.courtOwnerID = reservation.getCourtOwnerID();
            this.startDate = reservation.getStartDate();
            this.endDate = reservation.getEndDate();
            this.timeFrom = reservation.getTimeFrom();
            this.timeTo = reservation.getTimeTo();
            this.state = reservation.getState();
            this.moneyPayed = reservation.getMoneyPayed();
            this.totalCost = reservation.getTotalCost();
            this.receiptUrl = reservation.getReceiptUrl();
            //this.players = reservation.getPlayers();
        }
    }

    /**
     * checked till 21/12/2022 by gad
     * @param information JSON
     * {
     *    reservationId: long,
     *    moneyPaid: int
     * }
     * @return response messege
     */
    public String book(String information)  throws JSONException
    {
        JSONObject jsonObject = new JSONObject(information);
        long reservationId ;
        int moneyPaid ;
        try{
            reservationId = jsonObject.getLong("reservationId");
            moneyPaid = jsonObject.getInt("moneyPaid");
        }catch (Exception e){
            return "bad request" ;
        }

        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if (reservationOptional.isEmpty())
            return "Not found";
        Reservation reservation = reservationOptional.get();
        if (moneyPaid <= 0 || moneyPaid > reservation.getTotalCost())
            return "invalid amount of money";
        reservation.setMoneyPayed(moneyPaid);
        reservation.setState(ReservationState.Booked);
        Optional<Court> optionalCourt = courtRepository.findById(reservation.getCourtID());
        CourtSchedule courtSchedule = optionalCourt.get().getCourtSchedule();
        courtSchedule.getPendingReservations().remove(reservation);
        courtSchedule.getBookedReservations().add(reservation);
        reservationRepository.save(reservation);
        scheduleRepository.save(courtSchedule);
        return "Success";
    }

    // checked
    public String cancelBookedReservation(String information) throws JSONException {
        JSONObject jsonObject = new JSONObject(information);
        Long id;

        try{
            id = jsonObject.getLong("id");
        }catch (Exception e){
            return "bad request" ;
        }

        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if (reservationOptional.isEmpty())
            return "Reservation not found";
        Reservation reservation = reservationOptional.get();
        Optional<Court> optionalCourt = courtRepository.findById(reservation.getCourtID());
        if (!reservation.getState().equals(ReservationState.Booked)) {
            return "Reservation not booked";
        }
        CourtSchedule courtSchedule = optionalCourt.get().getCourtSchedule();
        courtSchedule.getBookedReservations().remove(reservation);
        scheduleRepository.save(courtSchedule);
        int cost = reservation.getMoneyPayed();
        System.out.println("ID DELETED|  " + id.toString());
        reservationRepository.deleteById(id);
        return Integer.toString(cost);
    }

    public String cancelPendingReservation(String information) throws JSONException {
        JSONObject jsonObject = new JSONObject(information);
        Long id ;
        try{
            id = jsonObject.getLong("id");
        }catch (Exception e){
            return "bad request" ;
        }

        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if (reservationOptional.isEmpty())
            return "Reservation not found";
        Reservation reservation = reservationOptional.get();
        Optional<Court> optionalCourt = courtRepository.findById(reservation.getCourtID());

        if (!reservation.getState().equals(ReservationState.Pending)) {
            return "Reservation is not pending";
        }
        CourtSchedule courtSchedule = optionalCourt.get().getCourtSchedule() ;
        courtSchedule.getPendingReservations().remove(reservation) ;
        scheduleRepository.save(courtSchedule);
        reservationRepository.deleteById(id);
        return "Success";
    }

    //check
    public String setPending(String information) throws JSONException {
        JSONObject jsonObject = new JSONObject(information);
        Long courtId;
        Long courtOwnerId ;
        String dateStrS ;
        String dateStrF ;
        int startHour ;
        int finishHour;
        long playerID ;
        Player player ;

        try{
            courtId = jsonObject.getLong("courtId");
            courtOwnerId = jsonObject.getLong("courtOwnerId");
            dateStrS = jsonObject.getString("startDate");
            dateStrF = jsonObject.getString("endDate");
            startHour = jsonObject.getInt("startHour");
            finishHour = jsonObject.getInt("finishHour");
        }catch (Exception e){
            return "bad request" ;
        }

        try {
            playerID = jsonObject.getLong("playerId");
            Optional<Player> optionalPlayer= playerRepository.findById(playerID) ;
            if(optionalPlayer.isPresent()){
                player = optionalPlayer.get();
            }else{
                throw new JSONException("Player ID") ;
            }
        }catch (Exception e){
            player = new Player() ;
            player.setPlayerType(PlayerType.Lite);
            String playerName ;
            try{
                playerName = jsonObject.getString("playerName");
            }catch (Exception e2){
                return "bad request" ;
            }
            player.setName(playerName);
            player.setReservations(new HashSet<>());
            this.playerRepository.save(player) ;

        }
        String[] tempArrS = dateStrS.split("/");
        String[] tempArrF = dateStrF.split("/");
        Date stDate, endDate;
        Time timeFrom, timeTo;
        if (tempArrS.length != 3 || tempArrF.length != 3)
            return "In valid date1";
        try
        {
            SimpleDateFormat obj = new SimpleDateFormat("MM/dd/yyyy");
            long date1 = obj.parse(dateStrS).getTime();
            long date2 = obj.parse(dateStrF).getTime();
            stDate = new Date(date1);
            endDate = new Date(date2);
        } catch (Exception e) {
            return "Invalid date2";
        }
        try {
            timeFrom = new Time(startHour, 0, 0);
            timeTo = new Time(finishHour, 0, 0);
        } catch (Exception e) {
            return "Invalid Time";
        }



        Optional<Court> courtOptional = courtRepository.findById(courtId);
        Optional<CourtOwner> courtOwnerOptional = courtOwnerRepository.findById(courtOwnerId);
        if (courtOptional.isEmpty() || courtOwnerOptional.isEmpty())
            return "Court Not found";
        Court court = courtOptional.get();
        if (!court.getCourtOwner().equals(courtOwnerOptional.get())) {
            return "Court does not belong to the courtOwner";
        }
        CourtSchedule courtSchedule = court.getCourtSchedule();
        ScheduleAgent scheduleAgent = new ScheduleAgent(scheduleRepository, reservationRepository);
        DateTime startWorking = new DateTime(stDate, courtSchedule.getStartWorkingHours()) ;
        DateTime endWorking ;
        if(courtSchedule.getStartWorkingHours().after(courtSchedule.getEndWorkingHours())){
            Date temp = new Date(stDate.getTime() + (1000 * 60 * 60 * 24)) ;
            endWorking = new DateTime(temp,courtSchedule.getEndWorkingHours()) ;
        }else{
            endWorking = new DateTime(stDate,courtSchedule.getEndWorkingHours()) ;
        }
        DateTime from = new DateTime(stDate,timeFrom) ;
        DateTime to = new DateTime(endDate, timeTo) ;

        DateTime now = new DateTime(new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()));
        System.out.println("now " + now.toString());
        System.out.println("from " + from.toString());
        System.out.println("to " + to.toString());
        System.out.println("compare " +from.compareTo(now)+ " " + to.compareTo(now) + " " + to.compareTo(from));
        //if(from.compareTo(now)<=0 || to.compareTo(now)<=0 ){
        //    return "Invalid date3 date in the past" ;
        //}
        //checks

        int c = to.compareTo(from) ;
        if(c<=0){
            return "invalid start and end times" ;
        }
        if(c < courtSchedule.getMinBookingHours()){
            return "must be more than or equal the minimum booking hours" ;
        }

        if (from.compareTo(startWorking)< 0 || to.compareTo(endWorking)>0)
            return "In that time the court is closed";


        List<Reservation> oldReservation = scheduleAgent.getScheduleOverlapped(stDate, endDate, timeFrom, timeTo, courtSchedule);
        if (!oldReservation.isEmpty())
            return "that time have reservation";

        Reservation reservation = new Reservation(player,courtId, courtOwnerId, stDate, endDate, timeFrom,
                timeTo, ReservationState.Pending,0,
                reservationService.calcTotalCost(stDate, endDate, timeFrom, timeTo, courtOptional.get()));

        player.getReservations().add(reservation) ;
        reservationRepository.save(reservation);
        playerRepository.save(player) ;
        courtSchedule.getPendingReservations().add(reservation);
        scheduleRepository.save(courtSchedule);
        courtRepository.save(court);
        System.out.println("Success set pending");
        return "Success";
    }

    public static class ReservationComparator implements Comparator<Reservation>{
        boolean ascending;
        ReservationComparator(boolean ascending) {
            this.ascending = ascending;
        }
        @Override
        public int compare(Reservation o1, Reservation o2) {
            DateTime stR1 = new DateTime(o1.getStartDate(), o1.getTimeFrom()) ;
            DateTime stR2 = new DateTime(o2.getStartDate(), o2.getTimeFrom()) ;
            return (ascending) ? stR1.compareTo(stR2) : stR2.compareTo(stR1) ;
        }
    }

    public String getReservations(String information) throws JSONException {
        JSONObject jsonObject = new JSONObject(information);
        long courtId ;
        long courtOwnerId;
        String strDate;
        try{
            courtId = jsonObject.getLong("courtId");
            courtOwnerId = jsonObject.getLong("courtOwnerId");
            strDate = jsonObject.getString("date");
        }catch (Exception e){
            return "bad request" ;
        }
        String[] tempArrS = strDate.split("/");
        if (tempArrS.length != 3)
            return "In valid date";

        Date date ;
        try
        {
            SimpleDateFormat obj = new SimpleDateFormat("MM/dd/yyyy");
            long date1 = obj.parse(strDate).getTime();
            date = new Date(date1);
        } catch (Exception e) {
            return "In valid date";
        }
        Optional<Court> courtOptional = courtRepository.findById(courtId);
        Optional<CourtOwner> courtOwnerOptional = courtOwnerRepository.findById(courtOwnerId);
        if (courtOptional.isEmpty() || courtOwnerOptional.isEmpty())
            return "Court Not found";
        Court court = courtOptional.get();
        if (!court.getCourtOwner().equals(courtOwnerOptional.get())) {
            return "Court does not belong to the courtOwner";
        }
        ScheduleAgent scheduleAgent = new ScheduleAgent(scheduleRepository, reservationRepository) ;
        Date endDate = date ;
        if(court.getCourtSchedule().getEndWorkingHours().before(court.getCourtSchedule().getStartWorkingHours())){
            endDate = new Date(date.getTime() + (1000 * 60 * 60 * 24)) ;
        }

        List<Reservation> reservations = scheduleAgent.getScheduleOverlapped(date, endDate, court.getCourtSchedule().getStartWorkingHours() , court.getCourtSchedule().getEndWorkingHours(), court.getCourtSchedule());
        reservations.addAll(scheduleAgent.getExpiredOverlapped(date, endDate, court.getCourtSchedule().getStartWorkingHours() , court.getCourtSchedule().getEndWorkingHours(), court.getCourtSchedule()));
        reservations.sort(new ReservationComparator(true)) ;
        List<FrontEndReservation> frontEndReservations = new ArrayList<>(reservations.size());
        for(Reservation r: reservations){
            frontEndReservations.add(new FrontEndReservation(r)) ;
        }
        return "S "+  new  Gson().toJson(frontEndReservations);
    }
    public String sendReceipt(String information) throws JSONException {
        JSONObject jsonObject = new JSONObject(information);
        Long reservationId = jsonObject.getLong("reservationId");
        String imageUrl = jsonObject.getString("receiptUrl");
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        if(optionalReservation.isEmpty())
            return "Not Found";
        optionalReservation.get().setReceiptUrl(imageUrl);
        reservationRepository.save(optionalReservation.get());
        return "Receipt Sent";
    }

}
