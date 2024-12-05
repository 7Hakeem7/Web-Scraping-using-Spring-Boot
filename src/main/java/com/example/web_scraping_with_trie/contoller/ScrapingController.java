package com.example.web_scraping_with_trie.contoller;


import com.example.web_scraping_with_trie.service.ScrapingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ScrapingController {

    private final ScrapingService scrapingService;

    public ScrapingController(ScrapingService scrapingService) {
        this.scrapingService = scrapingService;
    }

    // On-demand scraping endpoint
    @PostMapping("/scrape")
    public ResponseEntity<String> scrape(@RequestBody Map<String, Object> request) throws IOException {
        List<String> urls = (List<String>) request.get("urls");
        List<String> keywords = (List<String>) request.get("keywords");

        for (String url : urls) {
            scrapingService.scrape(url, keywords);
        }

        return ResponseEntity.ok("Scraping initiated successfully.");
    }

    // Endpoint to retrieve scraped data
    @GetMapping("/scraped-data")
    public ResponseEntity<List<String>> getScrapedData() {
        return ResponseEntity.ok(scrapingService.getScrapedData());
    }
}

