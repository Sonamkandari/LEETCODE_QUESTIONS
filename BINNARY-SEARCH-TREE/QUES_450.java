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
    public TreeNode deleteNode(TreeNode root, int key) {
        if(root==null){
            return null;
        }
        if(key<root.val){
            root.left=deleteNode(root.left,key);
        }else if(key>root.val){
            root.right=deleteNode(root.right,key);

        }else{
            // case 1: if key is at leaf node
            if(root.left==null){
                return root.right;
            }
            if(root.right==null){
                return root.left;
            }
            // case 2: if key is in middle of the tree
            TreeNode min=findmin(root.right);
            root.val=min.val;
            root.right=deleteNode(root.right,min.val);
        }
        return root;
    }

    // helper funtion to find min
    TreeNode findmin(TreeNode node){
        while(node.left!=null){
            node=node.left;
        }
        return node;//minimum node
    }
}