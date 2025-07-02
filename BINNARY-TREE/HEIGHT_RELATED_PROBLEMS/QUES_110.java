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

     //create a helper function for height
        public int height(TreeNode root){
            if(root==null){
                return -1;
            }
           int left=height(root.left); 
           int right=height(root.right); 
           int max=Math.max(left,right);
           return max+1;
            
        }

    public boolean isBalanced(TreeNode root) {
        if(root==null ) return true;
        int diff=Math.abs(height(root.left)-height(root.right));
        if(diff>1){
            return false;
        }
    //   recursively checking each subtyree is balanced or not
       return isBalanced(root.left)&& isBalanced(root.right);
        
    }
}