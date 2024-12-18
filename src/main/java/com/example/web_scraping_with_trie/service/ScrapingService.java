package com.example.web_scraping_with_trie.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ScrapingService {
    private Trie trie = new Trie();
    private final List<Map<String, String>> scrapedData = new ArrayList<>();
    private final List<String> scheduledUrls = new ArrayList<>();
    private final List<String> scheduledKeywords = new ArrayList<>();

    // Constructor injection for dependency
    public ScrapingService(Trie trie) {
        this.trie = trie;
    }

    // Add URLs and keywords for scheduled scraping
    public void addScheduledTask(String url, List<String> keywords) {
        scheduledUrls.add(url);
        scheduledKeywords.addAll(keywords);
    }

    // Scrape a single URL and check for keywords
    public void scrape(String url, List<String> keywords) throws IOException {
        try {
            Document document = Jsoup.connect(url).get();
            String content = document.text();
            LocalDateTime timestamp = LocalDateTime.now(); // Record timestamp

            // Check for each keyword in the content
            for (String keyword : keywords) {
                if (content.contains(keyword)) {
                    String matchedContent = extractMatchedContent(content, keyword); // Extract content
                    trie.insert(keyword); // Insert keyword into Trie
                    
                    // Store metadata for the scraped data
                    scrapedData.add(Map.of(
                        "keyword", keyword,
                        "url", url,
                        "matchedContent", matchedContent,
                        "timestamp", timestamp.toString()
                    ));
                }
            }
        } catch (IOException e) {
            throw new IOException("Failed to scrape URL: " + url, e);
        }
    }

    @Scheduled(fixedRate = 3600000) // 1 hour in milliseconds
    public void performScheduledScraping() {
        for (String url : scheduledUrls) {
            try {
                scrape(url, scheduledKeywords);
            } catch (IOException e) {
                System.err.println("Error during scheduled scraping for URL: " + url + " - " + e.getMessage());
            }
        }
    }

    // Helper method to extract matched content (customize as per requirements)
    private String extractMatchedContent(String content, String keyword) {
        int startIndex = content.indexOf(keyword);
        int endIndex = Math.min(startIndex + 100, content.length()); // Get first 100 chars after keyword
        return content.substring(startIndex, endIndex) + "..."; // Add "..." at the end
    }

    // Retrieve all scraped data with metadata (url, matchedContent, timestamp)
    public List<Map<String, String>> getScrapedData() {
        return new ArrayList<>(scrapedData);
    }

    // Search for results by prefix in scraped data using Trie
    public List<Map<String, String>> searchByPrefix(String prefix, int limit) {
        // Find all matching keywords in the trie
        List<String> keywords = trie.getWordsWithPrefix(prefix);

        // Filter scraped data by matched keywords
        return scrapedData.stream()
            .filter(data -> keywords.contains(data.get("keyword")))
            .limit(limit) // Limit the number of results
            .toList();
    }
}
