package com.yww.code.hot.others;

import java.util.HashSet;

public class arrayMerge {
    /**
     * 数组去重,给一个字符串数组,去除重复字符
     * 输入:["a","b","c","a","c","a","b"]
     * 输出:["a","b","c"]
     */
    public String[] removeDuplicates(String[] nums){
        HashSet<String> set = new HashSet<>();
        for(int i=0;i<nums.length;i++){
            set.add(nums[i]);
        }
        return set.toArray(new String[set.size()]);
    }
    public static void main(String[] args){
        arrayMerge am = new arrayMerge();
        String[] nums = {"a","b","c","a","c","a","b"};
        System.out.println(am.removeDuplicates(nums));
    }
}
