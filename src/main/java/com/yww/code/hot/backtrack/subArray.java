package com.yww.code.hot.backtrack;
import java.util.ArrayList;
import java.util.List;

public class subArray {
    /**
     * 给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
     * 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
     * 输入：nums = [1,2,3]
     * 输出：[[3],[1],[2],[1,2,3],[1,3],[2,3],[1,2],[[]]
     */
    public List<List<Integer>> subsets(int[] nums){
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> output = new ArrayList<>();
        backtrack(res,output,nums,0);
        return res;
    }
    public void backtrack(List<List<Integer>> res,List<Integer> output,int[] nums,int start){
        res.add(new ArrayList<>(output));
        for(int i = start;i<nums.length;i++){
            output.add(nums[i]);
            backtrack(res, output, nums, i+1);
            output.remove(output.size()-1);
        }
    }
    public static void main(String[] args) {
        subArray sa = new subArray();
        int[] nums = {1,2,3};
        System.out.println(sa.subsets(nums));
    }
}
