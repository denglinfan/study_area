package com.charles.geek.algorithm;

/**
 * @Description 高效的字符串匹配算法，匹配过程包含坏字符规则和好后缀规则
 * @Date 2019/11/18 11:11 下午
 * @Author Charles
 * @Version 1.0.0
 **/
public class BoyerMoore {

    private static final int METADATA_SIZE = 256;

    public static void main(String[] args) {
        String coreStr = "ailkmno";
        String patternStr = "o";
        int index = bmCore(coreStr.toCharArray(),coreStr.length(),patternStr.toCharArray(),patternStr.length());
        System.out.println(String.format("字符串[%s]在主串[%s]出现到起始位置为：%s",patternStr,coreStr,index));
    }

    /**
     * bm算法核心实现
     * @param coreChars
     * @param coreLength
     * @param patternChars
     * @param patternLength
     * @return
     */
    private static int bmCore(char[] coreChars, int coreLength, char[] patternChars, int patternLength){
        int[] patternMetadata = new int[METADATA_SIZE];
        badCharacterRule(patternChars,patternLength,patternMetadata);
        int[] suffix = new int[patternLength];
        boolean[] prefix = new boolean[patternLength];
        goodSuffixShift(patternChars,patternLength,suffix,prefix);

        int firstMatchIndex = 0;
        while(firstMatchIndex <= coreLength - patternLength){
            //从后往前找到第一个不匹配的字符（即坏字符）
            int badCharIndex;
            for(badCharIndex = patternLength - 1;badCharIndex >= 0;badCharIndex--){
                if(patternChars[badCharIndex] != coreChars[firstMatchIndex + badCharIndex]){
                    break;
                }
            }

            if(badCharIndex < 0){
                return firstMatchIndex;
            }

            int moveStepByBadCharacter = badCharIndex - patternMetadata[(int)coreChars[firstMatchIndex+badCharIndex]];

            int moveStepByGoodSuffix = 0;
            if(badCharIndex < patternLength - 1){
                //存在好后缀的情况下，根据好后缀规则获取需要向后移动的长度
                moveStepByGoodSuffix = moveStepByGoodSuffix(badCharIndex,patternLength,suffix,prefix);
            }

            firstMatchIndex += Math.max(moveStepByBadCharacter,moveStepByGoodSuffix);
        }
        return -1;
    }

    private static int moveStepByGoodSuffix(int badCharIndex, int patternLength, int[] suffix, boolean[] prefix) {
        int goodSuffixLength = patternLength - badCharIndex - 1;
        //若好后缀在模式串中存在，返回最大的能匹配好后缀串的索引号到好后缀字串的距离做为向后移动的长度
        if(suffix[goodSuffixLength] != -1) {
            return badCharIndex - suffix[goodSuffixLength] + 1;
        }
        for(int r = badCharIndex + 2; r < patternLength; r++){
            //判断好后缀的后缀字串是否是模式串的前缀字串，若是，则直接返回当前索引号做为向后移动的长度
            if(prefix[patternLength - r] == true){
                return r;
            }
        }
        //若好后缀在模式串中其他位置不存在并且好后缀的后缀字串不存在属于模式串的前缀字串，则直接返回模式串长度做为向后移动的长度
        return patternLength;
    }


    /**
     * 构建模式串在数组中的index位置
     * @param patternChars
     * @param patternLength
     * @param patternMetadata
     */
    private static void badCharacterRule(char[] patternChars, int patternLength, int[] patternMetadata){
        for (int i = 0; i < METADATA_SIZE; i++) {
            patternMetadata[i] = -1;
        }

        for (int i = 0; i < patternLength; i++) {
            int charIndex = (int) patternChars[i];
            patternMetadata[charIndex] = i;
        }
    }

    /**
     * 构造好后缀匹配规则模式串数据
     * @param patternChars
     * @param patternLength
     * @param suffix
     * @param prefix
     */
    private static void goodSuffixShift(char[] patternChars, int patternLength, int[] suffix, boolean[] prefix){
        for (int i = 0; i < patternLength; i++) {
           suffix[i] = -1;
           prefix[i] = false;
        }

        for (int i = 0; i < patternLength - 1; i++) {
            int j = i;
            int commonStrLength = 0;
            while(j >= 0 && patternChars[j] == patternChars[patternLength - 1 - commonStrLength]){
                j--;
                commonStrLength++;
                suffix[commonStrLength] = j+1;
            }
            if(j <= -1){
                prefix[commonStrLength] = true;
            }
        }
    }
}
