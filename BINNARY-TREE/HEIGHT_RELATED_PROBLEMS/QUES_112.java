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
    public boolean hasPathSum(TreeNode root, int targetSum) {
        
        if(root==null ){
            return false;
      }

         Queue<TreeNode>queue=new LinkedList<>();
         Queue<Integer>PathSum=new LinkedList<>();

        queue.offer(root);
        PathSum.offer(root.val);

        while(!queue.isEmpty()){
            TreeNode currentNode=queue.poll();
            int currentSum=PathSum.poll();
         
            if( currentNode.left==null&&currentNode.right==null&&currentSum==targetSum){
                return true;
            }

            // if currentNode.left node is not null  add it in the queue and add to the pathsum
            if(currentNode.left!=null){
                queue.offer(currentNode.left);
                PathSum.offer(currentSum+currentNode.left.val);
            }

           // if currentNode.right node is not null  add it in the queue and add to the pathsum
           if(currentNode.right!=null){
                queue.offer(currentNode.right);
                PathSum.offer(currentSum+currentNode.right.val);
           }
             

        }
         
         return false;

        
        
    }
}
