package com.yww.code.hot.tree;

import java.util.ArrayList;
import java.util.List;

class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
public class treeToLinked {
    /**
     * 给你二叉树的根结点 root ，请你将它展开为一个单链表：
        展开后的单链表应该同样使用 TreeNode ，其中 right 子指针指向链表中下一个结点，而左子指针始终为 null 。
        展开后的单链表应该与二叉树 先序遍历 顺序相同。
        输入：root = [1,2,5,3,4,null,6]
        输出：[1,null,2,null,3,null,4,null,5,null,6]
     */
    
    public void flatten(TreeNode root) {
        if(root==null){
            return;
        }
        List<TreeNode> res = new ArrayList<>();
        preOrder(root,res);
        for(int i=1;i<res.size();i++){
            TreeNode pre = res.get(i-1), cur = res.get(i);
            pre.left = null;
            pre.right = cur;
        }
    }
    public void preOrder(TreeNode root,List<TreeNode> res){
        if(root==null){
            return;
        }
        res.add(root);
        if(root.left!=null){
            preOrder(root.left,res);
        }
        if(root.right!=null){
            preOrder(root.right,res);
        }
    }
    // 辅助方法: 打印展开后的链表
    public void printFlattenedTree(TreeNode root) {
        System.out.print("展开后的链表: ");
        TreeNode current = root;
        while(current != null) {
            System.out.print(current.val);
            if(current.right != null) {
                System.out.print(" -> ");
            }
            current = current.right;
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        treeToLinked t = new treeToLinked();
        // 构建测试树: [1,2,5,3,4,null,6]
        //       1
        //      / \
        //     2   5
        //    / \   \
        //   3   4   6
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(6);
        
        System.out.println("原始树的先序遍历应该是: 1 -> 2 -> 3 -> 4 -> 5 -> 6");
        t.flatten(root);
        t.printFlattenedTree(root);
    }

}

