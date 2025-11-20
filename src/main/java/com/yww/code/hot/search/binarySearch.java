package com.yww.code.hot.search;

public class binarySearch {
    /**
     * 二分查找数组插入位置
     * 输入:  nums = [1,3,5,6], target = 5
     * 输出: 2
     */
    public int searchInsert(int[] nums, int target) {
        int n = nums.length;
        int left = 0,right = n-1;
        while(left<right){
            int mid = left+ (right-left)/2;
            if(nums[mid]==target){
                return mid;
            }else if(nums[mid]>target){
                right = mid-1;
            }else{
                left = mid+1;
            }
        }
        return left;
    }
    public static void main(String[] args) {
        binarySearch bs = new binarySearch();
        int[] nums = {1,3,5,6};
        int target = 5;
        System.out.println(bs.searchInsert(nums,target));
    }
}
