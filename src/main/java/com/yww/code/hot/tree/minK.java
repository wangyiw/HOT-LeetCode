package com.yww.code.hot.tree;
import java.util.ArrayList;
import java.util.List;
public class minK {
    /**
     * 二叉树中第K个最小的元素
     * 给定一个二叉搜索树的根节点 root ，和一个整数 k ，请你设计一个算法查找其中第 k 小的元素（从 1 开始计数）。
     * 输入: root = [3,1,4,null,2], k = 1
     * 输出: 1
     */
    
    public int kthSmallest(TreeNode root, int k) {
        // 中序遍历
        List<Integer> res  = new ArrayList<>();
        inorder(root,res);
        return res.get(k-1);
    }
    public void inorder(TreeNode root,List<Integer> res){
        if(root==null){
            return;
        }
        inorder(root.left,res);
        res.add(root.val);
        inorder(root.right,res);
    }
    public static void main(String[] args) {
        minK mk = new minK();
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(1);
        root.right = new TreeNode(4);
        root.left.right = new TreeNode(2);
        int k = 1;
        System.out.println(mk.kthSmallest(root, k));
    }
}
