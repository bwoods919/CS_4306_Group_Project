import javax.swing.*;
import java.awt.*;
import java.util.*;

public class NodeGraph extends JFrame {

    private JPanel panel;

    public NodeGraph(int[][] adjacencyMatrix) {
        setTitle("Node Graph");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new GraphPanel(adjacencyMatrix);
        add(panel);

        setSize(500, 500);

        setVisible(true);
    }

    public void bfs(int startNode, JPanel panel) {
        GraphPanel graphPanel = (GraphPanel) panel;
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startNode);

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            visited.add(currentNode);
            System.out.print(Character.toString((char)(currentNode+65)) + " ");

            for (int i = 0; i < graphPanel.numNodes; i++) {
                if (graphPanel.adjacencyMatrix[currentNode][i] == 1 && !visited.contains(i)) {
                    queue.add(i);
                    visited.add(i);
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] adjacencyMatrix = {
                {0, 1, 0, 0, 1},
                {1, 0, 1, 0, 1},
                {0, 1, 0, 1, 0},
                {0, 0, 1, 0, 1},
                {1, 1, 0, 1, 0}
        };
        NodeGraph graph = new NodeGraph(adjacencyMatrix);
        graph.bfs(0, graph.panel);
    }

    private class GraphPanel extends JPanel {

        private final int NODE_SIZE = 30;

        private int[][] adjacencyMatrix;
        private int numNodes;
        private Map<Integer, Point> nodePositions;

        public GraphPanel(int[][] adjacencyMatrix) {
            this.adjacencyMatrix = adjacencyMatrix;
            this.numNodes = adjacencyMatrix.length;
            this.nodePositions = new HashMap<>();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Random random = new Random();
            for (int i = 0; i < numNodes; i++) {
                int x = random.nextInt(getWidth() - NODE_SIZE);
                int y = random.nextInt(getHeight() - NODE_SIZE);
                nodePositions.put(i, new Point(x, y));
            }

            for (int i = 0; i < numNodes; i++) {
                for (int j = i + 1; j < numNodes; j++) {
                    if (adjacencyMatrix[i][j] == 1) {
                        Point node1 = nodePositions.get(i);
                        Point node2 = nodePositions.get(j);

                        g.drawLine(node1.x + NODE_SIZE / 2, node1.y + NODE_SIZE / 2,
                                node2.x + NODE_SIZE / 2, node2.y + NODE_SIZE / 2);
                    }
                }
            }

            for (int i = 0; i < numNodes; i++) {
                Point node = nodePositions.get(i);
                g.fillOval(node.x, node.y, NODE_SIZE, NODE_SIZE);
                g.setColor(Color.WHITE);
                g.drawString(Character.toString((char)(i+65)), node.x + NODE_SIZE / 2 - 4, node.y + NODE_SIZE / 2 + 4);
                g.setColor(Color.BLACK);
            }
        }
    }
}

