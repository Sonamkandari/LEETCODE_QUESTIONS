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
    public int sumOfLeftLeaves(TreeNode root) {
        if(root==null){
            return 0;
        }
        int sum=0;
        //here we are getting only the left leaf node
        if(root.left!=null && root.left.left==null && root.left.right==null){
            sum+=root.left.val;
        }

        //recursively calling the functions for both left and  right subtree
        sum+=sumOfLeftLeaves(root.left);
        sum+=sumOfLeftLeaves(root.right);

        //returning the total sum of left leaf nodes
        return sum;
        
    }
}