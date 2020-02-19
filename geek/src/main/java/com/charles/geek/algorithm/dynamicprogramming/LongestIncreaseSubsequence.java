package com.charles.geek.algorithm.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 动态规划之   最长递增子序列
 * @Date 2019/12/29 10:32 下午
 * @Author Charles
 * @Version 1.0.0
 **/
public class LongestIncreaseSubsequence {

    /**
     * 我们有一个数字序列包含 n 个不同的数字，如何求出这个序列中的最长递增子序列长度？
     * 比如 2, 9, 3, 6, 5, 1, 7 这样一组数字序列，它的最长递增子序列就是 2, 3, 5, 7，所以最长递增子序列的长度是 4。
     */

    public static String calculate(int[] originalSubs){
        int subLength = originalSubs.length;
        if(subLength <= 1){
            return originalSubs.toString();
        }

        List<String> longestSubWithIndex = new ArrayList<String>(subLength);
        longestSubWithIndex.add(String.valueOf(originalSubs[0]));

        int currentIndex = 1;
        while(currentIndex < subLength){
            int tempIndex = currentIndex - 1;
            String prevLongestSub = longestSubWithIndex.get(tempIndex);
            while(tempIndex >= 0){
                int biggestChar = Integer.valueOf(prevLongestSub.substring(prevLongestSub.length() - 1));
                if(originalSubs[currentIndex] > biggestChar){
                    longestSubWithIndex.add(currentIndex,prevLongestSub + originalSubs[currentIndex]);
                    currentIndex++;
                    break;
                }else{
                    tempIndex--;
                    if(tempIndex < 0){
                        String longestStr = longestSubWithIndex.get(currentIndex - 1);
                        longestSubWithIndex.add(longestStr);
                        currentIndex++;
                        break;
                    }
                    prevLongestSub = longestSubWithIndex.get(tempIndex);
                }
            }
        }
        return longestSubWithIndex.get(subLength - 1);
    }

    public static void main(String[] args) {
        int[] originalSubs = {2,9,3,6,5,1,7};
        String longest = calculate(originalSubs);
        System.out.println(longest);
    }
}
