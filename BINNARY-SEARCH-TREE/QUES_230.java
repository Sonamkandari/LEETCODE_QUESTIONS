public class QUES_230 {
    private int count = 0;
    private int result = -1;

    public int kthSmallest(TreeNode root, int k) {
        count = 0;
        result = -1;
        inorder(root, k);
        return result;
    }

    private void inorder(TreeNode node, int k) {
        if (node == null || result != -1) return;  // stop early once result is set

        inorder(node.left, k);

        count++;
        if (count == k) {
            result = node.val;
            return;
        }

        inorder(node.right, k);
    }

    public static void main(String[] args) {
        // Example test
        TreeNode n1 = new TreeNode(5,
                        new TreeNode(3,
                            new TreeNode(2,
                                new TreeNode(1), null),
                            new TreeNode(4)),
                        new TreeNode(6));
        QUES_230 q = new QUES_230();
        System.out.println(q.kthSmallest(n1, 3));  // Should print 3
    }
}
