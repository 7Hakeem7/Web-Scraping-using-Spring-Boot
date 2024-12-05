package com.example.web_scraping_with_trie.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScrapingService {

    // A list to store scraped data (to simulate a database for now)
    private final List<String> scrapedData = new ArrayList<>();

    // On-demand scraping method
    public String scrape(String url, List<String> keywords) throws IOException {
        Document document = Jsoup.connect(url).get();
        String bodyText = document.body().text();

        // Filter content based on keywords
        for (String keyword : keywords) {
            if (bodyText.contains(keyword)) {
                scrapedData.add("Found keyword: " + keyword + " at URL: " + url);
            }
        }
        return "Scraping completed for: " + url;
    }

    // Scheduled scraping task
    @Scheduled(fixedRate = 60000) // Every 1 minute
    public void scheduledScraping() throws IOException {
        System.out.println("Scheduled scraping triggered...");
        // Sample URL and keywords for demo purposes
        scrape("https://example.com", List.of("technology", "innovation"));
    }

    // Retrieve scraped data (for testing purposes)
    public List<String> getScrapedData() {
        return scrapedData;
    }
}

