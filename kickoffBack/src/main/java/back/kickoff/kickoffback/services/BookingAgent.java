package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.*;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.repositories.CourtRepository;
import back.kickoff.kickoffback.repositories.ReservationRepository;
import back.kickoff.kickoffback.repositories.ScheduleRepository;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class BookingAgent {
    private final CourtRepository courtRepository;
    private final ScheduleRepository scheduleRepository;
    private final CourtOwnerRepository courtOwnerRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    public BookingAgent(CourtRepository courtRepository, ScheduleRepository scheduleRepository, CourtOwnerRepository courtOwnerRepository, ReservationRepository reservationRepository, ReservationService reservationService) {
        this.courtRepository = courtRepository;
        this.scheduleRepository = scheduleRepository;
        this.courtOwnerRepository = courtOwnerRepository;
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
    }

    public String book(String information) throws JSONException {
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
        CourtSchedule courtSchedule = optionalCourtSchedule.get();
        courtSchedule.getPendingReservations().remove(reservation);
        reservationRepository.deleteById(id);
        return "Success";
    }

    public String setPending(String information) throws JSONException {
        JSONObject jsonObject = new JSONObject(information);
        Long playerID = 0L;
        String playerName = jsonObject.getString("playerName");
        Long courtId = jsonObject.getLong("courtId");
        Long courtOwnerId = jsonObject.getLong("courtOwnerId");
        String dateStrS = jsonObject.getString("startDate");
        String dateStrF = jsonObject.getString("endDate");
        String[] tempArrS = dateStrS.split("/");
        String[] tempArrF = dateStrF.split("/");
        int startHour = jsonObject.getInt("startHour");
        int finishHour = jsonObject.getInt("finishHour");
        int yearS, monthS, dayS, yearF, monthF, dayF;
        Date stDate, endDate;
        Time timeFrom, timeTo;
        if (tempArrS.length != 3 || tempArrF.length != 3)
            return "In valid date1";
        yearS = Integer.parseInt(tempArrS[2]);
        yearF = Integer.parseInt(tempArrF[2]);
        monthS = Integer.parseInt(tempArrS[0]);
        monthF = Integer.parseInt(tempArrF[0]);
        dayS = Integer.parseInt(tempArrS[1]);
        dayF = Integer.parseInt(tempArrF[1]);
        try {
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
        if (timeFrom.before(courtSchedule.getStartWorkingHours()) || timeTo.after(courtSchedule.getEndWorkingHours()))
            return "In that time the court is closed";


        List<Reservation> oldReservation = scheduleAgent.getScheduleOverlapped(stDate, endDate, timeFrom, timeTo, courtSchedule);
        if (!oldReservation.isEmpty())
            return "that time have reservation";


        Reservation reservation = new Reservation(playerID, playerName, courtId, courtOwnerId, stDate, endDate, timeFrom,
                timeTo, ReservationState.Pending, 0,
                reservationService.calcTotalCost(stDate, endDate, timeFrom, timeTo, courtOptional.get()));
        reservationRepository.save(reservation);
        courtSchedule.getPendingReservations().add(reservation);
        scheduleRepository.save(courtSchedule);
        courtRepository.save(court);
        return "Success";
    }

    public String getReservations(String information) throws JSONException {
        JSONObject jsonObject = new JSONObject(information);
        Long courtId = jsonObject.getLong("courtId");
        Long courtOwnerId = jsonObject.getLong("courtOwnerId");
        String strDate = jsonObject.getString("date");
        String[] tempArrS = strDate.split("/");
        if (tempArrS.length != 3)
            return "In valid date";

        int yearS = Integer.parseInt(tempArrS[2]);
        int monthS = Integer.parseInt(tempArrS[0]);
        int dayS = Integer.parseInt(tempArrS[1]);
        Date date;
        try {
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
        ScheduleAgent scheduleAgent = new ScheduleAgent(scheduleRepository, reservationRepository);
        List<Reservation> reservations = scheduleAgent.getScheduleOverlapped(date, date, new Time(0), new Time(23, 59, 0), court.getCourtSchedule());
        reservations.sort(new ReservationComparitor());
        return "S " + new Gson().toJson(reservations);
    }

    public static class ReservationComparitor implements Comparator<Reservation> {

        @Override
        public int compare(Reservation o1, Reservation o2) {
            if (o1.getId() < o2.getId())
                return -1;
            else if (o1.getId() > o2.getId())
                return 1;
            return 0;
        }
    }


}
