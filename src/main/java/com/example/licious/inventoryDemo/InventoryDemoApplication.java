package com.example.licious.inventoryDemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryDemoApplication {

	private static final Logger logger = LoggerFactory.getLogger(InventoryDemoApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(InventoryDemoApplication.class, args);
	}

}
