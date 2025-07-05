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

     // declaring the variables
    private int currVal;
    private int currCount = 0;
    private int maxCount = 0;
    private int modeCount = 0;
    private int[] modes;
        // First traversal: find the highest frequency (maxCount) and number of modes (modeCount)
        inorder(root);

        // Create an array to store the result
        modes = new int[modeCount];

        // Reset counters before second traversal
        modeCount = 0;
        currCount = 0;

        // Second traversal: fill the modes array with values that match maxCount
        inorder(root);

        return modes;
    }

    // This method updates counters for each value during traversal
    private void handleValue(int val) {
        // If the value changes, reset the count
        if (val != currVal) {
            currVal = val;
            currCount = 0;
        }

        // Increase count for the current value
        currCount++;

        // If this is a new max, reset mode count and update maxCount
        if (currCount > maxCount) {
            maxCount = currCount;
            modeCount = 1;
        }
        // If value frequency equals max, store it in modes array
        else if (currCount == maxCount) {
            if (modes != null) {
                modes[modeCount] = currVal;
            }
            modeCount++;
        }
    }

    // Inorder traversal to visit BST nodes in sorted order
    private void inorder(TreeNode root) {
        if (root == null) return;
        inorder(root.left);            // Visit left subtree
        handleValue(root.val);         // Process current node
        inorder(root.right);           // Visit right subtree
    }
}
