package com.charles.leetcode;

/**
 * Created with IDEA
 * Description: Given a string s, find the longest palindromic substring in s.
 * You may assume that the maximum length of s is 1000.
 * User: Charles
 * Date: 2019-05-25 16:12
 */
public class LongestPalindromicSubstring {

    /**
     * Example 1:
     *      Input: "babad"
     *      Output: "bab"
     *      Note: "aba" is also a valid answer.
     * Example 2:
     *      Input: "cbbd"
     *      Output: "bb"
     * 最长公共子串
     */
    public static String longestPalindromeOwn(String s){
        if(s == null || s.length() <= 1){
            return s;
        }
        int totalSize = s.length();
        String original = s;
        String revert = new StringBuilder(s).reverse().toString();
        int[] arr = new int[totalSize];
        int maxLen = 0;
        int maxEnd = 0;

        for(int i = 0; i < totalSize; i++){
            for(int j = totalSize - 1; j >= 0;j--){
                if(original.charAt(i) == revert.charAt(j)){
                    if(i == 0 || j == 0){
                        arr[j] = 1;
                    }else{
                        arr[j] = arr[j - 1] + 1;
                    }
                }else{
                    arr[j] = 0;
                }
                if(arr[j] > maxLen){
                    int beforeRev = totalSize - 1 - j;
                    if(beforeRev + arr[j] - 1 == i){
                        maxLen = arr[j];
                        maxEnd = i;
                    }
                }
            }
        }
        return s.substring(maxEnd - maxLen + 1, maxEnd + 1);
    }

    public String longestPalindrome(String s) {
        return solution4(s);
    }
    public String solution1(String s) {
        String temp = "";
        for (int i = 0; i < s.length(); i++) {
            int k = 1;
            while (i - k >= 0 && i + k < s.length() && s.charAt(i-k) == s.charAt(i+k)){
                k++;
            }
            k--;
            if (temp.length() < 2 * k + 1) {
                temp = s.substring(i-k, i+k+1);
            }
            k = 0;
            while (i - k >= 0 && i + k + 1 < s.length() && s.charAt(i-k) == s.charAt(i+k+1)){
                k++;
            }

            k--;
            if (temp.length() < 2 * k + 2) {
                temp = s.substring(i-k, i+k+2);
            }
        }
        return temp;
    }
    public String solution2(String s) { // 分治
        int i = 0;
        while (i < s.length() - i - 1 && s.charAt(i) == s.charAt(s.length()-i-1)){
            i++;
        }
        if (i >= s.length() - i -1) {

            return s;
        }
        String temp1 = solution2(s.substring(0, s.length() - 1));
        String temp2 = solution2(s.substring(1, s.length()));
        return temp1.length() > temp2.length() ? temp1 : temp2;
    }
    public String solution3(String s) {  // 动态规划
        int n = s.length();
        if (n == 0 || n == 1) {
            return s;
        }
        int start = 0;
        int end = 0;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
            if (i + 1 < n && s.charAt(i) == s.charAt(i+1)) {
                dp[i][i+1] = 1;
                start = i;
                end = i+1;
            }
        }
        for (int i = 3; i <= n; i++) {
            for (int j = 0; j + i - 1 < n; j++) {
                if (s.charAt(j) == s.charAt(j + i - 1) && dp[j + 1][j + i - 2] == 1) {
                    dp[j][j + i - 1] = 1;
                    start = j;
                    end = j + i - 1;
                }
            }
        }
        return s.substring(start, end+1);
    }
    public String solution4(String s) {  // Manacher
        int n = s.length();
        if (n == 0){
            return "";
        }
        char[] temp = new char[2 * n + 3];
        temp[0] = '<';
        int[] p = new int[2 * n + 3];
        for (int i = 0; i < n; i++) {
            temp[2*i+1] = '#';
            temp[2*i+2] = s.charAt(i);
        }
        temp[2*n+1] = '#';
        temp[2*n+2] = '>';
        int id = 0;
        int mx = 0;
        int max = 0;
        int from = 0;
        int to = 0;
        for (int i = 1; i < 2*n + 2; i++) {
            if (i < mx) {
                p[i] = Math.min(p[2*id-i], mx - i);
            } else {
                p[i] = 1;
            }
            while (temp[i-p[i]] == temp[i+p[i]]) {
                p[i]++;
            }
            if (i+p[i] > mx) {
                id = i;
                mx = i + p[i];
                if (p[i] > max) {
                    max = p[i];
                    from = (mx - 2 * p[id] - 2) / 2 + 1;
                    to = (mx - 2) / 2;
                }
            }
        }
        return s.substring(from, to);
    }

    /**
     * 中心向两边扩散
     * @param s
     * @return
     */
    public static String longestPalindromeCenter(String s) {
        if(s == null || s.length() < 1){
            return "";
        }
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    private static int expandAroundCenter(String s, int left, int right) {
        int L = left, R = right;
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        return R - L - 1;
    }

    public static void main(String[] args){
        String s = longestPalindromeCenter("babab");
        System.out.println(s);
    }
}
