public class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return findLCA(root, p, q);
    }

    private TreeNode findLCA(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;

        if (p.val < root.val && q.val < root.val) {
            return findLCA(root.left, p, q);
        }

        if (p.val > root.val && q.val > root.val) {
            return findLCA(root.right, p, q);
        }

        return root;
    }
}
