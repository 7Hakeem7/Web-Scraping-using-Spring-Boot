package com.example.web_scraping_with_trie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // Enables scheduling in the application
public class WebScrapingWithTrieApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebScrapingWithTrieApplication.class, args);
	}

}
