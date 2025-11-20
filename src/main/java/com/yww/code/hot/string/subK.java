package com.yww.code.hot.string;

import java.util.HashMap;
import java.util.Map;

public class subK {
    /**
     * 给你一个整数数组 nums 和一个整数 k ，请你统计并返回 该数组中和为 k 的子数组的个数 。
     * 子数组是数组中元素的连续非空序列。
     * 输入：nums = [1,1,1], k = 2
     * 输出：2
     */
    // 暴力法
    public int subarraySum(int[] nums, int k) {
        int n  = nums.length;
        int count = 0;
        for(int i=0;i<n;i++){
            int num = 0;
            for(int j = i;j>=0;j--){
                num+=nums[j];
                if(num == k){
                    count++;
                }
            }
        }
        return count;
    }
    // 前缀和+哈希表
    public int subarraySum2(int[] nums, int k){
        // 当前和-前缀和 = k
        int n = nums.length;
        int count = 0;
        int sum = 0;
        /// key 前缀和 value 出现次数
        Map<Integer,Integer> map = new HashMap<>();
        // 添加初始化值，第一个数前缀和为0
        map.put(0,1);
        for(int i=0;i<n;i++){
            sum+=nums[i];
            if(map.containsKey(sum-k)){
                count+=map.get(sum-k);
            }
            map.put(sum,map.getOrDefault(sum,0)+1);
        }
        return count;
    }
    public static void main(String[] args){
        subK sk = new subK();
        int[] nums = {1,1,1};
        int k = 2;
        System.out.println(sk.subarraySum2(nums,k));
    }
}
