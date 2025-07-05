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
     private TreeNode current;
    public TreeNode increasingBST(TreeNode root) {
         TreeNode dummy=new TreeNode(0);//dummy node to start the tree
         current=dummy;
         inorder(root);
         return dummy.right;
        
    }

    // inorder traversal first
    private void inorder(TreeNode root){
        if(root==null){
        return;
     } 
      inorder(root.left);

    //   visit Node
    root.left=null;  //set left to null 
    current.right=root;  //atach node to the right of current node
    current=root;
    inorder(root.right);
      
    }

}