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
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {

    //create a new tree node
    // TreeNode root3 =new treeNode
    //meging function
  
        if(root1==null ) return root2;
         if(root2==null )return root1;

        //  create a new tree node with sum of values
        TreeNode merged=new TreeNode(root1.val+root2.val);
    
        merged.left=mergeTrees(root1.left,root2.left);
        merged.right=mergeTrees(root1.right,root2.right);

        return merged;
            
     }
    
}