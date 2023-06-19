package com.wecp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

import com.wecp.entities.User;
import com.wecp.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SpringBootHelloWorldApplication implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHelloWorldApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Starting application");
		User admin = new User();
		admin.setUserId("admin.user");
		admin.setPassword("12345");
		admin.setRole("ADMIN");
			if(userRepository.findByUserId(admin.getUserId())  == null){
				userRepository.save(admin);
			}
		
		
		User customer = new User();
		customer.setUserId("John");
		customer.setPassword("12345");
		customer.setRole("CUSTOMER");
		if(userRepository.findByUserId(customer.getUserId())  == null){
			userRepository.save(customer);
		}
		System.out.println("done");
	}


}