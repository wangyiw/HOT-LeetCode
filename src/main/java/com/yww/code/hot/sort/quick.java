package com.yww.code.hot.sort;

public class quick {
    /**
     * 实现快速排序
     */
    public void quickSort(int[] nums,int l,int r){
        if(l>=r){
            return ;
        }
        int pivot = partition(nums,l,r);
        // 两个子序列分别快排
        quickSort(nums, l, pivot-1);
        quickSort(nums, pivot+1, r);
    }
    public int partition(int[] nums,int l,int r){
        // 选择基准
        int pivot = nums[l];
        int i = l,j = r;
        while(i<j){
            while(i<j && nums[j]>pivot){
                // 从后往前找第一个小于pivot
                j--;
            }
            while(i<j && nums[i]<pivot){
                i++;
            }
            swap(nums,i,j);
        }
        // 基准换到j的位置 
        swap(nums,l,j);
        return j;
    }
    public void swap(int[] nums,int i,int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    public static void main(String[] args){
        int[] test = {1,2,3,4,5,6,7,8,9,10};
        quick q  = new quick();
        q.quickSort(test, 0, test.length-1);
        for(int i=0;i<test.length;i++){
            System.out.print(test[i]+" ");
        }
    }
    
}
