package com.yww.code.hot.string;

public class minSub {
    /**
     * 最小覆盖子串
     * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
     * 
     * 注意：
     * 对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
     * 如果 s 中存在这样的子串，我们保证它是唯一的答案。
     * 输入：s = "ADOBECODEBANC", t = "ABC"
     * 输出："BANC"
     */
    public String minWindow(String s, String t) {
        // 双指针，滑动窗口
        // 滑动窗口的左右指针，左指针记录当前起点，右指针记录当前终点
        // 同时，用一个map记录t的字符及其数量，用一个计数器记录当前窗口满足的字符数量
        // 如果窗口满足t的字符数量，则更新最小窗口
        int[] map = new int[128];
        int count = t.length();
        int left = 0, right = 0;
        int minLeft = 0, minRight = 0;
        int minLen = Integer.MAX_VALUE;
        for(char c : t.toCharArray()){
            map[c]++;
        }
        while(right < s.length()){
            char c = s.charAt(right);
            if(map[c] > 0){
                map[c]--;
                count--;
            }
            right++;
            while(count == 0){
                if(right - left < minLen){
                    minLen = right - left;
                    minLeft = left;
                    minRight = right;
                }
                char d = s.charAt(left);
                if(map[d] == 0){
                    count++;
                }
                map[d]++;
                left++;
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minLeft, minRight);
        
    }
}
