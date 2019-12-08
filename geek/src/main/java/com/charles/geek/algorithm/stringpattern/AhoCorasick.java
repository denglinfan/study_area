package com.charles.geek.algorithm.stringpattern;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Description ACz自动机实现
 * @Date 2019/12/8 10:21 下午
 * @Author Charles
 * @Version 1.0.0
 **/
public class AhoCorasick {

    private AcNode rootNode = new AcNode('/');

    public String replaceIfMatch(char[] text){
        int textLength = text.length;
        AcNode currentNode = rootNode;
        for (int i = 0; i < textLength; i++) {
            int currentIndex = text[i] - 'a';
            while(currentNode.getChildren()[currentIndex] == null && currentNode != rootNode){
                currentNode = currentNode.fail;
            }
            currentNode = currentNode.getChildren()[currentIndex];
            //TODO 此判断是否有必要
            if(currentNode == null){
                currentNode = rootNode;
            }
            AcNode tmp = currentNode;
            while (tmp != rootNode){
                if(tmp.isEndingChar()){
                    int position = i - tmp.length + 1;
                    System.out.println("匹配起始下标" + position + "; 长度" + tmp.length);
                }
                tmp = tmp.fail;
            }

        }
        return "";
    }

    public void buildFailurePointer(){
        Queue<AcNode> queue = new LinkedList<AcNode>();
        rootNode.fail = null;
        queue.add(rootNode);
        while(!queue.isEmpty()){
            AcNode p = queue.remove();
            for (int i = 0; i < 26; i++) {
                AcNode pc = p.children[i];
                if(pc == null){
                    continue;
                }
                if(p == rootNode){
                    pc.fail = rootNode;
                }else{
                    AcNode q = p.fail;
                    while(q != null){
                        AcNode qc = q.getChildren()[pc.getData() - 'a'];
                        if(qc != null) {
                            pc.fail = qc;
                            break;
                        }
                        q = q.fail;
                    }
                    if(q == null){
                        pc.fail = rootNode;
                    }
                }
                queue.add(pc);
            }
        }
    }

    public void buildTrieTree(char[] wordChars){
        AcNode nextNode = rootNode;
        for (int i = 0; i < wordChars.length; i++) {
            int currentIndex = wordChars[i] - 'a';
            if(nextNode.getChildren()[currentIndex] == null){
                AcNode newNode = new AcNode(wordChars[i]);
                newNode.getChildren()[currentIndex] = newNode;
            }
            nextNode = nextNode.getChildren()[currentIndex];
        }
        nextNode.setEndingChar(true);
    }

    public class AcNode{
        private char data;
        private AcNode[] children = new AcNode[26];
        private boolean isEndingChar = false;
        private Integer length = -1;//记录当isEndingChar=true时，模式串长度
        private AcNode fail;//失败指针

        public AcNode(char data) {
            this.data = data;
        }

        public char getData() {
            return data;
        }

        public AcNode[] getChildren() {
            return children;
        }

        public boolean isEndingChar() {
            return isEndingChar;
        }

        public void setEndingChar(boolean endingChar) {
            isEndingChar = endingChar;
        }

        public Integer getLength() {
            return length;
        }

        public void setLength(Integer length) {
            this.length = length;
        }

        public AcNode getFail() {
            return fail;
        }

        public void setFail(AcNode fail) {
            this.fail = fail;
        }
    }
}
