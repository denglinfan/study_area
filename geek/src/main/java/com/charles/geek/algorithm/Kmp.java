package com.charles.geek.algorithm;

/**
 * @Description KMP字符串匹配算法
 * @Date 2019/11/21 10:43 下午
 * @Author Charles
 * @Version 1.0.0
 **/
public class Kmp {

    public static void main(String[] args) {
        String coreStr = "ababaeabac";
        String patternStr = "bc";
        int index = kmp(coreStr.toCharArray(),patternStr.toCharArray(),coreStr.length(),patternStr.length());
        System.out.println(String.format("模式串[%s]在主串[%s]第一次出现的位置：[%s]",patternStr,coreStr,index));
    }

    public static int kmp(char[] coreStr, char[] patternStr, int coreLength, int patternLength){
        //构建模式串中所有的前缀字串的最长前缀字串数组
        int[] next = buildNextIndex(patternStr,patternLength);
        int startIndex = 0;
        for (int i = startIndex; i < coreLength; i++) {
            while(startIndex > 0 && coreStr[i] != patternStr[startIndex]){
                startIndex = next[startIndex - 1] + 1;
            }
            if(coreStr[i] == patternStr[startIndex]){
                startIndex++;
            }
            if(startIndex == patternLength){
                return i - patternLength + 1;
            }
        }

        return -1;
    }

    /**
     * KMP算法核心实现，预处理模式串，计算出好前缀对应的后缀字串与前缀字串的匹配度
     * @param patternStr
     * @param patternLength
     * @return
     */
    private static int[] buildNextIndex(char[] patternStr, int patternLength) {
        int[] next = new int[patternLength];
        next[0] = -1;
        int k = -1;
        for (int i = 1; i < patternLength; i++) {
            while( k != -1 && patternStr[k + 1] != patternStr[i]){
                k = next[k];
            }
            if(patternStr[k + 1] == patternStr[i]){
                k++;
            }
            next[i] = k;
        }
        return next;
    }
}
