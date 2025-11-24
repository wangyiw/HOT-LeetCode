package com.yww.code.hot.bestResult;

public class jumpGame {
    /**
     * 跳跃游戏，
     * 给你一个非负整数数组 nums ，你最初位于数组的 第一个下标 。数组中的每个元素代表你在该位置可以跳跃的最大长度。
     * 判断你是否能够到达最后一个下标，如果可以，返回 true ；否则，返回 false
     * 输入：nums = [2,3,1,1,4]
     * 输出：true
     */
    public boolean canJump(int[] nums) {
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > max) {
                return false;
            } else {
                max = Math.max(max, i + nums[i]);
            }
        }
        return true;
    }

    public static void main(String[] args) {
        jumpGame j = new jumpGame();
        int[] nums = { 2, 3, 1, 1, 4 };
        System.out.println(j.canJump(nums));
    }

}
