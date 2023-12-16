package medequipsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableAsync
public class MedEquipSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedEquipSystemApplication.class, args);
	}

}