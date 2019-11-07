package com.charles.geek;

/**
 * Created with IDEA
 * Description: 求最长公共子序列，动态规划应用
 * example:
 *  X = {a, Q, 1, 1}; Y = {a, 1, 1, d, f}那么，{a, 1, 1}是X和Y的最长公共子序列
 * User: Charles
 * Date: 2019-09-16 14:24
 */
public class LongestCommonSubsequence {

    public static void lcs(String[] a, String[] b,Integer[][] result){
        for (int i = 1; i <= a.length; i++) {
            for (int j = 1; j <= b.length; j++) {
                if(a[i - 1] == b[j - 1]){
                    result[i][j] = result[i - 1][j - 1] + 1;
                }else{
                    result[i][j]  = max(result[i - 1][j],result[i][j - 1]);
                }
            }
        }
    }

    public static Integer max(Integer upValue, Integer rightValue){
        if(upValue > rightValue){
            return upValue;
        }else{
            return rightValue;
        }
    }

    public static void printSequence(Integer[][] result, String[] rowData, int rowIndex, int columnIndex){
        //TODO
        String sb = "";
        Integer currentValue = result[rowIndex][columnIndex];
        while(rowIndex > 0 && columnIndex > 0){

        }
    }

    public static void main(String[] args){
        String[] a = {"A", "B", "C", "B", "D", "A", "B"};
        String[] b = {"B", "D", "C", "A", "B", "A"};

        Integer[][] result = new Integer[a.length + 1][b.length + 1];
        for (int i = 0; i <= a.length; i++) {
            result[i][0] = 0;
        }
        for (int i = 0; i <= b.length; i++) {
            result[0][i] = 0;
        }

        lcs(a,b,result);

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                System.out.print(result[i][j] + "   ");
            }
            System.out.println("");
        }
    }
}
