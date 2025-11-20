package com.yww.code.hot.string;

import java.util.HashMap;
import java.util.*;

public class longestSubString {
    /**
     * 无重复最长子串
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长 子串 的长度。
     * 输入: s = "abcabcbb"
     * 输出: 3 
     * 解释: 因为无重复字符的最长子串是 "abc"，所以答案是 3。
     */
    public int lengthOfLongestSubstring(String s) {
        // 滑动窗口
        int n = s.length();
        int l =0,r = 0;
        int res = 0;
        // 用map记录窗口中的字符
        Map<Character,Integer> map = new HashMap<>();
        // key 字符 value 索引
        // Set<Character> set = new HashSet<>();
        while(l<n){
            // 判断边界 符合条件 扩展窗口
            if(r<n && !map.containsKey(s.charAt(r))){
                map.put(s.charAt(r),r);
                r++;
                res = Math.max(res,r-l);
            }else{
                // 不符合条件，收缩窗口
                map.remove(s.charAt(l));
                l++;
            }
            // if(r<n&& !set.contains(s.charAt(r))){
            //     set.add(s.charAt(r));
            //     r++;
            //     res = Math.max(res,r-l);
            // }else{
            //     set.remove(s.charAt(l));
            //     l++;
            // }
        }
        return res;
    
        
    }
    public static void main(String[] args) {
        longestSubString lss = new longestSubString();
        System.out.println(lss.lengthOfLongestSubstring("abcabcbb"));
    }
}
