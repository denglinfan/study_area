package com.charles.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IDEA
 * Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
 * Symbol       Value
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000

 * Description: For example, two is written as II in Roman numeral, just two one's added together.
 * Twelve is written as, XII, which is simply X + II. The number twenty seven is written as XXVII, which is XX + V + II.
 * Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not IIII.
 * Instead, the number four is written as IV. Because the one is before the five we subtract it making four.
 * The same principle applies to the number nine, which is written as IX.
 * There are six instances where subtraction is used:  I can be placed before V (5) and X (10) to make 4 and 9.
 *                                                      X can be placed before L (50) and C (100) to make 40 and 90.
 *                                                      C can be placed before D (500) and M (1000) to make 400 and 900.
 * User: Charles
 * Date: 2019-05-15 09:17
 */
public class IntegerToRoman {

    public static String toRoman(int num){
        if(num == 0){
            return null;
        }
        String[] symbols = {"I","V","X","L","C","D","M"};
        List<String> results = new ArrayList<String>();
        int currentIndex = 0;
        while (num > 0){
            StringBuffer sb = new StringBuffer();
            int lastNo = num%10;
            if(lastNo > 0){
                int tempNo = lastNo%5;
                int tempIndex = lastNo/5;
                if(tempNo == 0){
                    sb.append(symbols[currentIndex + 1]);
                }else if(tempNo == 4){
                    sb.append(symbols[currentIndex]).append(symbols[currentIndex + 1 + tempIndex]);
                }else{
                    sb.append(symbols[currentIndex+tempIndex]);
                    while((--tempNo + tempIndex) > 0){
                        sb.append(symbols[currentIndex]);
                    }
                }
                results.add(sb.toString());
            }
            num = num/10;
            currentIndex = currentIndex + 2;
        }

        StringBuffer sbResult = new StringBuffer();
        for (int i = results.size() - 1; i >= 0; i--) {
            sbResult.append(results.get(i));
        }
        return sbResult.toString();
    }

    public static void main(String[] args){
        System.out.println(toRoman(4));
    }
}
