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
    public List<String> binaryTreePaths(TreeNode root) {

        List<String>myList=new ArrayList<>();
        if(root!=null){
            //if root is not empty we will call our function build path
            buildPath(root,myList,Integer.toString(root.val));
        }
        return myList;
    }

     private void buildPath(TreeNode node, List<String>myList,String path){
        //here we are checking weather it is leaf node or not if not return the path value
        if(node.left==null && node.right==null) myList.add(path);

         //now here we chaecking left leaf node value
        if(node.left!=null) buildPath(node.left,myList,path+"->"+node.left.val);

        // here we are checking the right leaf node value
        if(node.right!=null)buildPath(node.right,myList,path+"->"+node.right.val);
     }


}