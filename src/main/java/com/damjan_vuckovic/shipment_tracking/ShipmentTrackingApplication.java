package com.damjan_vuckovic.shipment_tracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ShipmentTrackingApplication {

	public static void main(String[] args) {
		//System.out.println(new BCryptPasswordEncoder().encode("user"));
		//System.out.println(new BCryptPasswordEncoder().encode("admin"));
		SpringApplication.run(ShipmentTrackingApplication.class, args);
	}

}
