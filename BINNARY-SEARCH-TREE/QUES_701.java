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
    public TreeNode insertIntoBST(TreeNode root, int val) {
        //base case condition
        if(root==null){
            return new TreeNode(val);
        }

        if(val<root.val){
            //inserting it into the left
            root.left=insertIntoBST(root.left,val);//this is taking place recursively
        }else{
            root.right=insertIntoBST(root.right,val);//this is also taking place recursively
        }
        return root;
    }
}