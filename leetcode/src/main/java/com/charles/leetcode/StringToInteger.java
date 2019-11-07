package com.charles.leetcode;


/**
 * Created with IDEA
 * Description: converts a string to an integer.
 * User: Charles
 * Date: 2019-04-20 22:15
 */
public class StringToInteger {

    public static int myAtoi(String str){
        str = str.trim();
        if(str.length() < 0){
            return 0;
        }
        boolean flag = false;
        int index = 0;
        char temp = str.charAt(index);
        if(temp == '-' || temp == '+'){
            flag = temp == '-';
            index++;
        }
        int s = 0;
        if(index < str.length()){
            for(char c : str.substring(index).toCharArray()){
                if(c < '0' || c > '9'){
                    break;
                }
                if(flag && (-s < Integer.MIN_VALUE/10 || (-s == Integer.MIN_VALUE/10 && c - 48 > 8))){
                    return Integer.MIN_VALUE;
                }
                if(!flag && (s > Integer.MAX_VALUE/10 || (s == Integer.MAX_VALUE/10 && c - 48 > 7))){
                    return Integer.MAX_VALUE;
                }
                s = s * 10 + c - 48;
            }
        }
        return flag?-s:s;
    }

    public static void main(String[] args){
        System.out.println(myAtoi("-91283472332"));
    }
}
