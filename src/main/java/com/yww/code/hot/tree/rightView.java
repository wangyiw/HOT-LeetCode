package com.yww.code.hot.tree;

import java.util.*;
class TreeNode{
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x){
        val = x;
    }
}   
public class rightView {
    /**
     * 给定一个二叉树的 根节点 root，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
     * 输入: [1,2,3,null,5,null,4]
     * 输出: [1,3,4]
     */
    public List<Integer> rightSideView(TreeNode root) {
        // 广度优先遍历,最简实现
        List<Integer> result = new ArrayList<>();
        if(root == null){
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);// 根节点进去queue
        while(!queue.isEmpty()){
            int size = queue.size();
            for(int i=0;i<size;i++){
                TreeNode node = queue.poll();  // 从队列中取出节点
                if(i == size-1){
                    result.add(node.val);  // 每层最后一个节点就是右视图
                }
                if(node.left!=null){
                    queue.offer(node.left);
                }
                if(node.right!=null){
                    queue.offer(node.right);
                }
            }
        }
        return result;

    }
    public static void main(String[] args) {
        rightView rv = new rightView();
        // 构建测试树: [1,2,3,null,5,null,4]
        //       1
        //      / \
        //     2   3
        //      \   \
        //       5   4
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(4);
        
        List<Integer> result = rv.rightSideView(root);
        System.out.println("右视图结果: " + result);  // 期望输出: [1, 3, 4]
    }
}
