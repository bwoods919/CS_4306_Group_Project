import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class InsertionSortGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    
private int[] array = {5, 2, 8, 4, 1, 9};
    private int currentIndex = 1;
    private JTextArea textArea;
    
    public InsertionSortGUI() {
        // create GUI components
        textArea = new JTextArea(Arrays.toString(array));
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentIndex < array.length) {
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
    
    private void step() {
        int key = array[currentIndex];
        int j = currentIndex - 1;
        while (j >= 0 && array[j] > key) {
            array[j + 1] = array[j];
            j--;
        }
        array[j + 1] = key;
        currentIndex++;
        textArea.setText(Arrays.toString(array));
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new InsertionSortGUI();
            }
        });
    }
}
