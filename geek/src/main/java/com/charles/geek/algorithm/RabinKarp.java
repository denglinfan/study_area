package com.charles.geek.algorithm;

/**
 * @Description
 * @Date 2019/11/15 10:14 下午
 * @Author Charles
 * @Version 1.0.0
 **/
public class RabinKarp {

    public static void main(String[] args) {
        String coreStr = "acbdfad";
        String patternStr = "ch";
        int index = specificRK(coreStr,patternStr);
        System.out.println(String.format("字符串[%s]在主串[%s]出现到起始位置为：%s",patternStr,coreStr,index));
    }

    public static int simpleRK(String coreStr, String patternStr){
        int coreStrLength = coreStr.length();
        int patternStrLength = patternStr.length();
        int patternHash = patternStr.hashCode();
        int currentIndex = 0;
        while(currentIndex + patternStrLength <= coreStrLength){
            int childCoreHash = coreStr.substring(currentIndex,currentIndex + patternStrLength).hashCode();
            if(patternHash == childCoreHash){
                return currentIndex;
            }
            currentIndex++;
        }
        return -1;
    }

    //TODO 如何保存这个超大的数字数组
    private static long[] constants = new long[26];
    static{
        int i = 1;
        constants[0] = 1L;
        while(i < 26){
            constants[i] = constants[i - 1] * 26;
            i++;
        }
    }
    public static int specificRK(String coreStr, String patternStr){
        int coreStrLength = coreStr.length();
        int patternStrLength = patternStr.length();
        char[] coreStrChars = coreStr.toCharArray();
        char[] patternStrChars = patternStr.toCharArray();
        //计算模式串到hash值
        long patternHash = 0L;
        int currentPatternIndex = 0;
        while(currentPatternIndex < patternStrLength){
            patternHash += (patternStrChars[currentPatternIndex] - 'a')*constants[patternStrLength - currentPatternIndex - 1];
            currentPatternIndex++;
        }
        //计算主串前patternStrLength（模式串长度）长度的字串的hash值
        long coreStartHash = 0L;
        int currentIndex = 0;
        while(currentIndex < patternStrLength){
            coreStartHash += (coreStrChars[currentIndex] - 'a')*constants[patternStrLength - currentIndex - 1];
            currentIndex++;
        }
        //比较是否相等，若是，则直接返回，否则继续计算
        if(coreStartHash == patternHash){
            return 0;
        }

        currentIndex = 1;
        long tempCoreHash = coreStartHash;
        while(currentIndex + patternStrLength <= coreStrLength){
             //公式：h[i] = (h[i-1] - 26^(m-1) * (S[i-1]-'a'))*26 + 26^0 * (S[i+m-1] - 'a');
             tempCoreHash = (tempCoreHash - constants[patternStrLength - 1]*(coreStrChars[currentIndex - 1] - 'a')) * 26
                     + (coreStrChars[currentIndex + patternStrLength - 1] - 'a')*constants[0];
             if(tempCoreHash == patternHash){
                 return currentIndex;
             }
             currentIndex++;
        }
        return -1;
    }
}
