package com.yww.code.hot.others;

import java.util.ArrayList;
import java.util.List;

public class wordSpilt {
    /**
     * 给你一个字符串 s 和一个字符串列表 wordDict 作为字典。
     * 如果可以利用字典中出现的一个或多个单词拼接出 s 则返回 true。
     * 注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用
     * 输入：s = "leetcode", wordDict = ["leet", "code"]
     * 输出：true
     * 输入：s = "applepenapple", wordDict = ["apple", "pen"]
     * 输出：true
     * 输入：s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
     * 输出：false
     */
    public boolean wordBreak(String s,List<String> wordDict){
        //j  和 i都是分割点，
       // 记录起始位置，判断是否在字典中
       int n  = s.length();
       boolean[] dp = new boolean[n+1];// 标记位置是否可以到达
       dp[0] = true;
       for(int i = 1;i<=n;i++){
        for(int j=0;j<i;j++){
            // 如果 位置j能到达，从j-i的子串在字典中，则位置i可达
            if(dp[j] && wordDict.contains(s.substring(j,i))){
                dp[i] = true;
                break;
            }
        }
       }
       return dp[n];
    }
    public static void main(String[] args) {
        wordSpilt ws = new wordSpilt();
        List<String> wordDict = new ArrayList<>();
        wordDict.add("leet");
        wordDict.add("code");
        System.out.println(ws.wordBreak("leetcode", wordDict));
    }
}
