package com.yww.code.hot.dp;

public class squartNum {
    /**
     * 给你一个整数 n ，返回 和为 n 的完全平方数的最少数量 。
     * 完全平方数 是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。例如，1、4、9 和 16 都是完全平方数，而 3 和 11 不是。
     * 输入: n = 12
     * 输出: 3 
     * 解释: 12 = 4 + 4 + 4 
     */
    public int numSquares(int n){
        // i-j*j+j*j = i 状态转移
        int[] dp = new int[n+1];
        dp[0] = 0;
        for(int i=1;i<=n;i++){
            dp[i] = i;
            for(int j=1;j*j<=i;j++){
                dp[i] = Math.min(dp[i],dp[i-j*j]+1);
            }
        }
        return dp[n];
    }
    public static void main(String[] args) {
        squartNum sn = new squartNum();
        System.out.println(sn.numSquares(12));
    }
}
