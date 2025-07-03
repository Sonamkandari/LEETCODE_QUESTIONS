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
     // create a helper function first
        boolean helper(TreeNode root,long min,long max){
            if(root==null){
                return true;
            }
            if(root.val<=min||root.val>=max){
                return false;
            }
            return helper(root.left,min,root.val)&& helper(root.right,root.val,max);
        }
    public boolean isValidBST(TreeNode root) {

        // similar to  insert binary search tree only difference is while inserting i shuld validate the left and write values
     return helper(root ,Long.MIN_VALUE,Long.MAX_VALUE);
       
        
    }
}