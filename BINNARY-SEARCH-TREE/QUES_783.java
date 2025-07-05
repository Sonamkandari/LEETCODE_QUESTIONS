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

     TreeNode prev=null;  //to store previousw node in inorder
     int minDiff=Integer.MAX_VALUE;
     public int minDiffInBST(TreeNode root) {
        inorder(root);
        return minDiff;
        
    }

    //in order traversal for traversing each node
    private void inorder(TreeNode root){
        if(root==null){
            return;
        }
       //left root right  
     inorder(root.left);
     if(prev!=null){
        int diff=root.val-prev.val;
        minDiff=Math.min(minDiff,diff);//updating minimum difference
     }
     prev=root;//update previous node
     inorder(root.right);//go to right

    }

}