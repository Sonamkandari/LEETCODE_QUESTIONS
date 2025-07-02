/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
     //create a helper funtion for height and diameter
     int diameterOfTree=0;
     public int helper(TreeNode root){
        if(root==null){
            return 0;
        }

        int left=helper(root.left);
        int right=helper(root.right);
        int diameterOfNode=left+right;
        diameterOfTree=Math.max(diameterOfNode,diameterOfTree);
        return 1+Math.max(left,right);
     }
    public int diameterOfBinaryTree(TreeNode root) {
        helper(root);
        return  diameterOfTree;
    

    }
}