package com.project.blogapp;

import com.project.blogapp.model.Role;
import com.project.blogapp.repository.RoleRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "SpringBoot Blog Application",
				description = "Blog Application API documentation",
				version = "v1.0.0",
				contact = @Contact(
						name = "Amith Kashyap H",
						email = "amithkashyaprv@gmail.com",
						url = "amith@amith.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "amith@amith.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "SpringBoot Blog app documentation",
				url = "https://github.com/amithkashyaph/blog-app"
		)
)
public class BlogAppApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Role adminRole = Role.builder()
				.name("ROLE_ADMIN")
				.build();

		Role userRole = Role.builder()
						.name("ROLE_USER")
				        .build();

		roleRepository.save(adminRole);
		roleRepository.save(userRole);
	}
}
