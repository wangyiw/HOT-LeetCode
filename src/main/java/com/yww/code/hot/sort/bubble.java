package com.yww.code.hot.sort;

public class bubble {
    // 实现冒泡排序
    public void bubbleSort(int[] nums){
        int n = nums.length;
        for(int i = 0;i<n;i++){
            for(int j = 0;j<n-1;j++){
                if(nums[i]>nums[j]){
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                }
            }
        }
    }
    public static void main(String[] args) {
        bubble b = new bubble();
        int[] nums = {1,2,3,4,5,6,7,8,9,10};
        b.bubbleSort(nums);
        for(int i:nums){
            System.out.print(i+" ");
        }
    }
}
