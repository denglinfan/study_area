package com.charles.leetcode;

/**
 * Created with IDEA
 * Description: Given n non-negative integers a1, a2, ..., an , where each represents a point at coordinate (i, ai). n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0). Find two lines, which together with x-axis forms a container, such that the container contains the most water.
 * User: Charles
 * Date: 2019-04-27 21:59
 */
public class MaxArea {
    public static int maxArea(int[] height){
        int leftIndex = 0;
        int rightIndex = height.length - 1;

        int maxArea = 0;
        while(leftIndex < rightIndex){
            maxArea = Math.max(Math.min(height[leftIndex],height[rightIndex])*(rightIndex - leftIndex),maxArea);
            if(height[leftIndex] > height[rightIndex]){
                rightIndex--;
            }else{
                leftIndex++;
            }
        }

        return maxArea;
    }

    public static void main(String[] args){
        int[] height = {1,8,6,2,5,4,8,3,7};
        maxArea(height);
    }
}
