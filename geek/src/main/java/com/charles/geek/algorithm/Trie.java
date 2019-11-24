package com.charles.geek.algorithm;

/**
 * @Description Trie树数据结构主要是为了在前缀查询比较多的情况下，效率会比较快
 * @Date 2019/11/23 9:57 下午
 * @Author Charles
 * @Version 1.0.0
 **/
public class Trie {

    private TrieNode rootTree = new TrieNode('/');

    public void buildTrieTree(String word){
        buildTireTree(word.toCharArray());
    }

    public void buildTireTree(char[] wordChars){
        TrieNode nextNode = rootTree;
        for (int i = 0; i < wordChars.length; i++) {
            int currentCharIndex = wordChars[i] - 'a';
            if(nextNode.getTrieNodes()[currentCharIndex] == null){
                TrieNode childTrieNode = new TrieNode(wordChars[i]);
                nextNode.getTrieNodes()[currentCharIndex] = childTrieNode;
            }
            nextNode = nextNode.getTrieNodes()[currentCharIndex];
        }
        nextNode.setEndingChar(true);
    }

    public boolean findWord(String word){
        return findWord(word.toCharArray());
    }

    public boolean findWord(char[] wordChars){
        TrieNode nextNode = rootTree;
        for (int i = 0; i < wordChars.length; i++) {
            int currentCharIndex = wordChars[i] - 'a';
            if(nextNode.getTrieNodes()[currentCharIndex] == null){
                return false;
            }
            nextNode = nextNode.getTrieNodes()[currentCharIndex];
        }
        if(nextNode.isEndingChar){
            return true;
        }
        return false;
    }

    class TrieNode{
        private char data;
        private TrieNode[] trieNodes = new TrieNode[26];
        private boolean isEndingChar = false;

        public TrieNode(char data){
            this.data = data;
        }

        public char getData() {
            return data;
        }

        public void setData(char data) {
            this.data = data;
        }

        public TrieNode[] getTrieNodes() {
            return trieNodes;
        }

        public void setTrieNodes(TrieNode[] trieNodes) {
            this.trieNodes = trieNodes;
        }

        public boolean isEndingChar() {
            return isEndingChar;
        }

        public void setEndingChar(boolean endingChar) {
            isEndingChar = endingChar;
        }
    }
}
