package com.yww.code.hot.backtrack;
import java.util.ArrayList;
import java.util.List;
public class partition {
    /**
     * 给你一个字符串 s，请你将 s 分割成一些 子串，使每个子串都是 回文串 。返回 s 所有可能的分割方案。
     * 输入：s = "aab"
     * 输出：["aa","b"], ["a","a","b"]
     */
    public List<List<String>> partition(String s){
        List<List<String>> res = new ArrayList<>();
        List<String> output = new ArrayList<>();
        backtrack(res,output,s,0);
        return res;
    }
    public void backtrack(List<List<String>> res,List<String> output,String s,int start){
        if(start == s.length()){
            res.add(new ArrayList<>(output));
            return ;
        }
        for(int i = start;i<s.length();i++){
            if(!isPartition(s,start,i)){
                continue;
            }
            output.add(s.substring(start,i+1));
            backtrack(res,output,s,i+1);
            output.remove(output.size()-1);
        }
    }
    public boolean isPartition(String s,int start,int i){
        int left = start, right = i;
        while(left < right){
            if(s.charAt(left++) != s.charAt(right--)){
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        partition p = new partition();
        System.out.println(p.partition("aab"));
    }
    
}
