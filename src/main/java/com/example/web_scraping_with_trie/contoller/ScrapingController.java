

package com.example.web_scraping_with_trie.contoller;

import com.example.web_scraping_with_trie.service.ScrapingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> scrape(@RequestBody Map<String, Object> request) {
        try {
            // Safely cast the request map to appropriate types
            List<?> rawUrls = (List<?>) request.get("urls");
            List<?> rawKeywords = (List<?>) request.get("keywords");

            if (rawUrls == null || rawKeywords == null) {
                return ResponseEntity.badRequest().body("Invalid input: 'urls' and 'keywords' are required.");
            }

            List<String> urls = rawUrls.stream().map(Object::toString).toList();
            List<String> keywords = rawKeywords.stream().map(Object::toString).toList();

            for (String url : urls) {
                scrapingService.scrape(url, keywords);
            }

            return ResponseEntity.ok("Scraping initiated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    // Endpoint to retrieve scraped data with metadata (url, matchedContent, timestamp)
    @GetMapping("/scraped-data")
    public ResponseEntity<List<Map<String, String>>> getScrapedData() {
        List<Map<String, String>> scrapedData = scrapingService.getScrapedData();
        return ResponseEntity.ok(scrapedData);
    }

    // Endpoint to search for content based on a prefix
    @PostMapping("/search")
    public ResponseEntity<Map<String, Object>> search(@RequestBody Map<String, Object> request) {
        try {
            String prefix = (String) request.get("prefix");
            Integer limit = (Integer) request.get("limit");

            if (prefix == null || limit == null || limit <= 0) {
                return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "'prefix' and 'limit' are required and limit must be > 0"));
            }

            List<Map<String, String>> results = scrapingService.searchByPrefix(prefix, limit);
            return ResponseEntity.ok(Map.of("status", "success", "results", results));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", "An error occurred: " + e.getMessage()));
        }
    }
}
