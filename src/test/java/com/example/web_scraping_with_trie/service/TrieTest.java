package com.example.web_scraping_with_trie.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrieTest {
    private Trie trie;

    @BeforeEach
    void setUp() {
        trie = new Trie();
    }

    @Test
    void testInsertAndSearch() {
        // Insert words into the Trie
        trie.insert("test");
        trie.insert("team");
        trie.insert("teach");

        // Test for existing words
        assertTrue(trie.search("test"), "Word 'test' should be found in the Trie.");
        assertTrue(trie.search("team"), "Word 'team' should be found in the Trie.");
        assertTrue(trie.search("teach"), "Word 'teach' should be found in the Trie.");

        // Test for non-existing word
        assertFalse(trie.search("tear"), "Word 'tear' should not be found in the Trie.");
    }

    @Test
    void testGetWordsWithPrefix() {
        // Insert words into the Trie
        trie.insert("apple");
        trie.insert("app");
        trie.insert("application");
        trie.insert("apt");
        trie.insert("banana");

        // Retrieve words with the prefix "app"
        List<String> wordsWithPrefix = trie.getWordsWithPrefix("app");

        // Verify the retrieved words
        assertEquals(3, wordsWithPrefix.size(), "There should be 3 words with the prefix 'app'.");
        assertTrue(wordsWithPrefix.contains("app"), "Word 'app' should be in the result.");
        assertTrue(wordsWithPrefix.contains("apple"), "Word 'apple' should be in the result.");
        assertTrue(wordsWithPrefix.contains("application"), "Word 'application' should be in the result.");

        // Test for a prefix with no matches
        List<String> noMatch = trie.getWordsWithPrefix("xyz");
        assertTrue(noMatch.isEmpty(), "There should be no words with the prefix 'xyz'.");
    }
}
