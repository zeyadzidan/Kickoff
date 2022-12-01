package back.kickoff.kickoffback;

import back.kickoff.kickoffback.model.Court;
import back.kickoff.kickoffback.repositories.CourtRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class KickoffBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(KickoffBackApplication.class, args);
	}
	/*
	@Bean
	CommandLineRunner commandLineRunner(CourtRepository courtRepository)
	{
		return args -> {
			Court court1 = new Court(
               1234, 4333,3,"seven_players"
			);
			courtRepository.save(court1);
            System.out.println(court1.toString());
		};
	}
	 */

}
