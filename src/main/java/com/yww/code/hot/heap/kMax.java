package com.yww.code.hot.heap;

public class kMax {
    /**
     * 中等
        相关标签
        premium lock icon
        相关企业
        给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
        请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
        你必须设计并实现时间复杂度为 O(n) 的算法解决此问题。
        输入: nums = [3,2,1,5,6,4], k = 2
        输出: 5
     */
    
    public int findKthLargest(int[] nums, int k) {
        // 构造最大堆,n-k个最小的取出,堆顶就是第k个最大的
        int n = nums.length;
        int headSize = nums.length;
        buildMaxHeap(nums,headSize);
        for(int i=n-1;i>n-k;i--){
            swap(nums,0,i);
            headSize--;
            maxHeapify(nums,headSize,0);
        }
        return nums[0];
    }
    public void buildMaxHeap(int[] nums,int headSize){
        // 构建最大堆
        for(int i = headSize/2-1;i>=0;i--){
            maxHeapify(nums, headSize, i);
        }
    }
    public void maxHeapify(int[] nums,int headSize,int i){
        int left = i*2+1,right = i*2+2;
        int largest = i;
        if(left<headSize && nums[left]>nums[largest]){
            largest = left;
        }
        if(right<headSize && nums[right]>nums[largest]){
            largest = right;
        }
        // 交换:当i不是最大时,然后再调整
        if(largest!=i){
            swap(nums,i,largest);
            maxHeapify(nums,headSize,largest);
        }
    }
    public void swap(int[] nums,int i,int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    public static void main(String[] args) {
        kMax km = new kMax();
        int[] nums = {3,2,1,5,6,4};
        int k = 2;
        System.out.println(km.findKthLargest(nums, k));
    }
}
