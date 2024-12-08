package com.example.web_scraping_with_trie.service;

import java.util.ArrayList;
import java.util.List;

public class Trie {
    private Node root;

    public Trie() {
        root = new Node();
    }

    public void insert(String word) {
        Node node = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (!node.containsKey(ch)) {
                node.put(ch, new Node());
            }
            node = node.get(ch);
        }
        node.setEnd();
    }

    public boolean search(String word) {
        Node node = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (!node.containsKey(ch)) {
                return false;
            }
            node = node.get(ch);
        }
        return node.isEnd();
    }

    public boolean startsWith(String prefix) {
        Node node = root;
        for (int i = 0; i < prefix.length(); i++) {
            char ch = prefix.charAt(i);
            if (!node.containsKey(ch)) {
                return false;
            }
            node = node.get(ch);
        }
        return true;
    }

    public List<String> getWordsWithPrefix(String prefix) {
        Node node = root;
        List<String> words = new ArrayList<>();
        for (int i = 0; i < prefix.length(); i++) {
            char ch = prefix.charAt(i);
            if (!node.containsKey(ch)) {
                return words;
            }
            node = node.get(ch);
        }
        collectWords(node, new StringBuilder(prefix), words);
        return words;
    }

    public List<String> searchByPrefix(String prefix, int limit) {
        List<String> words = getWordsWithPrefix(prefix);
        return words.subList(0, Math.min(words.size(), limit)); // Limit the results
    }
    

    private void collectWords(Node node, StringBuilder prefix, List<String> words) {
        if (node.isEnd()) {
            words.add(prefix.toString());
        }
        for (char ch = 'a'; ch <= 'z'; ch++) {
            if (node.containsKey(ch)) {
                prefix.append(ch);
                collectWords(node.get(ch), prefix, words);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
    }
}

