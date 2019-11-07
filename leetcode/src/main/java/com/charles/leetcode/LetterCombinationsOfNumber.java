package com.charles.leetcode;

import java.util.*;

/**
 * Created with IDEA
 * Description:
 *      Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent.
 *      A mapping of digit to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.
 * User: Charles
 * Date: 2019-06-01 16:00
 */
public class LetterCombinationsOfNumber {

    public static List<String> letterCombinations(String digits) {
        List<String> output = new ArrayList<String>();
        if(digits.length() == 0){
            return Collections.EMPTY_LIST;
        }
        Map<String, String> phone = new HashMap<String, String>() {{
            put("2", "abc");
            put("3", "def");
            put("4", "ghi");
            put("5", "jkl");
            put("6", "mno");
            put("7", "pqrs");
            put("8", "tuv");
            put("9", "wxyz");
        }};

        int currentIndex = 0;
        int totalLength = digits.length();
        while(currentIndex < totalLength){
            List<String> temp = output;
            output = new ArrayList<String>();
            String digit = digits.substring(currentIndex,currentIndex+1);
            String letters = phone.get(digit);
            for(int i = 0; i < letters.length(); i++){
                if(temp.size() == 0){
                    output.addAll(Arrays.asList(letters.split("")));
                    break;
                }
                String currentLetter = letters.substring(i, i + 1);
                for (String s : temp) {
                    output.add(s.concat(currentLetter));
                }
            }
            currentIndex++;
        }
        return output;
    }

    public List<String> letterCombinationsVersion1(String digits) {
        List<String> res = new ArrayList<String>();
        char[] arr = digits.toCharArray();

        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            List<String> temp = new ArrayList<String>();

            char[] maps = mapping(c);
            if (maps.length == 0) {
                return new ArrayList<String>();
            }
            for (int j = 0; j < res.size(); j++) {
                String s = res.get(j);
                for (int k = 0; k < maps.length;k++) {
                    StringBuilder sb = new StringBuilder(s);
                    sb.append(maps[k]);
                    temp.add(sb.toString());
                }
            }
            if (res.size() == 0) {
                for (int k = 0; k < maps.length;k++) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(maps[k]);
                    temp.add(sb.toString());
                }
            }

            res = temp;
        }

        return res;
    }

    public char[] mapping(char c) {
        switch(c) {
            case '2':
                return "abc".toCharArray();
            case '3':
                return "def".toCharArray();
            case '4':
                return "ghi".toCharArray();
            case '5':
                return "jkl".toCharArray();
            case '6':
                return "mno".toCharArray();
            case '7':
                return "pqrs".toCharArray();
            case '8':
                return "tuv".toCharArray();
            case '9':
                return "wxyz".toCharArray();
            default:
                return new char[0];
        }
    }

    public static void main(String[] args){
        List<String> result = letterCombinations("23");
        for (String s : result) {
            System.out.print(s + ",");
        }
    }
}
