package com.example.web_scraping_with_trie.controller;

import com.example.web_scraping_with_trie.service.ScrapingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ScrapingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ScrapingService scrapingService;

    @InjectMocks
    private ScrapingController scrapingController;

    @BeforeEach
    void setUp() {
        // Initialize mocks and set up MockMvc
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(scrapingController).build();
    }

    @Test
    void testScrapeEndpoint() throws Exception {
        doNothing().when(scrapingService).scrape(anyString(), anyList());

        mockMvc.perform(post("/api/v1/scrape")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "urls": ["https://example.com"],
                        "keywords": ["test"]
                    }
                """))
                .andExpect(status().isOk());
    }

    @Test
    void testGetScrapedDataEndpoint() throws Exception {
        when(scrapingService.getScrapedData()).thenReturn(
                List.of(Map.of(
                        "keyword", "test",
                        "url", "https://example.com",
                        "matchedContent", "This is a test content",
                        "timestamp", "2024-12-05T10:15:30"
                ))
        );

        mockMvc.perform(get("/api/v1/scraped-data")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].keyword").value("test"))
                .andExpect(jsonPath("$[0].url").value("https://example.com"))
                .andExpect(jsonPath("$[0].matchedContent").value("This is a test content"))
                .andExpect(jsonPath("$[0].timestamp").value("2024-12-05T10:15:30"));
    }

    @Test
    void testSearchEndpoint() throws Exception {
        when(scrapingService.searchByPrefix(anyString(), anyInt())).thenReturn(
                List.of(Map.of(
                        "keyword", "test",
                        "url", "https://example.com",
                        "matchedContent", "This is a test content",
                        "timestamp", "2024-12-05T10:15:30"
                ))
        );

        mockMvc.perform(post("/api/v1/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "prefix": "te",
                        "limit": 5
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.results[0].keyword").value("test"))
                .andExpect(jsonPath("$.results[0].url").value("https://example.com"))
                .andExpect(jsonPath("$.results[0].matchedContent").value("This is a test content"))
                .andExpect(jsonPath("$.results[0].timestamp").value("2024-12-05T10:15:30"));
    }
}
