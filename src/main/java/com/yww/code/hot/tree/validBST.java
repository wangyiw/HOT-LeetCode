package com.yww.code.hot.tree;

public class validBST {
    /**
     * 验证二叉搜索树
     * 给你一个二叉树的根节点 root ，判断其是否是一个有效的二叉搜索树。
        有效 二叉搜索树定义如下：

        节点的左子树只包含 严格小于 当前节点的数。
        节点的右子树只包含 严格大于 当前节点的数。
        所有左子树和右子树自身必须也是二叉搜索树。
        输入: root = [2,1,3]
        输出: true
     */
    public boolean isValidBST(TreeNode root) {
        return isValid(root,Long.MIN_VALUE,Long.MAX_VALUE);
    }
    public boolean isValid(TreeNode root,long lower,long upper){
        if(root == null){
            return true;
        }
        if(root.val<=lower || root.val>=upper){
            return false;
        }
        // 左子树的值 < root.val 右子树的值 > root.val
        return isValid(root.left,lower,root.val) && isValid(root.right,root.val,upper);

    }
    public static void main(String[] args) {
        validBST vb = new validBST();
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);
        System.out.println(vb.isValidBST(root));
    }
}
