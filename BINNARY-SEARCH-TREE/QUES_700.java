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
    public TreeNode searchBST(TreeNode root, int val) {
        //if root value is null return null
        if(root==null){
            return null;
        }
        // if root value is equal to the target value return root
        if(root.val==val){
            return root;
        }
        // keeping the properties of Binary search tree in mind 
        // if target value is less than root value search in the left direction recursively
        if(val<root.val){
            return searchBST(root.left,val);
        // else if target value is greater than the root value search in the right direction recursively
        }else{
            return searchBST(root.right,val);
        }
        
    }
}