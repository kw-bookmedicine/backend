package kr.KWGraduate.BookPharmacy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookPharmacyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookPharmacyApplication.class, args);
	}

}
