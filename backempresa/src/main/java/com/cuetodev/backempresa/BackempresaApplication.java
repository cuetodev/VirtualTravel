package com.cuetodev.backempresa;

import com.cuetodev.backempresa.User.application.UserUseCase;
import com.cuetodev.backempresa.User.application.port.UserPort;
import com.cuetodev.backempresa.User.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackempresaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackempresaApplication.class, args);
	}

	@Autowired
	UserPort userPort;

	// Only for testing
	@Bean
	CommandLineRunner creatingTestUsers() {
		return p -> {
			User checkNormalUser = userPort.findUserByEmailAndByPassword("user@user.es", "user");
			User checkAdminUser = userPort.findUserByEmailAndByPassword("admin@admin.es", "admin");

			if (checkNormalUser == null) {
				User normalUser = new User(null, "user@user.es", "user", "ROLE_USER");
				userPort.saveUser(normalUser);
			}

			if (checkAdminUser == null) {
				User adminUser = new User(null, "admin@admin.es", "admin", "ROLE_ADMIN");
				userPort.saveUser(adminUser);
			}
		};
	}

}
