package com.yww.code.hot.backtrack;
import java.util.ArrayList;
import java.util.List;

public class quanpailie {
    /**
     * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。
     * 你可以 按任意顺序 返回答案
     * 输入：nums = [1,2,3]
     * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
     */
    public List<List<Integer>> permute(int[] nums){
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> output = new ArrayList<>();
        backtrack(res,output,nums);
        return res;
        
    }
    public void backtrack(List<List<Integer>> res,List<Integer> output,int[] nums){
        // 回溯
        if(output.size() == nums.length){
            res.add(new ArrayList<>(output));
            return ;
        }
        for(int i = 0;i<nums.length;i++){
            // 不能有重复记录
            if(output.contains(nums[i])){
                continue;
            }
            output.add(nums[i]);
            backtrack(res,output,nums);
            output.remove(output.size()-1);
        }

      
    }
    public static void main(String[] args) {
        quanpailie qp = new quanpailie();
        int[] nums = {1,2,3};
        System.out.println(qp.permute(nums));
    }

}
