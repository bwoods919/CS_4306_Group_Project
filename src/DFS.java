import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class DFS extends JFrame {

    private JPanel panel;
    private Set<Integer> visited;
    private Stack<Integer> stack;
    private GraphPanel graphPanel;
    private int currentNode;

    private final int NODE_SIZE = 30;

    private int[][] adjacencyMatrix;
    private int numNodes;
    private Map<Integer, Point> nodePositions;


    public DFS(int[][] adjacencyMatrix) {
        setTitle("Node Graph");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new GraphPanel(adjacencyMatrix);
        add(panel);

        setSize(500, 500);

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stack.isEmpty()) {
                    System.out.println("DFS traversal completed");
                    return;
                }

                currentNode = stack.pop();
                visited.add(currentNode);
                System.out.print(Character.toString((char) (currentNode + 65)) + " ");

                for (int i = graphPanel.numNodes - 1; i >= 0; i--) {
                    if (graphPanel.adjacencyMatrix[currentNode][i] == 1 && !visited.contains(i)) {
                        stack.push(i);
                        visited.add(i);
                    }
                }

                graphPanel.currentNode = currentNode;
                panel.repaint();
            }
        });
        add(nextButton, BorderLayout.SOUTH);

        setVisible(true);

        graphPanel = (GraphPanel) panel;
        visited = new HashSet<>();
        stack = new Stack<>();
        stack.push(0); // Starting node
    }

    public static void main(String[] args) {
        int[][] adjacencyMatrix = {
                {0, 1, 0, 0, 1},
                {1, 0, 1, 0, 1},
                {0, 1, 0, 1, 0},
                {0, 0, 1, 0, 1},
                {1, 1, 0, 1, 0}
        };
        DFS graph = new DFS(adjacencyMatrix);
    }

    private class GraphPanel extends JPanel {

        private final int NODE_SIZE = 30;

        private int[][] adjacencyMatrix;
        private int numNodes;
        private Map<Integer, Point> nodePositions;
        private int currentNode;
        private boolean positionsGenerated;

        public GraphPanel(int[][] adjacencyMatrix) {
            this.adjacencyMatrix = adjacencyMatrix;
            this.numNodes = adjacencyMatrix.length;
            this.nodePositions = new HashMap<>();
            this.positionsGenerated = false;
        }

        @Override
        public void addNotify() {
            super.addNotify();

            if (isVisible()) {
                generateNodePositions();
                this.positionsGenerated = true;
            }
        }

        private void generateNodePositions() {
            // Generate random positions for the nodes
            Random random = new Random();
            for (int i = 0; i < numNodes; i++) {
                if (getWidth() - NODE_SIZE > 0) {
                    int x = random.nextInt(getWidth() - NODE_SIZE);
                    int y = random.nextInt(getHeight() - NODE_SIZE);
                    nodePositions.put(i, new Point(x, y));
                }
                int x = random.nextInt(getWidth() - NODE_SIZE);
                int y = random.nextInt(getHeight() - NODE_SIZE);
                nodePositions.put(i, new Point(x, y));
            }
        }

        @Override
        public boolean isVisible() {
            return super.isVisible() && getWidth() > 0 && getHeight() > 0;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Check if positions have been generated
            if (!positionsGenerated) {
                generateNodePositions();
                this.positionsGenerated = true;
            }

            for (int i = 0; i < numNodes; i++) {
                for (int j = i + 1; j < numNodes; j++) {
                    if (adjacencyMatrix[i][j] == 1) {
                        Point node1 = nodePositions.get(i);
                        Point node2 = nodePositions.get(j);

                        if (visited.contains(i) && visited.contains(j)) {
                            g.setColor(Color.RED);
                        } else {
                            g.setColor(Color.BLACK);
                        }
                        g.drawLine(node1.x + NODE_SIZE / 2, node1.y + NODE_SIZE / 2,
                                node2.x + NODE_SIZE / 2, node2.y + NODE_SIZE / 2);
                    }
                }
            }

            for (int i = 0; i < numNodes; i++) {
                Point node = nodePositions.get(i);
                if (visited.contains(i)) {
                    g.setColor(Color.RED);
                } else if (i == currentNode) { // set color to yellow for current node
                    g.setColor(Color.YELLOW);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillOval(node.x, node.y, NODE_SIZE, NODE_SIZE);
                g.setColor(Color.WHITE);
                g.drawString(Character.toString((char)(i+65)), node.x + NODE_SIZE / 2 - 4, node.y + NODE_SIZE / 2 + 4);
                g.setColor(Color.BLACK);
            }
        }
    }
}
