package com.example.cricketApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication
@EnableScheduling
public class cricketApplication {

	public static void main(String[] args) {
    SpringApplication.run(cricketApplication.class, args);
	}

}
