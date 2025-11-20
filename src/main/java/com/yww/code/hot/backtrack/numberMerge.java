package com.yww.code.hot.backtrack;
import java.util.ArrayList;
import java.util.List;

public class numberMerge {
    /**
     * 电话号码组合
     * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
     * 答案可以按 任意顺序 返回。
     * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
     * 2对应abc 3对应def 4对应ghi 5对应jkl 6对应mno 7对应pqrs 8对应tuv 9对应wxyz
     * 输入：digits = "23"
     * 输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
     */
    public List<String> letterCombinations(String digits){
        List<String> res = new ArrayList<>();
        StringBuilder output = new StringBuilder();
        String[] alpha = {"","","abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};

        backtrack(res,output,digits,0,alpha);
        return res;
    }
    public void backtrack(List<String> res,StringBuilder output,String digits,int start,String[] alpha){
        // 1. 确定终止条件
        if(output.length() == digits.length() ){
            res.add(output.toString());
            return ;
        }
        if(digits.length() == 0){
            return ;
        }
        String s = alpha[digits.charAt(start)-'0'];
        // 2.遍历
        for(int i = 0;i<s.length();i++){
            output.append(s.charAt(i));
            backtrack(res,output,digits,start+1,alpha);
            output.deleteCharAt(output.length()-1);
        }
    }
    
    public static void main(String[] args) {
        numberMerge nm = new numberMerge();
        System.out.println(nm.letterCombinations("23"));
    }
    
}
