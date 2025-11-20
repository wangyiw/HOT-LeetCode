package com.yww.code.hot.string;

import java.util.ArrayList;
import java.util.List;


public class differentWord {
    /**
     * 所有字母的异位词
     * 给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
     * 输入: s = "abab", p = "ab"
     * 输出: [0, 1, 2]
     * 解释:
     * 起始索引等于 0 的子串是 "ab", 它是 "ab" 的异位词。
     * 起始索引等于 1 的子串是 "ba", 它是 "ab" 的异位词。
     * 起始索引等于 2 的子串是 "ab", 它是 "ab" 的异位词。
     */
    public List<Integer> findAnagrams(String s, String p) {
        // 滑动窗口
        int n = s.length(),m = p.length();
        List<Integer> res = new ArrayList<>();
        if(n<m){
            return res;
        }
        int[] map = new int[26];
        int[] windowMap = new int[26];
        for(int i=0;i<m;i++){
            map[p.charAt(i)-'a']++;
            // 以ab举例 a出现一次 b出现一次 map[0]=1 map[1]=1
        }
        int left = 0,right = 0;
        while(right<n){
            char c = s.charAt(right);
            windowMap[c-'a']++;// 表示当前窗口的字符频次
            right++;
            while(right-left>=m){
                // 当窗口长度大于等于p的长度时，检查当前窗口是否是p的异位词
                if(isEquals(windowMap,map)){
                    // 如果是异位词,left加入结果集合
                    res.add(left);
                }
                // 不是就收缩窗口,左指针右移
                char d = s.charAt(left);
                // 当前窗口的字符频次减一
                windowMap[d-'a']--;
                left++;
            }

        }
        return res;

    }
    public boolean isEquals(int[] window,int[] map){
        // 判断当前窗口哦是否是异位词
        for(int i=0;i<26;i++){
            if(window[i]!=map[i]){
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        differentWord dw = new differentWord();
        System.out.println(dw.findAnagrams("abab","ab"));
    }
}
