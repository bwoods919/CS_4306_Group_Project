/*

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class BFS_GUI extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    
    private int V; // No. of vertices
    private LinkedList<Integer> adj[]; // Adjacency Lists
    private JTextArea textArea;
    private JTextField startField;
    
    public BFS_GUI() {
        // initialize graph
        V = 4;
        adj = new LinkedList[V];
        for (int i = 0; i < V; ++i)
            adj[i] = new LinkedList();
        
        // create GUI components
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        startField = new JTextField(5);
        JButton startButton = new JButton("Start BFS");
        startButton.addActionListener(this);
        JPanel inputPanel = new JPanel();
        inputPanel.add(startField);
        inputPanel.add(startButton);
        
        // add components to JFrame
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
        pack();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        // clear text area
        textArea.setText("");
        
        // parse start vertex from input field
        int start = Integer.parseInt(startField.getText());
        
        // run BFS
        boolean visited[] = new boolean[V];
        LinkedList<Integer> queue = new LinkedList<Integer>();
        visited[start] = true;
        queue.add(start);
        while (queue.size() != 0) {
            int s = queue.poll();
            textArea.append(s + " ");
            Iterator<Integer> i = adj[s].listIterator();
            while (i.hasNext()) {
                int n = i.next();
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }
    }
    
    void addEdge(int v, int w) {
        adj[v].add(w);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BFS_GUI();
            }
        });
    }
}

*/

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class BFS_GUI extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private int V; // No. of vertices
    private LinkedList<Integer> adj[]; // Adjacency Lists
    private boolean visited[]; // array to keep track of visited nodes
    private int currentIndex = 0;
    private JTextArea textArea;
    
    public BFS_GUI(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();
        visited = new boolean[V];
        
        // create GUI components
        textArea = new JTextArea();
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentIndex < V) {
                    step();
                }
            }
        });
        
        // add components to JFrame
        setLayout(new BorderLayout());
        add(textArea, BorderLayout.CENTER);
        add(nextButton, BorderLayout.SOUTH);
        pack();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    // Function to add an edge into the graph
    void addEdge(int v, int w) { adj[v].add(w); }
    
    // performs BFS traversal from a given source s
    void BFS(int s) {
        // Create a queue for BFS
        LinkedList<Integer> queue = new LinkedList<Integer>();

        // Mark the current node as visited and enqueue it
        visited[s] = true;
        queue.add(s);

        while (queue.size() != 0) {
            // Dequeue a vertex from queue and print it
            s = queue.poll();
            textArea.append(s + " ");

            // Get all adjacent vertices of the dequeued vertex s
            // If an adjacent has not been visited, then mark it visited
            // and enqueue it
            Iterator<Integer> i = adj[s].listIterator();
            while (i.hasNext()) {
                int n = i.next();
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }
    }
    
    private void step() {
        BFS(currentIndex);
        currentIndex++;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                BFS_GUI g = new BFS_GUI(4);

                g.addEdge(0, 1);
                g.addEdge(0, 2);
                g.addEdge(1, 2);
                g.addEdge(2, 0);
                g.addEdge(2, 3);
                g.addEdge(3, 3);

                System.out.println(
                        "Following is Breadth First Traversal "
                                + "(starting from vertex 2)");

                g.step(); // start BFS traversal
            }
        });
    }
}

