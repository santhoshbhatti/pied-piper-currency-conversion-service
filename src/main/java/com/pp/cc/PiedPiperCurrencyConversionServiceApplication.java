package com.pp.cc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PiedPiperCurrencyConversionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PiedPiperCurrencyConversionServiceApplication.class, args);
	}

}
