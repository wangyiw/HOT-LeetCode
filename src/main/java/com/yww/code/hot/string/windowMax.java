
package com.yww.code.hot.string;
import java.util.Deque;
import java.util.LinkedList;

public class windowMax {
    /**
     * 实现滑动窗口最大值，给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。
     * 你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位
     * 返回 滑动窗口中的最大值
     * 输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
     * 输出：[3,3,5,5,6,7]
     */

     public int[] maxSlidingWindow(int[] nums,int k){
        // 基于单调队列实现
        // 维护队列的单调，队列的头元素是当前窗口的最大值，当前大于队头，队尾出去
        // 窗口范围[i-k+1,i],队头索引小于i-k+1则超出了窗口，队头出队
        int n = nums.length;
        int[] res = new int[n-k+1];
        Deque<Integer> deque = new LinkedList<>();
        for(int i=0;i<n;i++){
            while(!deque.isEmpty() && deque.peekFirst()<i-k+1){
                deque.pollFirst();
            }
            // 队尾元素比当前小，直接丢掉
            while(!deque.isEmpty() && nums[deque.peekLast()]<nums[i]){
                deque.pollLast();
            }
            // 添加到队列尾，并维护递减队列 头最大
            deque.offerLast(i);
            // 形成了窗口后记录结果集合
            if(i>=k-1){
                res[i-k+1] = nums[deque.peekFirst()];
            }
        }
        return res;
     }
    public static void main(String[] args) {
        windowMax wm = new windowMax();
        int[] nums = {1,3,-1,-3,5,3,6,7};
        int k = 3;
        System.out.println(wm.maxSlidingWindow(nums,k));
    }
}
