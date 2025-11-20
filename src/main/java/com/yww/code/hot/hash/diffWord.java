package com.yww.code.hot.hash;

import java.util.*;
public class diffWord {
    /**
     * |
     * 代码
     * 49. 字母异位词分组
     * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
     * 输入：strs = ["eat","tea","tan","ate","nat","bat"]
     * 输出：[ ["bat"], ["nat","tan"], ["ate","eat","tea"] ]
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> res  = new ArrayList<>();
        Map<String,List<String>> map = new HashMap<>();
        for(String str:strs){
            char[] c = str.toCharArray();
            Arrays.sort(c);
            String key = new String(c);
            if(!map.containsKey(key)){
                List<String> list = new ArrayList<>();
                list.add(str);
                map.put(key,list);
            }else{
                map.get(key).add(str);
            }
        }
        for(Map.Entry<String,List<String>> entry:map.entrySet()){
            res.add(entry.getValue());
        }
        return res;
    }
    public static void main(String[] args) {
        diffWord diffWord = new diffWord();
        String[] strs = {"eat","tea","tan","ate","nat","bat"};
        System.out.println(diffWord.groupAnagrams(strs));
    }
    
}
