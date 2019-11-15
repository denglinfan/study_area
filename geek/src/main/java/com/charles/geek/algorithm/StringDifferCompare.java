package com.charles.geek.algorithm;

/**
 * Created with IDEA
 * Description: 字符串相似度计算
 * User: Charles
 * Date: 2019-09-30 11:04
 */
public class StringDifferCompare {

    private static int minDist = Integer.MAX_VALUE;

    /**
     * 利用回溯算法计算字符串的相似程度(优化：可将每次计算的缓存下来，若后面再遇到相同的，则可直接使用)
     */
    public static void lookBackUpon(char[] a, int aIndex, char[] b, int bIndex, int edist){
        if(a.length == aIndex || b.length == bIndex){
            if(aIndex < a.length){
                edist += (a.length - aIndex);
            }
            if(bIndex < b.length){
                edist += (b.length - bIndex);
            }
            if(edist < minDist){
                minDist = edist;
            }
            return ;
        }

        if(a[aIndex] == b[bIndex]){
            lookBackUpon(a,aIndex+1,b,bIndex+1,edist);
        }else{
            //删除a[aIndex]或者在b[bIndex]前面添加一个和a[aIndex]相同的字符
            lookBackUpon(a,aIndex+1,b,bIndex,edist+1);
            //删除b[bIndex]或者在a[aIndex]前面添加一个和b[bIndex]相同的字符
            lookBackUpon(a,aIndex,b,bIndex +1,edist+1);
            //将a[aIndex]替换成b[bIndex]，或者将b[bIndex]替换成a[aIndex]
            lookBackUpon(a,aIndex+1,b,bIndex+1,edist+1);
        }
    }

    /**
     * 利用动态规划的思想解决回溯算法中多次重复计算的问题
     * @param a
     * @param b
     * @param edists
     */
    public static void dynamicProgramming(char[] a, char[] b, int[][] edists){
        int aLength = a.length;
        int bLength = b.length;
        for (int i = 0; i < aLength; i++) {
            if(a[i] == b[0]){
                edists[i][0] = i;
            }else if(i != 0){
                edists[i][0] = edists[i-1][0] + 1;
            }else{
                edists[i][0] = 1;
            }
        }

        for (int i = 0; i < bLength; i++) {
            if(a[0] == b[i]){
                edists[0][i] = i;
            }else if(i != 0){
                edists[0][i] = edists[0][i - 1] + 1;
            }else{
                edists[0][i] = 1;
            }
        }

        for (int i = 1; i < aLength; i++) {
            for (int j = 1; j < bLength; j++) {
                if(a[i] == b[j]){
                    edists[i][j] = min(edists[i-1][j]+1,edists[i][j-1]+1,edists[i-1][j-1]);
                }else{
                    edists[i][j] = min(edists[i-1][j]+1,edists[i][j-1]+1,edists[i-1][j-1]+1);
                }
            }
        }
    }

    public static int min(int leftNo, int upNo, int thirdNo){
        int temp = leftNo;
        if(temp > upNo){
            temp = upNo;
        }
        if(temp > thirdNo){
            temp = thirdNo;
        }
        return temp;
    }

    public static void main(String[] args){
        char[] a = "mitcmu".toCharArray();
        char[] b = "mtacnu".toCharArray();

        lookBackUpon(a, a.length, b, b.length,0);
        System.out.println("两个字符串的相似长度为：" + minDist);

        int[][] edists = new int[a.length][b.length];
        dynamicProgramming(a,b,edists);
        System.out.println("两个字符串的差异度为：" + edists[a.length - 1][b.length - 1]);
    }
}
