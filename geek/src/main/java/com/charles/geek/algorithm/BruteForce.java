package com.charles.geek.algorithm;

/**
 * @Description 字符串单模式匹配--暴力匹配算法/朴素匹配算法
 * @Date 2019/11/15 10:08 下午
 * @Author Charles
 * @Version 1.0.0
 **/
public class BruteForce {

    public static void main(String[] args) {
        String coreStr = "ailkmno";
        String patternStr = "ak";
        int index = findChildStr(coreStr,patternStr);
        System.out.println(String.format("字符串[%s]在主串[%s]出现到起始位置为：%s",patternStr,coreStr,index));
    }

    private static int findChildStr(String coreStr, String patternStr) {
        char[] coreChars = coreStr.toCharArray();
        char[] patternChars = patternStr.toCharArray();
        int coreIndex = 0;
        int patternIndex = 0;
        while(coreIndex <= coreChars.length - patternChars.length + 1 && patternIndex < patternChars.length){
            if(coreChars[coreIndex] == patternChars[patternIndex]){
                coreIndex++;
                patternIndex++;
                continue;
            }
            patternIndex = 0;
            //主串到遍历位置向前行进1个单位长度
            coreIndex = coreIndex - patternIndex + 1;
        }
        if(patternIndex == patternChars.length){
            return coreIndex - patternIndex;
        }
        return -1;
    }
}
