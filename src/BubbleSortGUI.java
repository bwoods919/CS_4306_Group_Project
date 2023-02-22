import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BubbleSortGUI extends JFrame implements ActionListener {
    
    private int[] arr;
    private int currentIndex;
    private JButton sortButton;
    private JLabel statusLabel;
    private JPanel sortPanel;
    private Timer timer;
    
    public BubbleSortGUI(int[] arr) {
        super("Bubble Sort");
        this.arr = arr;
        this.currentIndex = 0;
        
        // create the GUI elements
        this.sortButton = new JButton("Sort");
        this.statusLabel = new JLabel("Ready");
        this.sortPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawArray(g);
            }
        };
        this.sortPanel.setPreferredSize(new Dimension(400, 400));
        this.sortPanel.setBackground(Color.WHITE);
        
        // add the GUI elements to the JFrame
        this.add(this.sortPanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(this.sortButton);
        buttonPanel.add(this.statusLabel);
        this.add(buttonPanel, BorderLayout.SOUTH);
        
        // set up the timer for sorting
        this.timer = new Timer(1000, this);
        this.timer.setInitialDelay(0);
        
        // add the sort button action listener
        this.sortButton.addActionListener(this);
        
        // set up the JFrame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
    
    private void drawArray(Graphics g) {
        int barWidth = (int) ((double) this.sortPanel.getWidth() / this.arr.length);
        int maxHeight = this.sortPanel.getHeight() - 20;
        int y = maxHeight;
        for (int i = 0; i < this.arr.length; i++) {
            int x = i * barWidth;
            int height = (int) (((double) this.arr[i] / 100) * maxHeight);
            g.fillRect(x, y - height, barWidth, height);
        }
    }
    
    private void swap(int i, int j) {
        int temp = this.arr[i];
        this.arr[i] = this.arr[j];
        this.arr[j] = temp;
    }
    
    private void bubbleSortStep() {
        if (this.currentIndex >= this.arr.length - 1) {
            this.statusLabel.setText("Done");
            this.timer.stop();
            return;
        }
        for (int i = 0; i < this.arr.length - this.currentIndex - 1; i++) {
            if (this.arr[i] > this.arr[i+1]) {
                swap(i, i+1);
                this.statusLabel.setText("Swapping " + this.arr[i] + " and " + this.arr[i+1]);
                this.sortPanel.repaint();
                return;
            }
        }
        this.statusLabel.setText("No swap needed");
        this.currentIndex++;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.sortButton) {
            this.timer.start();
            this.statusLabel.setText("Sorting...");
        } else if (e.getSource() == this.timer) {
            bubbleSortStep();
        }
    }
    
    public static void main(String[] args) {
        int[] arr = {5, 2, 9, 1, 5, 6, 3};
        BubbleSortGUI gui = new BubbleSortGUI(arr);
    }
}
