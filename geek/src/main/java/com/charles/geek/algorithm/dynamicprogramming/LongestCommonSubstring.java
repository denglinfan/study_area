package com.charles.geek.algorithm.dynamicprogramming;

/**
 * @Description 最长公共子串长度只允许增加、删除字符这两个编辑操作
 * @Date 2019/12/29 11:32 下午
 * @Author Charles
 * @Version 1.0.0
 **/
public class LongestCommonSubstring {
    /**
     * mitcmu/mtacnu
     * 最长公共子序列比较的是两个字符串之间的相识度，值越高说明字符串越接近相同
     */
    public static int lcs(String oriString, String patternString){
        int oriLength = oriString.length();
        int patternLength = patternString.length();

        int[][] lcsCollects = new int[oriLength][patternLength];
        char[] oriChars = oriString.toCharArray();
        char[] patternChars = patternString.toCharArray();

        for (int i = 0; i < oriLength; i++) {
            if(oriChars[i] == patternChars[0]){
                lcsCollects[i][0] = 1;
            } else if( i != 0){
                lcsCollects[i][0] = lcsCollects[i - 1][0];
            } else{
                lcsCollects[i][0] = 0;
            }
        }

        for (int i = 0; i < patternLength; i++) {
            if(oriChars[0] == patternChars[i]){
                lcsCollects[0][i] = 1;
            } else if(i != 0){
                lcsCollects[0][i] = lcsCollects[0][i - 1];
            } else{
                lcsCollects[0][i] = 0;
            }
        }

        for (int i = 1; i < oriLength; i++) {
            for (int j = 1;  j < patternLength;  j++) {
                if(oriChars[i] == patternChars[j]){
                    lcsCollects[i][j] = maxLcs(lcsCollects[i - 1][j],lcsCollects[i][j - 1],lcsCollects[i - 1][j - 1] + 1);
                } else{
                    lcsCollects[i][j] = maxLcs(lcsCollects[i - 1][j],lcsCollects[i][j - 1],lcsCollects[i - 1][j - 1]);
                }
            }
        }

        return lcsCollects[oriLength - 1][patternLength - 1];
    }

    public static int maxLcs(int i, int j, int k){
        int temp = i;
        if(temp < j) temp = j;
        if(temp < k) temp = k;

        return temp;
    }


    /**
     * 简单实现，通过递归的方式进行求去，缺陷是会有很多重复的计算
     */
    private static int lcsValue = Integer.MIN_VALUE;
    public static void lcs(char[] oriChars, int oriIndex, char[] patternChars, int patternIndex, int totalLcs){
        int oriLength = oriChars.length;
        int patternLength = patternChars.length;

        if(oriLength == oriIndex || patternLength == patternIndex){
            if(totalLcs > lcsValue) lcsValue = totalLcs;
            return ;
        }

        /**
         * 设当前两个比较的字符为a,b
         * 1. 若a==b,  lcs(oriChars,oriIndex++,patternChars,patternIndex++,totalLcs++)
         * 2. 若a!=b
         *      1. 向oriChars增加字符b,删除patternChars字符b,   lcs(oriChars,oriIndex,patternChars,patternIndex++,totalLcs)
         *      2. 删除oriChars字符a,向patternChars增加字符a,   lcs(oriChars,oriIndex++,patternChars,patternIndex,totalLcs)
         */
        if(oriChars[oriIndex] == patternChars[patternIndex]){
            lcs(oriChars,oriIndex++,patternChars,patternIndex++,totalLcs++);
        } else{
            lcs(oriChars,oriIndex,patternChars,patternIndex++,totalLcs);
            lcs(oriChars,oriIndex++,patternChars,patternIndex,totalLcs);
        }
    }
}
