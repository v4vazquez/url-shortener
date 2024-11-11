package com.URL_shortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class UrlShortener12Application {

	public static void main(String[] args) {
		SpringApplication.run(UrlShortener12Application.class, args);
	}

}
