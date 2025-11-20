package com.yww.code.hot.dp;

public class maxSubArray {
    /**
     * 给你一个整数数组 nums ，请你找出数组中乘积最大的非空连续 子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
        测试用例的答案是一个 32-位 整数。
        请注意，一个只包含一个元素的数组的乘积是这个元素的值
        输入: nums = [2,3,-2,4]
        输出: 6
     */
    public int maxProduct(int[] nums) {
        int max = nums[0];
        int min = nums[0];
        int result = nums[0];
        for(int i=1;i<nums.length;i++){
            if(nums[i] < 0){
                int temp = max;
                max = min;
                min = temp;
            }
            max = Math.max(nums[i], max * nums[i]);
            min = Math.min(nums[i], min * nums[i]);
            result = Math.max(result, max);
        }
        return result;
    }
    public static void main(String[] args) {
        maxSubArray ms = new maxSubArray();
        int[] nums = {2,3,-2,4};
        System.out.println(ms.maxProduct(nums));
    }
}
