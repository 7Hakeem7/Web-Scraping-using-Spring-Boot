package com.example.web_scraping_with_trie.service;

public class Node {
    private Node[] links;
    private boolean flag;

    public Node() {
        links = new Node[26];
        flag = false;
    }

    public boolean containsKey(char ch) {
        return links[ch - 'a'] != null;
    }

    public void put(char ch, Node node) {
        links[ch - 'a'] = node;
    }

    public Node get(char ch) {
        return links[ch - 'a'];
    }

    public void setEnd() {
        flag = true;
    }

    public boolean isEnd() {
        return flag;
    }
}

