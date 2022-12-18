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
        Player mainPlayer;
        Long courtID;
        Long courtOwnerID;
        Date startDate ;
        Date endDate;
        Time timeFrom;
        Time timeTo;
        ReservationState state;
        int moneyPayed ;
        int totalCost ;
        Set<Player> players;

        public FrontEndReservation(Reservation reservation){
            this.id = reservation.getId();
            this.playerID = reservation.getMainPlayer().getId();
            this.playerName = reservation.getMainPlayer().getName();
            this.mainPlayer = reservation.getMainPlayer();
            this.courtID = reservation.getCourtID();
            this.courtOwnerID = reservation.getCourtOwnerID();
            this.startDate = reservation.getStartDate();
            this.endDate = reservation.getEndDate();
            this.timeFrom = reservation.getTimeFrom();
            this.timeTo = reservation.getTimeTo();
            this.state = reservation.getState();
            this.moneyPayed = reservation.getMoneyPayed();
            this.totalCost = reservation.getTotalCost();
            this.players = reservation.getPlayers();
        }
    }

    public String book(String information)  throws JSONException
    {
        JSONObject jsonObject = new JSONObject(information);
        Long reservationId = jsonObject.getLong("reservationId");
        Integer moneyPaid = jsonObject.getInt("moneyPaid");
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if (reservationOptional.isEmpty())
            return "Not found";
        Reservation reservation = reservationOptional.get();
        if (moneyPaid <= 0 || moneyPaid > reservation.getTotalCost())
            return "invalid amount of money";
        reservation.setMoneyPayed(moneyPaid);
        reservation.setState(ReservationState.Booked);
        Optional<CourtSchedule> optionalCourtSchedule = scheduleRepository.findById(reservation.getCourtID());
        CourtSchedule courtSchedule = optionalCourtSchedule.get();
        courtSchedule.getPendingReservations().remove(reservation);
        courtSchedule.getBookedReservations().add(reservation);
        reservationRepository.save(reservation);
        scheduleRepository.save(courtSchedule);
        return "Success";
    }

    public String cancelBookedReservation(String information) throws JSONException {
        JSONObject jsonObject = new JSONObject(information);
        Long id = jsonObject.getLong("id");
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if (reservationOptional.isEmpty())
            return "Reservation not found";
        Reservation reservation = reservationOptional.get();
        Optional<CourtSchedule> optionalCourtSchedule = scheduleRepository.findById(reservation.getCourtID());
        if (optionalCourtSchedule.isEmpty())
            return "Court not found";
        if (!reservation.getState().equals(ReservationState.Booked)) {
            return "Reservation not booked";
        }
        CourtSchedule courtSchedule = optionalCourtSchedule.get();
        courtSchedule.getBookedReservations().remove(reservation);
        scheduleRepository.save(courtSchedule);
        int cost = reservation.getMoneyPayed();
        System.out.println("ID DELETED|  " + id.toString());
        reservationRepository.deleteById(id);
        return Integer.toString(cost);
    }

    public String cancelPendingReservation(String information) throws JSONException {
        JSONObject jsonObject = new JSONObject(information);
        Long id = jsonObject.getLong("id");
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if (reservationOptional.isEmpty())
            return "Reservation not found";
        Reservation reservation = reservationOptional.get();
        Optional<CourtSchedule> optionalCourtSchedule = scheduleRepository.findById(reservation.getCourtID());
        if (optionalCourtSchedule.isEmpty())
            return "Court not found";
        if (!reservation.getState().equals(ReservationState.Pending)) {
            return "Reservation is not pending";
        }
        CourtSchedule courtSchedule = optionalCourtSchedule.get() ;
        courtSchedule.getPendingReservations().remove(reservation) ;
        scheduleRepository.save(courtSchedule);
        reservationRepository.deleteById(id);
        return "Success";
    }

    public String setPending(String information) throws JSONException {
        JSONObject jsonObject = new JSONObject(information);
        String playerName = jsonObject.getString("playerName");
        Long courtId = jsonObject.getLong("courtId");
        Long courtOwnerId = jsonObject.getLong("courtOwnerId");
        String dateStrS = jsonObject.getString("startDate");
        String dateStrF = jsonObject.getString("endDate");
        String[] tempArrS = dateStrS.split("/");
        String[] tempArrF = dateStrF.split("/");
        int startHour = jsonObject.getInt("startHour");
        int finishHour = jsonObject.getInt("finishHour");
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
            return "In valid date2";
        }
        try {
            timeFrom = new Time(startHour, 0, 0);
            timeTo = new Time(finishHour, 0, 0);
        } catch (Exception e) {
            return "In valid Time";
        }

        Optional<Court> courtOptional = courtRepository.findById(courtId);
        Optional<CourtOwner> courtOwnerOptional = courtOwnerRepository.findById(courtOwnerId);
        if (courtOptional.isEmpty() || courtOwnerOptional.isEmpty())
            return "Court Not found";
        Court court = courtOptional.get();
        if (!court.getCourtOwner().equals(courtOwnerOptional.get())) {
            return "Court does not belong to the courtOwner";
        }
        //check
        CourtSchedule courtSchedule = court.getCourtSchedule();
        ScheduleAgent scheduleAgent = new ScheduleAgent(scheduleRepository, reservationRepository);
        DateTime startWorking = new DateTime(stDate, courtSchedule.getStartWorkingHours()) ;
        DateTime endWorking ;
        DateTime startDateTime = new DateTime(stDate,timeFrom) ;
        DateTime endDateTime = new DateTime(endDate, timeTo) ;
        if(courtSchedule.getStartWorkingHours().after(courtSchedule.getEndWorkingHours())){
            Date temp = new Date(stDate.getTime() + (1000 * 60 * 60 * 24)) ;
            endWorking = new DateTime(temp,courtSchedule.getEndWorkingHours()) ;
        }else{
            endWorking = new DateTime(stDate,courtSchedule.getEndWorkingHours()) ;
        }

        if (startDateTime.compareTo(startWorking)< 0 || endDateTime.compareTo(endWorking)>0)
            return "In that time the court is closed";


        List<Reservation> oldReservation = scheduleAgent.getScheduleOverlapped(stDate, endDate, timeFrom, timeTo, courtSchedule);
        if (!oldReservation.isEmpty())
            return "that time have reservation";


        Player player ;
        try {
            Long playerID = jsonObject.getLong("playerId");
            Optional<Player> optionalPlayer= playerRepository.findById(playerID) ;
            if(optionalPlayer.isPresent()){
                player = optionalPlayer.get();
                if(!player.getName().equals(playerName)){
                    return "Player name do not match the player id" ;
                }
            }else{
                throw new JSONException("Player ID") ;
            }

        }catch (Exception e){
            player = new Player() ;
            player.setPlayerType(PlayerType.Lite);
            player.setName(playerName);
        }

        this.playerRepository.save(player) ;

        Reservation reservation = new Reservation(player,courtId, courtOwnerId, stDate, endDate, timeFrom,
                timeTo, ReservationState.Pending,0,
                reservationService.calcTotalCost(stDate, endDate, timeFrom, timeTo, courtOptional.get()));
        reservationRepository.save(reservation);
        courtSchedule.getPendingReservations().add(reservation);
        scheduleRepository.save(courtSchedule);
        courtRepository.save(court);
        return "Success";
    }

    public static class ReservationComparitor implements Comparator<Reservation>{

        @Override
        public int compare(Reservation o1, Reservation o2) {
            DateTime stR1 = new DateTime(o1.getStartDate(), o1.getTimeFrom()) ;
            DateTime stR2 = new DateTime(o2.getStartDate(), o2.getTimeFrom()) ;
            return stR1.compareTo(stR2) ;

        }
    }

    public String getReservations(String information) throws JSONException {
        JSONObject jsonObject = new JSONObject(information);
        Long courtId = jsonObject.getLong("courtId");
        Long courtOwnerId = jsonObject.getLong("courtOwnerId");
        String strDate = jsonObject.getString("date");
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
        List<Reservation> reservations = scheduleAgent.getScheduleOverlapped(date, date, new Time(0) , new Time(23,59,0), court.getCourtSchedule());
        reservations.sort(new ReservationComparitor()) ;

        List<FrontEndReservation> frontEndReservations = new ArrayList<>(reservations.size());
        for(Reservation r: reservations){
            frontEndReservations.add(new FrontEndReservation(r)) ;
        }
        return "S "+  new  Gson().toJson(frontEndReservations);

    }


}
