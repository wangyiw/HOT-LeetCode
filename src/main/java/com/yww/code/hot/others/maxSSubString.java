package com.yww.code.hot.others;

import java.util.Arrays;

public class maxSSubString {
    /**
     * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度
     * 子序列 是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。
     * 例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
     * @param s
     * @return
     * 输入：nums = [10,9,2,5,3,7,101,18]
     * 输出：4
     */
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        if(n == 0){
            return 0;
        }
        int[] dp = new int[n];
        int max = n+1;
        Arrays.fill(dp,max);
        dp[0] = 1;
        // 1. 确定dp数组的含义
        // dp[i]表示以nums[i]结尾的最长递增子序列的长度
        // 2. 确定dp数组的递推公式
        // dp[i] = dp[i-1] + 1
        // 3. dp数组的初始化
        // dp[0] = 1,先把所有的都设置为一个最大数
        // 4. 确定遍历顺序
        // 从左到右
        for(int i=0;i<n;i++){
            for(int j=1;j<=i;j++){
                if(nums[i]>nums[j]){
                    dp[i] = Math.max(dp[i],dp[i-1]+1);
                }
            }
        }
        return dp[n-1];
    }
    public static void main(String[] args){
        maxSSubString mss = new maxSSubString();
        int[] nums = {10,9,2,5,3,7,101,18};
        System.out.println(mss.lengthOfLIS(nums));
    }
}