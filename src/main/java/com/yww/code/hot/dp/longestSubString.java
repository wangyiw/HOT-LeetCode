package com.yww.code.hot.dp;

import java.util.Arrays;

public class longestSubString {
    /**
     * 最长递增子序列
     * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
     * 子序列 是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
     * 输入：nums = [10,9,2,5,3,7,101,18]
     * 输出：4
     */
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        int maxLength = 0;
        dp[0] = 1;
        // Arrays.fill(dp,1);
        for(int i=1;i<n;i++){
            dp[i] = 1;
            for(int j=0;j<=i;j++){
                if(nums[i]>nums[j]){
                    dp[i] = Math.max(dp[i],dp[j]+1);
                }
            }
        }
        for(int num:dp){
            maxLength = Math.max(maxLength,num);
        }
        return maxLength;
        
    }
    public static void main(String[] args) {
        longestSubString lss = new longestSubString();
        int[] nums = {10,9,2,5,3,7,101,18};
        System.out.println(lss.lengthOfLIS(nums));
    }
}
