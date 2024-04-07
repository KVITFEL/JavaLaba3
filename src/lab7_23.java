import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class lab7_23 {
    private static JTextArea resultTextArea;
    private static JTextField inputField;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Lab3");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel inputLabel = new JLabel("Введите элементы массива через запятую:");
        inputField = new JTextField();
        JButton calculateButton = new JButton("Вычислить");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculate();
            }
        });

        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(resultTextArea);

        panel.add(inputLabel, BorderLayout.NORTH);
        panel.add(inputField, BorderLayout.CENTER);
        panel.add(calculateButton, BorderLayout.SOUTH);

        frame.getContentPane().add(panel, BorderLayout.NORTH);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static void calculate() {
        String input = inputField.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Пожалуйста, введите элементы массива.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] inputArray = input.split(",");
        int[] array = new int[inputArray.length];
        for (int i = 0; i < inputArray.length; i++) {
            try {
                array[i] = Integer.parseInt(inputArray[i].trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Пожалуйста, введите корректные числа через запятую.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        String result = findLongestSubsequences(array);

        resultTextArea.setText(result);
    }

    public static String findLongestSubsequences(int[] array) {
        StringBuilder result = new StringBuilder();
        int maxLength = 0;

        for (int i = 0; i < array.length - 1; i++) {
            int currentLength = 1;
            int mismatchCount = 0;
            int[] candidates = new int[2];
            int candidateCount = 0;

            for (int j = i; j < array.length - 1; j++) {
                if (array[j] != array[j + 1]) {
                    mismatchCount++;
                    if (mismatchCount > 2) {
                        break;
                    }
                    if (candidateCount < 2) {
                        candidates[candidateCount++] = j;
                    }
                }
                currentLength++;

                if (currentLength > maxLength && mismatchCount <= 2) {
                    maxLength = currentLength;
                    // Очищаем результат и добавляем новую подпоследовательность
                    result.setLength(0);
                    result.append("Самая длинная подпоследовательность:\n");
                    result.append("Подпоследовательность: ");
                    for (int k = i; k < i + currentLength; k++) {
                        result.append(array[k]).append(" ");
                    }
                    result.append("\nПозиция: ").append(i).append(", Длина: ").append(currentLength).append("\n");
                } else if (currentLength == maxLength && mismatchCount <= 2) {
                    // Добавляем еще одну подпоследовательность той же длины
                    result.append("Подпоследовательность: ");
                    for (int k = i; k < i + currentLength; k++) {
                        result.append(array[k]).append(" ");
                    }
                    result.append("\nПозиция: ").append(i).append(", Длина: ").append(currentLength).append("\n");
                }
            }
        }
        return result.toString();
    }
}
