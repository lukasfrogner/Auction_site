package com.projektgik2h9.auctionsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AuctionsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuctionsiteApplication.class, args);
	}

}
