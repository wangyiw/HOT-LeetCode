package com.yww.code.hot.hash;

import java.util.HashMap;
import java.util.Map;

public class plus {
    /**
     * 两数之和
     * 输入：nums = [2,7,11,15], target = 9
     * 输出：[0,1]
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<>();
        for(int i = 0;i<nums.length;i++){
            if(map.containsKey(target-nums[i])){
                return new int[] {i,map.get(target-nums[i])};
            }
            map.put(nums[i], i);
        }
        return new int[] {};
    }
    public static void main(String[] args) {
        plus plus = new plus();
        int[] nums = {2,7,11,15};
        int target = 9;
        int[] result = plus.twoSum(nums,target);
        System.out.println(result[0]+" "+result[1]);
    }
    
}
