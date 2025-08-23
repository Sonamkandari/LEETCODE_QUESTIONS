import java.util.Random;

class Node {
    int id;
    int x, y;
    Random rand = new Random();

    public Node(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    // Random movement
    public void move(int maxStep) {
        x += rand.nextInt(2 * maxStep + 1) - maxStep; // move between -maxStep and +maxStep
        y += rand.nextInt(2 * maxStep + 1) - maxStep;
    }

    // Euclidean distance between two nodes
    public double distance(Node other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }
}

public class MANETSimulation {
    public static void main(String[] args) {
        int transmissionRange = 20;  // Transmission range
        Node nodeA = new Node(1, 0, 0);    // Node A at origin
        Node nodeB = new Node(2, 10, 0);   // Node B at (10, 0)

        System.out.println("Initial Positions:");
        System.out.println("Node A: (" + nodeA.x + ", " + nodeA.y + ")");
        System.out.println("Node B: (" + nodeB.x + ", " + nodeB.y + ")\n");

        // Simulate 5 steps of movement
        for (int step = 1; step <= 5; step++) {
            nodeB.move(5); // Move nodeB randomly
            double dist = nodeA.distance(nodeB);

            System.out.println("Step " + step + ": Node B at (" + nodeB.x + ", " + nodeB.y + 
                               ") | Distance = " + String.format("%.2f", dist));

            if (dist <= transmissionRange) {
                System.out.println(" Node B is within transmission range.\n");
            } else {
                System.out.println(" Node B is OUT of transmission range.\n");
            }
        }
    }
}
