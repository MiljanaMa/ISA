package medequipsystem;
import medequipsystem.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class MedEquipSystemApplication implements CommandLineRunner {
	@Autowired
	private ClientService clientService;

	public static void main(String[] args) {
		SpringApplication.run(MedEquipSystemApplication.class, args);
	}
	@Override
	public void run(String... args) {
		clientService.updatePenaltyPoints();
	}

}