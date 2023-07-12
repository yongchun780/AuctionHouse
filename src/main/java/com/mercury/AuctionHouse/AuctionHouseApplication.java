package com.mercury.AuctionHouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class AuctionHouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuctionHouseApplication.class, args);
	}

}
