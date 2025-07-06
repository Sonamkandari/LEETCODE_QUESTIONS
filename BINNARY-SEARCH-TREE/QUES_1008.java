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

    //keeps track of current position inthe preorder array
    int index=0;
    public TreeNode bstFromPreorder(int[] preorder) {
        return build(preorder,Integer.MAX_VALUE);

        
    }

    //  creating a helper function to recursively build tree
    private TreeNode build(int[]preorder,int bound){
        //If we have used all values while creating the tree
        // or current value is out of allowed bound return null
        if(index==preorder.length||preorder[index]>bound){
            return null;
        }
        // create the current root
        TreeNode root=new TreeNode(preorder[index++]);
        // Build the left subtree with updated bound(values<root.val)
        root.left=build(preorder,root.val);

        // Build the right subtree original bound (values>root.val and values<bound)

        root.right=build(preorder,bound);
        // return the constructed node
        return root;
    }
}