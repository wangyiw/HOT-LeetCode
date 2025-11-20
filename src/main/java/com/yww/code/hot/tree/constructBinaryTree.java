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
public class constructBinaryTree {
    /**
     * // 通过先序遍历和中序遍历构造二叉树
     * 给定两个整数数组 preorder 和 inorder ，其中 preorder 是二叉树的先序遍历， inorder 是同一棵树的中序遍历，请构造二叉树并返回其根节点。
     * 输入: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
     * 输出: [3,9,20,null,null,15,7]
     */
    Map<Integer,Integer> map = new HashMap<>();
    // key 元素的值node.val value 在中序序列中的位置
    public TreeNode mybuildTree(int[] preorder, int[] inorder,int pre_left,int pre_right,int in_left,int in_right) {
        // 递归边界条件：任一区间为空都返回null
        if(pre_left > pre_right || in_left > in_right){
            return null;
        }
        // 1.确定根节点,前序的左边界
        int root_val = preorder[pre_left];
        // 2.中序中找到根节点位置
        int root_index = map.get(root_val);
        // 3.构建根节点
        TreeNode root = new TreeNode(root_val);
        // 前序遍历[根,左,右] 中序遍历[左,根,右]
        // 4.递归构建左子树 前[pre_left+1,pre_left+root_index-in_left] 中[in_left,root_index-1]
        root.left = mybuildTree(preorder,inorder,pre_left+1,pre_left+root_index-in_left,in_left,root_index-1);
        // 5.递归构建右子树 前[pre_left+root_index-in_left+1,pre_right] 中[root_index+1,in_right]
        root.right = mybuildTree(preorder,inorder,pre_left+root_index-in_left+1,pre_right,root_index+1,in_right);
        return root;
    }
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        for(int i=0;i<inorder.length;i++){
            map.put(inorder[i],i);
        }
        return mybuildTree(preorder,inorder,0,preorder.length-1,0,inorder.length-1);
    }
    public static void main(String[] args) {
        constructBinaryTree cbt = new constructBinaryTree();
        int[] preorder = {3,9,20,15,7};
        int[] inorder = {9,3,15,20,7};
        TreeNode root = cbt.buildTree(preorder,inorder);
        System.out.println(root.val);
    }
}
