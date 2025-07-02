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
    // maintain a preorder index
    int preOrderIndex=0;//because root value is here
    //creating a map
    Map<Integer,Integer>map=new HashMap<>();//inorder {key- value inorder array element and value index}
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // travesing
        for(int i=0;i<inorder.length;i++){
            map.put(inorder[i],i); // filling  the map
        }
      return  helper(preorder,0,preorder.length-1);

        
    }

    //creating a helper methode  it will create a tree
    TreeNode helper(int []preorder,int start,int end){
        if(start>end){
            return null;
        }
       int nodeValue= preorder[preOrderIndex];
       preOrderIndex++;
       TreeNode node=new TreeNode(nodeValue); // created a new node here
       if(start==end){
        return node;
       }

       int index=map.get(nodeValue);
       node.left=helper(preorder,start,index-1); //left mai jare to right mai se ek less
       node.right=helper(preorder,index+1,end); //right mai jare to left mai se 1 less

       //when left and right is recursively called
       return node;
    }
}