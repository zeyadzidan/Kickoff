package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.*;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.repositories.CourtRepository;
import back.kickoff.kickoffback.repositories.ReservationRepository;
import back.kickoff.kickoffback.repositories.ScheduleRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
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
    public String book(String information)  throws JSONException
    {
        JSONObject jsonObject = new JSONObject(information);
        Long reservationId = jsonObject.getLong("reservationId");
        Integer moneyPaid = jsonObject.getInt("moneyPaid");
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if(reservationOptional.isEmpty())
            return "Not found";
        Reservation reservation = reservationOptional.get();
        if(moneyPaid<=0 || moneyPaid>reservation.getTotalCost())
            return "invalid amount of money" ;
        reservation.setMoneyPayed(moneyPaid);
        reservation.setState(ReservationState.Booked);
        reservationRepository.save(reservation);

        Optional<CourtSchedule> optionalCourtSchedule = scheduleRepository.findById(reservation.getCourtID());
        CourtSchedule courtSchedule = optionalCourtSchedule.get() ;
        courtSchedule.getPendingReservations().remove(reservation) ;
        courtSchedule.getBookedReservations().add(reservation) ;
        return "Success";
    }
    public String cancelBookedReservation(Long id)
    {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if(reservationOptional.isEmpty())
            return  "Reservation not found";
        Reservation reservation = reservationOptional.get();
        Optional<CourtSchedule> optionalCourtSchedule = scheduleRepository.findById(reservation.getCourtID());
        if(optionalCourtSchedule.isEmpty())
            return "Court not found" ;
        if(!reservation.getState().equals(ReservationState.Booked)){
            return "Reservation not booked" ;
        }
        CourtSchedule courtSchedule = optionalCourtSchedule.get() ;
        courtSchedule.getBookedReservations().remove(reservation) ;
        int cost = reservation.getMoneyPayed();
        reservationRepository.deleteById(id);
        return Integer.toString(cost);
    }
    public String cancelPendingReservation(Long id)
    {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if(reservationOptional.isEmpty())
            return  "Reservation not found";
        Reservation reservation = reservationOptional.get();
        Optional<CourtSchedule> optionalCourtSchedule = scheduleRepository.findById(reservation.getCourtID());
        if(optionalCourtSchedule.isEmpty())
            return "Court not found" ;
        if(!reservation.getState().equals(ReservationState.Pending)){
            return "Reservation is not pending" ;
        }
        CourtSchedule courtSchedule = optionalCourtSchedule.get() ;
        courtSchedule.getPendingReservations().remove(reservation) ;
        reservationRepository.deleteById(id);
        return "Success";
    }

    public String setPending(String information) throws JSONException {
        JSONObject jsonObject = new JSONObject(information);
        Long courtId = jsonObject.getLong("courtId");
        Long courtOwnerId = jsonObject.getLong("courtOwnerId");
        String dateStrS = jsonObject.getString("startDate");
        String dateStrF = jsonObject.getString("endDate");
        String[] tempArrS = dateStrS.split("//");
        String[] tempArrF = dateStrF.split("//");
        int startHour = jsonObject.getInt("startHour");
        int finishHour = jsonObject.getInt("finishHour");
        int yearS, monthS, dayS, yearF, monthF, dayF;
        Date stDate, endDate;
        Time timeFrom, timeTo;
        if(tempArrS.length != 3 || tempArrF.length != 3)
            return "In valid date";
        yearS = Integer.parseInt(tempArrS[0]); yearF = Integer.parseInt(tempArrF[0]);
        monthS = Integer.parseInt(tempArrS[1]); monthF = Integer.parseInt(tempArrF[1]);
        dayS = Integer.parseInt(tempArrS[2]); dayF = Integer.parseInt(tempArrF[2]);
        try
        {
            stDate = new Date(yearS, monthS, dayS);
            endDate = new Date(yearF, monthF, dayF);
        }
        catch (Exception e)
        {
            return "In valid date";
        }
        try
        {
            timeFrom = new Time(startHour, 0, 0);
            timeTo = new Time(finishHour, 0, 0);
        }
        catch (Exception e)
        {
            return "In valid Time";
        }
        Optional<Court> courtOptional = courtRepository.findById(courtId);
        Optional<CourtOwner> courtOwnerOptional = courtOwnerRepository.findById(courtOwnerId);
        if(courtOptional.isEmpty() || courtOwnerOptional.isEmpty())
            return "Court Not found";
        Court court = courtOptional.get();
        if(!court.getCourtOwner().equals(courtOwnerOptional.get())){
            return "Court does not belong to the courtOwner" ;
        }
        Reservation reservation = new Reservation(courtId, courtOwnerId, stDate, endDate, timeFrom,
                timeTo, ReservationState.Pending,0,
                reservationService.calcTotalCost(stDate, endDate, timeFrom, timeTo, courtOptional.get()));
        reservationRepository.save(reservation);
        CourtSchedule courtSchedule = court.getCourtSchedule();
        courtSchedule.getPendingReservations().add(reservation);
        scheduleRepository.save(courtSchedule);
        courtRepository.save(court);
        return "Success";
    }
}
