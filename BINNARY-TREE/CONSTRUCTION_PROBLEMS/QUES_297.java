/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class solution {
    private static final String NULL = "X";
    private static final String SEP = ",";

    // Encodes a tree into a single string.
    public String serialize(TreeNode root) {
        if (root == null) return NULL + SEP;
        return root.val + SEP
             + serialize(root.left)
             + serialize(root.right);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        Queue<String> q = new LinkedList<>(Arrays.asList(data.split(SEP)));
        return deserializeHelper(q);
    }

    // Helper to rebuild tree recursively using preorder sequence.
    private TreeNode deserializeHelper(Queue<String> q) {
        String token = q.poll();
        if (token.equals(NULL)) return null;
        TreeNode node = new TreeNode(Integer.parseInt(token));
        node.left = deserializeHelper(q);
        node.right = deserializeHelper(q);
        return node;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));
