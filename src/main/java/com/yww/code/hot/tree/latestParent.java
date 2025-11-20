package com.yww.code.hot.tree;
import java.util.HashMap;
import java.util.*;
class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
class Solution{
    // 存储父节点
    Map<Integer,TreeNode> parentMap = new HashMap<>();
    // 记录访问过的节点指针
    Set<Integer> visited = new HashSet<>();
    public void dfs(TreeNode root){
        if(root == null){
            return;
        }
        parentMap.put(root.val,root);
        // 存储子节点到父节点的映射
        if(root.left != null){
            parentMap.put(root.left.val, root);
        }
        if(root.right != null){
            parentMap.put(root.right.val, root);
        }
        dfs(root.left);
        dfs(root.right);
            
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
       // 1.先dfs构建父节点映射
       dfs(root);
       // 2.从p开始向上遍历，记录所有祖先节点
       while(p != null){
        visited.add(p.val);
        p = parentMap.get(p.val);
       }
       // 3.从q开始向上遍历，找到第一个在visited中的节点
       while(q!=null){
        if(visited.contains(q.val)){
            return q;
        }
        q = parentMap.get(q.val);
       }
       return null;
    }
}
public class latestParent {
    /**
     * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
     * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个节点 p、q，
     * 最近公共祖先表示为一个节点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
     * 输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
     * 输出: 3
     * 要求两种解法:
     * 1. 递归法
     * 2. 存储父节点法
     */
    
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 递归法
        if(root==null || root == p||root == q){
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left,p,q);
        TreeNode right = lowestCommonAncestor(root.right,p,q);
        if(left==null){
            return right;
        }
        if(right==null){
            return left;
        }
        return root;
    }

    public static void main(String[] args) {
        latestParent lp = new latestParent();
        Solution solution = new Solution();
        TreeNode root = new TreeNode(3);
        TreeNode p = new TreeNode(5);
        TreeNode q = new TreeNode(1);
        System.out.println(solution.lowestCommonAncestor(root,p,q));
        System.out.println(lp.lowestCommonAncestor(root,p,q));
    }
}
