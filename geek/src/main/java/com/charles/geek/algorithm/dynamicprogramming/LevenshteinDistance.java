package com.charles.geek.algorithm.dynamicprogramming;

/**
 * @Description 莱文斯坦距离允许增加、删除、替换字符这三个编辑操作
 * @Date 2019/12/29 11:30 下午
 * @Author Charles
 * @Version 1.0.0
 **/
public class LevenshteinDistance {
    /**
     * mitcmu/mtacnu
     * 这个问题是求把一个字符串变成另一个字符串，需要的最少编辑次数(比较的是差异情况，编辑次数越多，差异越大)
     */
    public static int editNum(String oriStr, String patternStr){
        int[][] temp = new int[oriStr.length()][patternStr.length()];
        char[] oriChars = oriStr.toCharArray();
        char[] patternChars = patternStr.toCharArray();

        int oriLength = oriChars.length;
        int patternLength = patternChars.length;

        for (int i = 0; i < oriLength; i++) {
            if(oriChars[i] == patternChars[0]) {
                temp[i][0] = i;
            }else if( i != 0){
                temp[i][0] = temp[i - 1][0];
            } else{
                temp[i][0] = 1;
            }
        }

        for (int j = 0; j < patternLength; j++) {
            if(oriChars[0] == patternChars[j]){
                temp[0][j] = j;
            } else if(j != 0){
                temp[0][j] = temp[0][j - 1] + 1;
            } else{
                temp[0][j] = 1;
            }
        }

        for (int oriIndex = 1; oriIndex < oriLength; oriIndex++) {
            for (int patternIndex = 1; patternIndex < patternLength; patternIndex++) {
                if(oriChars[oriIndex] == patternChars[patternIndex]){
                    temp[oriIndex][patternIndex] = minEdit(temp[oriIndex - 1][patternIndex - 1],temp[oriIndex - 1][patternIndex] + 1,temp[oriIndex][patternIndex - 1] + 1);
                } else {
                    temp[oriIndex][patternIndex] = minEdit(temp[oriIndex - 1][patternIndex - 1] + 1,temp[oriIndex - 1][patternIndex] + 1,temp[oriIndex][patternIndex - 1] + 1);
                }
            }
        }

        return temp[oriLength - 1][patternLength - 1];
    }

    public static int minEdit(int i, int j, int k){
        int temp = i;
        if(temp > j) temp = j;
        if(temp > k) temp = k;
        return temp;
    }

    /**
     * 简单实现，通过递归方式进行计算，缺陷是会重复进行相同的逻辑判断，效率比较低下
     */
    private static int minDist = Integer.MAX_VALUE;
    public static void editNum(char[] oriChars, int oriIndex, char[] patternChars, int patternIndex, int totalEditNum){
        /**
         * 设两个字符分别为a,b
         * 1. a == b，ediNum(oriChars, oriIndex++, patternChars, patternIndex++, totalEdiNum)
         * 2. a != b：
         *      1. oriChars添加b,patternChars删除b ==> ediNum(oriChars, oriIndex, patternChars, patternIndex++, totalEdiNum++)
         *      2. patternChars增加a,oriChars删除a ==> ediNum(oriChars, oriIndex++, patternChars, patternIndex, totalEditNum++)
         *      3. oriChars替换a为b,patternChars替换b为a ==> ediNum(oriChars, oriIndex++, patternChars, patternIndex++, totalEditNum++)
         */
        int oriLength = oriChars.length;
        int patternLength = patternChars.length;
        if(oriLength == oriIndex || patternLength == patternIndex){
            if(oriIndex < oriLength) totalEditNum += (oriLength - oriIndex);
            if(patternIndex < patternLength) totalEditNum += (patternLength - patternIndex);
            if(minDist > totalEditNum) minDist = totalEditNum;
            return ;
        }

        if(oriChars[oriIndex] == patternChars[patternIndex]){
            editNum(oriChars,oriIndex++,patternChars,patternIndex++,totalEditNum);
        } else{
            editNum(oriChars,oriIndex,patternChars,patternIndex++,totalEditNum++);
            editNum(oriChars,oriIndex++,patternChars,patternIndex,totalEditNum++);
            editNum(oriChars,oriIndex++,patternChars,patternIndex++,totalEditNum++);
        }
    }
}
