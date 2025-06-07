package org.example;

import org.example.model.Session;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.SessionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootApplication
public class CinemaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            SessionService sessionService
    ) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                userRepository.save(admin);
            }

            if (sessionService.findAll().isEmpty()) {
                Session session1 = new Session();
                session1.setMovieTitle("The Matrix");
                session1.setStudio("Warner Bros");
                session1.setSessionDateTime(LocalDateTime.of(2024, 6, 15, 18, 0));
                session1.setTicketCount(100);
                sessionService.save(session1);

                Session session2 = new Session();
                session2.setMovieTitle("Inception");
                session2.setStudio("Paramount");
                session2.setSessionDateTime(LocalDateTime.of(2024, 6, 16, 20, 30));
                session2.setTicketCount(80);
                sessionService.save(session2);
            }
        };
    }
}