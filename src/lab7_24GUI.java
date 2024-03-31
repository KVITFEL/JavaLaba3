import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

    public class lab7_24GUI extends JFrame {


        private JLabel resultLabel;
        private JTextArea sequenceArea;
        private JButton calculateButton;
        private JTextField arrayField;

        public lab7_24GUI() {
            setTitle("Lab 7_24var");
            setSize(400, 300);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());

            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new FlowLayout());
            JLabel arrayLabel = new JLabel("Массив:");
            arrayField = new JTextField(20);
            inputPanel.add(arrayLabel);
            inputPanel.add(arrayField);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout());
            calculateButton = new JButton("Посчитать");
            buttonPanel.add(calculateButton);

            JPanel resultPanel = new JPanel();
            resultPanel.setLayout(new BorderLayout());
            resultLabel = new JLabel("");
            resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
            sequenceArea = new JTextArea(5, 20);
            sequenceArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(sequenceArea);
            resultPanel.add(resultLabel, BorderLayout.NORTH);
            resultPanel.add(scrollPane, BorderLayout.CENTER);

            mainPanel.add(inputPanel, BorderLayout.NORTH);
            mainPanel.add(buttonPanel, BorderLayout.CENTER);
            mainPanel.add(resultPanel, BorderLayout.SOUTH);

            add(mainPanel);

            calculateButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String input = arrayField.getText();
                    if (input.isEmpty()) {
                        JOptionPane.showMessageDialog(lab7_24GUI.this, "Введите массив", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String[] tokens = input.split(",");
                    int[] array = new int[tokens.length];
                    for (int i = 0; i < tokens.length; i++) {
                        try {
                            array[i] = Integer.parseInt(tokens[i].trim());
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(lab7_24GUI.this, "Неправильный формат массива", "Ошибка", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    int[] result = calculateSequence(array);
                    resultLabel.setText("Начальная позиция: " + result[0] + ", Длина: " + result[1]);
                    sequenceArea.setText(getSequenceString(array, result[0], result[1]));
                }
            });
        }

        private int[] calculateSequence(int[] array) {
            int maxLen = 0;
            int curLen = 1;
            int startIndex = 0;

            for (int i = 1; i < array.length; i++) {
                if ((array[i] % 2 == 0 && array[i - 1] % 2 != 0) || (array[i] % 2 != 0 && array[i - 1] % 2 == 0)) {
                    curLen++;
                    if (curLen >= maxLen) {
                        maxLen = curLen;
                        startIndex = i - curLen + 1;
                    }
                } else {
                    curLen = 1;
                }
            }

            return new int[]{startIndex, maxLen};
        }

        private String getSequenceString(int[] array, int startIndex, int length) {
            StringBuilder sb = new StringBuilder();
            for (int i = startIndex; i < startIndex + length; i++) {
                sb.append(array[i]).append(" ");
            }
            return sb.toString();
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new lab7_24GUI().setVisible(true);
                }
            });
        }
    }


