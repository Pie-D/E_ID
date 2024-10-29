package vn.cmcati.eid.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.cmcati.eid.entity.User;
import vn.cmcati.eid.enums.Role;
import vn.cmcati.eid.repository.UserRepository;

import java.time.Instant;


@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;
    @Bean
    ApplicationRunner applicationRunner (UserRepository userRepository) {
        return args -> {
            if (!userRepository.existsByUsername("admin")) {

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .email("admin@admin.com")
                        .roles(Role.ADMIN.name())
                        .createdAt(Instant.now())
                        .build();
                userRepository.save(user);
                log.warn("admin user created");
            }


        };
    }
}

