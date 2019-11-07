package com.charles.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IDEA
 * Description: written in a zigzag pattern on a given number of rows
 * User: Charles
 * Date: 2019-04-14 22:22
 */
public class ZigZagConversion {
    /**
     * example1:
     *
     *  Input: s = "PAYPALISHIRING", numRows = 4
     *  Output: "PINALSIGYAHRPI"
     *  Explanation:
     *
     *      P     I    N
     *      A   L S  I G
     *      Y A   H R
     *      P     I
     */
    public static String ownerConvert(String s, int numRows){
        if(numRows == 1){
            return s;
        }
        List<StringBuffer> results = new ArrayList<StringBuffer>(numRows);
        for(int i = 0; i < numRows; i++){
            results.add(new StringBuffer());
        }
        int currentRowNum = 0;
        boolean goingDown = false;
        for (char c : s.toCharArray()) {
            results.get(currentRowNum).append(c);
            currentRowNum += goingDown ? -1 : 1;
            if(currentRowNum + 1 == numRows || currentRowNum == 0){
                goingDown = !goingDown;
            }
        }

        StringBuffer resultStr = new StringBuffer();
        for(StringBuffer sb : results){
            resultStr.append(sb);
        }
        return resultStr.toString();
    }

    public static String betterConvert(String s, int numRows){
        numRows--;
        if(numRows < 1){
            return s;
        }
        char[] cArr = s.toCharArray();
        int len = cArr.length;
        StringBuilder sb = new StringBuilder();
        //第一行的字符集
        for(int j = 0;j <len ;){
            sb.append(cArr[j]);
            j+=2 * numRows;
        }
        //中间几行的字符集
        for(int i = 1 ;i < numRows;i++){
            int k = 2 * i;
            for(int j = i;j < len;){
                sb.append(cArr[j]);
                j+=2 * numRows;
                if(j<len + k){
                    sb.append(cArr[j - k]);
                }
                else{
                    break;
                }
            }
        }
        //最后一行的字符集
        for(int j = numRows;j <len;){
            sb.append(cArr[j]);
            j+=2 * numRows;
        }
        return sb.toString();
    }

    public static String betterConvert1(String s, int numRows){
        StringBuilder sb = new StringBuilder();
        if(numRows <= 1){
            return s;
        }
        numRows--;
        for(int i = 0, j = 0; i <= numRows; i++)
        {
            j = i;
            while(j < s.length())
            {
                sb.append(s.charAt(j));
                j = j + (numRows - j % numRows) * 2;
            }
        }
        return sb.toString();
    }

    public static void main(String[] args){
        System.out.println(ownerConvert("PAYPALISHIRING",4));
    }
}
