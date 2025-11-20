package com.yww.code.hot.backtrack;
public class wordSearch {
    /**
     * 单词搜索
     * 给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。
     * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
     */
    public boolean exist(char[][] board, String word) {
        int h = board.length,w = board[0].length;
        boolean[][] visited =  new boolean[h][w];
        for(int i=0;i<h;i++){
            for(int j=0;j<w;j++){
                // 从每个位置开始回溯
                boolean flag = backtrack(board,word,visited,i,j,0);
                // 如果都能找到则返回true
                if(flag){
                    return true;
                }
            }
        }      
        return false;
    }
    // 写注释
    public boolean backtrack(char[][] board, String word,boolean[][] visited,int i,int j,int k){
        int h = board.length,w = board[0].length;
        // 1. 确定终止条件
        // 条件是：
        // 1.1 越界
        // 1.2 已访问
        // 1.3 字符不匹配
        if(i<0||i>=h || j<0||j>=w || visited[i][j] || board[i][j] != word.charAt(k)){
            return false;
        }
        // 2. 确定递归终止条件：遍历到最后一个字符
        if(k == word.length()-1){
            return true;
        }
        // 3. 确定遍历顺序：上下左右 i是行 j是列 k是字符
        // 确定选择一个字符
        visited[i][j] = true;
        // 为什么用||连接 因为要找到所有可能的路径，字符需要相邻
        boolean res = backtrack(board,word,visited,i+1,j,k+1)
        ||backtrack(board,word,visited,i-1,j,k+1)
        ||backtrack(board,word,visited,i,j+1,k+1)
        ||backtrack(board,word,visited,i,j-1,k+1);
        // 撤销选择
        visited[i][j] = false;
        return res;
    }
    
}
