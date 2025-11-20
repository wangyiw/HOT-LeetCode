package com.yww.code.hot.others;

import java.util.HashMap;
import java.util.Map;

public class mergeString {
    public String mergeStringToNum(String s) {
        // 实现字符串的组合，消除重复的字符
        // 输入aaaaabbbccc
        // 输出a4b3c3
        HashMap<Character, Integer> map = new HashMap<>();
        for(int i=0;i<s.length();i++) {
            char c = s.charAt(i);
            map.put(c,map.getOrDefault(c,0)+1);
        }
        // 遍历map，拼接string
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Character,Integer> entry:map.entrySet()){
            sb.append(entry.getKey()).append(entry.getValue());
        }
        return sb.toString();
    }
    
    public static void main(String[] args){
        String s = "aaaaabbbccc";
        mergeString ms = new mergeString();
        System.out.println(ms.mergeStringToNum(s));
    }
}
