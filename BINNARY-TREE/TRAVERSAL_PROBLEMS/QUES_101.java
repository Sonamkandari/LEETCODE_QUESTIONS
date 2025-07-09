package BINNARY-TREE;
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

public class QUES_101{
    //recursive approach
     public boolean isSymmetric(TreeNode root) {
        if(root ==null){
            return true;
        }  
            return isMirror(root.left,root.right);
        }

        // making a isMirror methhod 
        private boolean isMirror(TreeNode t1,TreeNode t2){
            if(t1==null && t2==null) return true;
            if(t1==null||t2==null ||t1.val!=t2.val) return false;
            return isMirror(t1.left,t2.right)&& isMirror(t1.right,t2.left);
        }

        

     //  iterative Approach
    public boolean isSymmetricOLD(TreeNode root) {
        if(root==null){
            return true;

        }
      Queue<TreeNode>q=new LinkedList<>();
      q.offer(root.left); //adding left node to rootnode
      q.offer(root.right); //adding right node to rootnode

      while(!q.isEmpty()){
        TreeNode t1=q.poll(),t2=q.poll();
        if(t1==null && t2==null) continue;
        if(t1==null || t2==null || t1.val!=t2.val) return false;

        q.offer(t1.left);
        q.offer(t2.right);
        q.offer(t1.right);
        q.offer(t2.left);

      }
      return true;

  
    }

    
}