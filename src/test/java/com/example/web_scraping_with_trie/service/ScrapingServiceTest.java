package com.example.web_scraping_with_trie.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ScrapingServiceTest {

    @Mock
    private Trie trie; // Mock the Trie dependency

    @InjectMocks
    private ScrapingService scrapingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testScrape() throws Exception {
        String url = "https://example.com";
        List<String> keywords = List.of("example", "domain");
    
        // Mock Trie behavior
        doNothing().when(trie).insert(anyString());
    
        // Call the scrape method
        scrapingService.scrape(url, keywords);
    
        // Verify Trie insertion for each keyword
        for (String keyword : keywords) {
            verify(trie, times(1)).insert(keyword);
        }
    
        // Validate that scraped data is stored correctly
        List<Map<String, String>> result = scrapingService.getScrapedData();
        assertEquals(2, result.size()); // Ensure one keyword matched
        assertEquals("example", result.get(0).get("keyword"));
        assertEquals(url, result.get(0).get("url"));
    }    

    @Test
    void testGetScrapedData() {
        // Use the scrape method to populate data
        String url = "https://example.com";
        List<String> keywords = List.of("example");
        try {
            scrapingService.scrape(url, keywords);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Call the method and validate
        List<Map<String, String>> result = scrapingService.getScrapedData();
        assertEquals(1, result.size()); // Check if data was added
        assertEquals("example", result.get(0).get("keyword"));
        assertEquals(url, result.get(0).get("url"));
    }



    @Test
 void testSearchByPrefix() {
        String prefix = "exa";
        int limit = 1;
    
        // Mock Trie search behavior
        when(trie.getWordsWithPrefix(prefix)).thenReturn(List.of("example"));
    
        // Populate data
        try {
            scrapingService.scrape("https://example.com", List.of("example"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        // Call the method
        List<Map<String, String>> result = scrapingService.searchByPrefix(prefix, limit);
    
        // Verify the results
        assertEquals(1, result.size()); // Ensure the search returns results
        assertEquals("example", result.get(0).get("keyword"));
    
        // Verify Trie interaction
        verify(trie, times(1)).getWordsWithPrefix(prefix);
    }
}