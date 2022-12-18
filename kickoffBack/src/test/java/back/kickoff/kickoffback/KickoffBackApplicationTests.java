package back.kickoff.kickoffback;

import back.kickoff.kickoffback.model.Court;
import back.kickoff.kickoffback.model.CourtSchedule;
import back.kickoff.kickoffback.model.Reservation;
import back.kickoff.kickoffback.repositories.CourtRepository;
import back.kickoff.kickoffback.repositories.PlayerRepository;
import back.kickoff.kickoffback.repositories.ReservationRepository;
import back.kickoff.kickoffback.repositories.ScheduleRepository;
import back.kickoff.kickoffback.services.ReservationService;
import back.kickoff.kickoffback.services.ScheduleAgent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class KickoffBackApplicationTests {

    @Test
    void contextLoads() {

    }


//	@Autowired
//	ReservationRepository rr ;
//	@Autowired
//	CourtRepository cr ;
//	@Autowired
//	LitePlayerRepository pr ;
//	@Autowired
//	ScheduleRepository sr ;
//
//	public KickoffBackApplicationTests(ReservationRepository rr, CourtRepository cr, LitePlayerRepository pr, ScheduleRepository sr){
//		this.rr =rr;
//		this.cr = cr;
//		this.pr = pr ;
//		this.sr = sr ;
//	}


//	@Bean
//	public void tryy(){

/*
		try {


		Court c = new Court((long) 1243, 1, "5");
		cr.save(c);

		ReservationService rs = new ReservationService(rr, pr);
		Long id = rs.tryy(c.id, c.Court_Owner_id);
		Optional<Reservation> reservation = rr.findById(id);
		System.out.println(reservation.get().toString());

		Time startWorkingHours = new Time(10, 0, 0);
		Time endWorkingHours = new Time(23, 0, 0);
		Time endMorning = new Time(16, 0, 0);
		CourtSchedule courtSchedule = new CourtSchedule(c.id, startWorkingHours, endWorkingHours, endMorning, 200, 200);
		sr.save(courtSchedule);

		ScheduleAgent scheduleAgent = new ScheduleAgent(sr, rr);
		Date df = new Date(2022, 12, 1);
		Date dt = new Date(2022, 12, 2);

		List<Reservation> res = scheduleAgent.getScheduleBetween(df, dt, startWorkingHours, endWorkingHours, c.id);
		for (Reservation r : res) {
			System.out.println(r.toString());
		}
		//}catch (Exception e){
		//  System.out.println(e);
		//}

*/

//	}

}
