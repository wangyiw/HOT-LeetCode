
package com.yww.code.hot.backtrack;
import java.util.ArrayList;
import java.util.List;

public class kuohao {
    /**
     * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
     * @param n
     * @return
     * 输入：n = 3
     * 输出：["((()))","(()())","(())()","()(())","()()()"]
     */
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        StringBuilder output = new StringBuilder();
        backtrack(res,output,0,0,n);
        return res;
        
    }
    public void backtrack(List<String> res,StringBuilder output,int left,int right,int n){
        if(output.length() == 2*n){
            res.add(output.toString());
            return ;
        }
        if(left<n){
            output.append('(');
            backtrack(res,output,left+1,right,n);
            output.deleteCharAt(output.length()-1);
        }
        if(right<left){
            output.append(')');
            backtrack(res,output,left,right+1,n);
            output.deleteCharAt(output.length()-1);
        }
    }
    public static void main(String[] args){
        kuohao k = new kuohao();
        System.out.println(k.generateParenthesis(3));
    }
}
    