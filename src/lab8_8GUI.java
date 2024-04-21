import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class lab8_8GUI extends JFrame {

    private JTable originalTable;
    private DefaultTableModel originalModel;

    private JTable sortedTable;
    private DefaultTableModel sortedModel;

    public lab8_8GUI() {
        setTitle("lab8_8GUI");
        setSize(600, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        originalModel = new DefaultTableModel();
        originalTable = new JTable(originalModel);
        JScrollPane originalScrollPane = new JScrollPane(originalTable);

        sortedModel = new DefaultTableModel();
        sortedTable = new JTable(sortedModel);
        JScrollPane sortedScrollPane = new JScrollPane(sortedTable);

        JButton loadButton = new JButton("Загрузить");
        JButton sortButton = new JButton("Сортировать");
        JButton saveButton = new JButton("Сохранить");

        loadButton.addActionListener(e -> loadDataFromFile());
        sortButton.addActionListener(e -> sortData());
        saveButton.addActionListener(e -> saveDataToFile());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loadButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(saveButton);

        setLayout(new GridLayout(1, 3));
        add(originalScrollPane);
        add(sortedScrollPane);
        add(buttonPanel);
    }

    private void loadDataFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                Scanner scanner = new Scanner(selectedFile);
                int rows = scanner.nextInt();
                int cols = scanner.nextInt();
                scanner.nextLine(); // consume newline
                originalModel.setRowCount(rows);
                originalModel.setColumnCount(cols);
                for (int i = 0; i < rows; i++) {
                    String[] rowData = scanner.nextLine().trim().split("\\s+");
                    for (int j = 0; j < cols; j++) {
                        originalModel.setValueAt(Integer.parseInt(rowData[j]), i, j);
                    }
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Файл не найден", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void sortData() {
        int rows = originalModel.getRowCount();
        int cols = originalModel.getColumnCount();
        int[][] array = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                array[i][j] = (int) originalModel.getValueAt(i, j);
            }
        }
        sortColumns(array);
        sortedModel.setRowCount(rows);
        sortedModel.setColumnCount(cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sortedModel.setValueAt(array[i][j], i, j);
            }
        }
    }

    private void saveDataToFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                PrintWriter writer = new PrintWriter(selectedFile);
                int rows = sortedModel.getRowCount();
                int cols = sortedModel.getColumnCount();
                writer.println(rows + " " + cols);
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        writer.print(sortedModel.getValueAt(i, j) + " ");
                    }
                    writer.println();
                }
                writer.close();
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Ошибка сохранения файла", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void sortColumns(int[][] array) {
        for (int i = 0; i < array[0].length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array[0].length; j++) {
                if (compareColumns(array, j, minIndex) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                swapColumns(array, i, minIndex);
            }
        }
    }

    private static int compareColumns(int[][] array, int col1, int col2) {
        for (int i = 0; i < array.length; i++) {
            if (array[i][col1] != array[i][col2]) {
                return array[i][col1] - array[i][col2];
            }
        }
        return 0;
    }

    private static void swapColumns(int[][] array, int col1, int col2) {
        for (int i = 0; i < array.length; i++) {
            int temp = array[i][col1];
            array[i][col1] = array[i][col2];
            array[i][col2] = temp;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(lab8_8GUI::new);
    }
}
