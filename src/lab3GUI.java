import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class lab3GUI {
    static DefaultTableModel tableModel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Lab 3");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel sizeLabel = new JLabel("Размер массива:");
        sizeLabel.setBounds(10, 20, 120, 25);
        panel.add(sizeLabel);

        JTextField sizeField = new JTextField(20);
        sizeField.setBounds(140, 20, 120, 25);
        panel.add(sizeField);

        JButton submitButton = new JButton("Значения");
        submitButton.setBounds(280, 20, 100, 25);
        panel.add(submitButton);

        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 60, 370, 150);
        panel.add(scrollPane);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int size = Integer.parseInt(sizeField.getText());
                tableModel = new DefaultTableModel(0, 1);
                table.setModel(tableModel);

                for (int i = 0; i < size; i++) {
                    String input = JOptionPane.showInputDialog("Введите значение для элемента " + (i + 1));
                    tableModel.addRow(new Object[]{input});
                }

                int maxCount = calculateMaxCount();
                JOptionPane.showMessageDialog(panel, "Максимальное кол-во одинаковых элементов: " + maxCount);
            }
        });
    }

    private static int calculateMaxCount() {
        int rowCount = tableModel.getRowCount();
        if (rowCount == 0) return 0;

        int count = 1, countMax = 1;
        String prevValue = (String) tableModel.getValueAt(0, 0);
        for (int i = 1; i < rowCount; i++) {
            String curValue = (String) tableModel.getValueAt(i, 0);
            if (curValue.equals(prevValue)) {
                count++;
            } else {
                count = 1;
                prevValue = curValue;
            }
            countMax = Math.max(count, countMax);
        }
        return countMax;
    }
}
