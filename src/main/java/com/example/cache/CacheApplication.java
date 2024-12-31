package com.example.cache;

import com.example.cache.domain.enitity.User;
import com.example.cache.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@RequiredArgsConstructor
public class CacheApplication implements ApplicationRunner {

	private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(CacheApplication.class, args);
	}

	// application 실행 시점에 data insert
	@Override
	public void run(ApplicationArguments args) throws Exception {
		userRepository.save(User.builder().name("greg").email("greg@gmail.com").build());
		userRepository.save(User.builder().name("tony").email("tony@gmail.com").build());
		userRepository.save(User.builder().name("bob").email("bob@gmail.com").build());
		userRepository.save(User.builder().name("ryan").email("ryan@gmail.com").build());
	}
}
