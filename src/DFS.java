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

            if (isVisible() && getWidth() > 0) {
                generateNodePositions();
                this.positionsGenerated = true;
            }
        }

        private void generateNodePositions() {
            // Generate random positions for the nodes
            Random random = new Random();
            for (int i = 0; i < numNodes; i++) {
                if (getWidth() - NODE_SIZE > 0) {
                    int x = random.nextInt(Math.max(getWidth() - NODE_SIZE, 1));
                    int y = random.nextInt(getHeight() - NODE_SIZE);
                    nodePositions.put(i, new Point(x, y));
                }
                int x = random.nextInt(getWidth() - NODE_SIZE);
                int y = random.nextInt(getHeight() - NODE_SIZE);
                nodePositions.put(i, new Point(x, y));
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (!positionsGenerated) {
                return;
            }

            Graphics2D g2d = (Graphics2D) g;

            // Draw edges
            g2d.setColor(Color.BLACK);
            for (int i = 0; i < numNodes; i++) {
                for (int j = i; j < numNodes; j++) {
                    if (adjacencyMatrix[i][j] == 1) {
                        Point p1 = nodePositions.get(i);
                        Point p2 = nodePositions.get(j);
                        g2d.drawLine(p1.x + NODE_SIZE / 2, p1.y + NODE_SIZE / 2,
                                p2.x + NODE_SIZE / 2, p2.y + NODE_SIZE / 2);
                    }
                }
            }

            // Draw nodes
            for (int i = 0; i < numNodes; i++) {
                Point p = nodePositions.get(i);
                g2d.setColor(Color.WHITE);
                g2d.fillOval(p.x, p.y, NODE_SIZE, NODE_SIZE);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(p.x, p.y, NODE_SIZE, NODE_SIZE);
                g2d.drawString(Character.toString((char) (i + 65)), p.x + NODE_SIZE / 2 - 5, p.y + NODE_SIZE / 2 + 5);

                if (i == currentNode) {
                    g2d.setColor(Color.BLUE);
                    g2d.drawOval(p.x - 2, p.y - 2, NODE_SIZE + 4, NODE_SIZE + 4);
                }

                if (visited.contains(i)) {
                    g2d.setColor(Color.GREEN);
                    g2d.fillOval(p.x + 1, p.y + 1, NODE_SIZE - 2, NODE_SIZE - 2);
                }
            }
        }
    }
}
