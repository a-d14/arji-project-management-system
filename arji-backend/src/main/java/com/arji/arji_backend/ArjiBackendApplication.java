package com.arji.arji_backend;

import com.arji.arji_backend.models.User;
import com.arji.arji_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class ArjiBackendApplication {

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(ArjiBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner() {
		return args -> {

			User user1 = new User();
			user1.setFirstName("Aakash");
			user1.setLastName("Dasgupta");
			user1.setEmail("a@b.com");
			user1.setUsername("aakash");
			user1.setCreatedAt(LocalDateTime.now());

			userRepository.save(user1);

			User user2 = new User();
			user2.setFirstName("Asmi");
			user2.setLastName("Dasgupta");
			user2.setEmail("b@c.com");
			user2.setUsername("asmi");
			user2.setCreatedAt(LocalDateTime.now());

			userRepository.save(user2);

			User user3 = new User();
			user3.setFirstName("Sumit");
			user3.setLastName("Dasgupta");
			user3.setEmail("c@d.com");
			user3.setUsername("sumit");
			user3.setCreatedAt(LocalDateTime.now());

			userRepository.save(user3);

			User user4 = new User();
			user4.setFirstName("Kuheli");
			user4.setLastName("Dasgupta");
			user4.setEmail("d@e.com");
			user4.setUsername("kuheli");
			user4.setCreatedAt(LocalDateTime.now());

			userRepository.save(user4);


		};
    }

}
