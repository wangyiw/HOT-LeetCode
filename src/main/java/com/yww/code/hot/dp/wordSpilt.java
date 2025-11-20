package com.yww.code.hot.dp;

import java.util.*;

public class wordSpilt {
    /**
     * 单词拆分
     * 给你一个字符串 s 和一个字符串数组 wordDict 请你判断 s 是否可以由 wordDict 中的单词拼接而成
     * 输入: s = "leetcode", wordDict = ["leet", "code"]
     * 输出: true
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        int n = s.length();
        boolean[] dp = new boolean[n+1];
        dp[0] = true;
        for(int i=1;i<=n;i++){
            for(int j=0;j<=i;j++){
                String word = s.substring(j,i);
                if(wordDict.contains(word)&&dp[j]){
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n];
    }
    public static void main(String[] args) {
        wordSpilt ws = new wordSpilt();
        String s = "leetcode";
        List<String> wordDict = new ArrayList<>();
        wordDict.add("leet");
        wordDict.add("code");
        System.out.println(ws.wordBreak(s, wordDict));
    }
}
