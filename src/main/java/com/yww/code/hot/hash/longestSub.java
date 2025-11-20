package com.yww.code.hot.hash;

import java.util.HashSet;
import java.util.Set;

public class longestSub {
    /**
     * 最长连续子序列
     * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
     * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
     * 输入：nums = [100,4,200,1,3,2]
     * 输出：4
     */
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for(int num:nums){
            set.add(num);
        }
        int longest = 0;
        for(int num:set){
            if(set.contains(num-1)){
                continue;
            }
            int cur = num;
            int length = 1;
            while(set.contains(cur+1)){
                cur++;
                length++;
            }
            longest = Math.max(longest, length);
        }
        return longest;
    }
    public static void main(String[] args) {
        longestSub longestSub = new longestSub();
        int[] nums = {100,4,200,1,3,2};
        System.out.println(longestSub.longestConsecutive(nums));
    }
}
