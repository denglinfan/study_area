package com.charles.leetcode;

import java.util.Arrays;

/**
 * Created with IDEA
 * Description: Given an array nums of n integers and an integer target, find three integers in nums such that the sum is closest to target.
 * Return the sum of the three integers. You may assume that each input would have exactly one solution.
 *
 *  Given array nums = [-1, 2, 1, -4], and target = 1.
    The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
 * User: Charles
 * Date: 2019-05-21 20:37
 */
public class ThreeSumClosest {

    public static int threeSumClosest(int[] nums, int target) {
        if(nums.length == 3){
            return nums[0] + nums[1] + nums[2];
        }
        Arrays.sort(nums);
        int closestDiff = Integer.MAX_VALUE;
        int numsLength = nums.length;
        int result = 0;

        for(int i = 0; i < numsLength - 2; i++){
            int start = i + 1;
            int end = numsLength - 1;
            while (start < end){
                int sum = nums[start] + nums[end] + nums[i];
                int diff = Math.abs(target - sum);
                if(diff < closestDiff){
                    closestDiff = diff;
                    result = sum;
                }else if(diff == 0){
                    return target;
                }else if(sum > target){
                    end--;
                }else{
                    start++;
                }
            }
        }

        return result;
    }

    public static void main(String[] args){
        int sum = threeSumClosest(new int[]{1,1,1,0},100);
        System.out.println(sum);
    }
}
