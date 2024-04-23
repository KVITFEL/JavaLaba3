import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class lab8_24GUI extends JFrame {
    private JTable table1;
    private JTable table2;
    private JButton loadButton;
    private JButton saveButton;
    private JButton checkOverlayButton;

    public lab8_24GUI() {
        setTitle("Проверка наложения массивов");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);

        // Создание таблиц
        table1 = new JTable();
        table2 = new JTable();
        JPanel tablesPanel = new JPanel(new GridLayout(1, 2));
        tablesPanel.add(new JScrollPane(table1));
        tablesPanel.add(new JScrollPane(table2));
        getContentPane().add(tablesPanel, BorderLayout.CENTER);

        // Создание кнопок
        loadButton = new JButton("Загрузить ");
        saveButton = new JButton("Сохранить");
        checkOverlayButton = new JButton("Проверить наложение");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(checkOverlayButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Обработчики событий для кнопок
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFromFile();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToFile();
            }
        });

        checkOverlayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkOverlay();
            }
        });
    }

    // Метод для загрузки данных из файла
    private void loadFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                int[][][] data = readDataFromFile(selectedFile);
                displayDataInTables(data);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Ошибка загрузки файла.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Метод для чтения данных из файла
    private int[][][] readDataFromFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        if (line == null) {
            throw new IOException("Неправильный формат файла");
        }
        String[] dimensions1 = line.trim().split(" ");
        int rows1 = Integer.parseInt(dimensions1[0]);
        int cols1 = Integer.parseInt(dimensions1[1]);
        int[][] array1 = new int[rows1][cols1];
        for (int i = 0; i < rows1; i++) {
            line = reader.readLine();
            if (line == null) {
                throw new IOException("Неправильный формат файла");
            }
            String[] values = line.trim().split(" ");
            for (int j = 0; j < cols1; j++) {
                array1[i][j] = Integer.parseInt(values[j]);
            }
        }

        // Пропускаем пустую строку
        reader.readLine();

        String[] dimensions2 = reader.readLine().trim().split(" ");
        int rows2 = Integer.parseInt(dimensions2[0]);
        int cols2 = Integer.parseInt(dimensions2[1]);
        int[][] array2 = new int[rows2][cols2];
        for (int i = 0; i < rows2; i++) {
            line = reader.readLine();
            if (line == null) {
                throw new IOException("Неправильный формат файла");
            }
            String[] values = line.trim().split(" ");
            for (int j = 0; j < cols2; j++) {
                array2[i][j] = Integer.parseInt(values[j]);
            }
        }

        reader.close();
        return new int[][][]{array1, array2};
    }


    // Метод для отображения данных в таблицах
    private void displayDataInTables(int[][][] data) {
        DefaultTableModel model1 = new DefaultTableModel(data[0].length, data[0][0].length);
        for (int i = 0; i < data[0].length; i++) {
            for (int j = 0; j < data[0][0].length; j++) {
                model1.setValueAt(data[0][i][j], i, j);
            }
        }
        table1.setModel(model1);

        DefaultTableModel model2 = new DefaultTableModel(data[1].length, data[1][0].length);
        for (int i = 0; i < data[1].length; i++) {
            for (int j = 0; j < data[1][0].length; j++) {
                model2.setValueAt(data[1][i][j], i, j);
            }
        }
        table2.setModel(model2);
    }

    // Метод для сохранения данных в файл
    private void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                int[][][] data = getDataFromTables();
                writeDataToFile(data, selectedFile);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Ошибка сохранения файла.", "Ошибка ", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Метод для получения данных из таблиц
    private int[][][] getDataFromTables() {
        DefaultTableModel model1 = (DefaultTableModel) table1.getModel();
        int rows1 = model1.getRowCount();
        int cols1 = model1.getColumnCount();
        int[][] array1 = new int[rows1][cols1];
        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols1; j++) {
                array1[i][j] = (int) model1.getValueAt(i, j);
            }
        }

        DefaultTableModel model2 = (DefaultTableModel) table2.getModel();
        int rows2 = model2.getRowCount();
        int cols2 = model2.getColumnCount();
        int[][] array2 = new int[rows2][cols2];
        for (int i = 0; i < rows2; i++) {
            for (int j = 0; j < cols2; j++) {
                array2[i][j] = (int) model2.getValueAt(i, j);
            }
        }

        return new int[][][]{array1, array2};
    }

    // Метод для проверки наложения массивов
    private void checkOverlay() {
        int[][][] data = getDataFromTables();
        int[] coordinates = findOverlayCoordinates(data[0], data[1]);
        if (coordinates != null) {
            JOptionPane.showMessageDialog(this, "Массивы можно наложить, координаты: (" + coordinates[0] + ", " + coordinates[1] + ")", "Результат", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Наложение невозможно.", "Результат", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Функция для проверки наложения массивов
    public static int[] findOverlayCoordinates(int[][] array1, int[][] array2) {
        int[] coordinates = new int[2];
        int rows1 = array1.length;
        int cols1 = array1[0].length;
        int rows2 = array2.length;
        int cols2 = array2[0].length;

        for (int i = 0; i <= rows1 - rows2; i++) {
            for (int j = 0; j <= cols1 - cols2; j++) {
                if (checkOverlay(array1, array2, i, j)) {
                    coordinates[0] = i;
                    coordinates[1] = j;
                    return coordinates;
                }
            }
        }
        return null;
    }

    // Функция для проверки наложения массивов
    public static boolean checkOverlay(int[][] array1, int[][] array2, int row, int col) {
        int rows2 = array2.length;
        int cols2 = array2[0].length;

        for (int i = 0; i < rows2; i++) {
            for (int j = 0; j < cols2; j++) {
                if (array1[row + i][col + j] != array2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Метод для записи данных в файл
    private void writeDataToFile(int[][][] data, File file) throws IOException {
        PrintWriter writer = new PrintWriter(file);
        writer.println(data[0].length + " " + data[0][0].length);
        for (int i = 0; i < data[0].length; i++) {
            for (int j = 0; j < data[0][0].length; j++) {
                writer.print(data[0][i][j] + " ");
            }
            writer.println();
        }

        writer.println(data[1].length + " " + data[1][0].length);
        for (int i = 0; i < data[1].length; i++) {
            for (int j = 0; j < data[1][0].length; j++) {
                writer.print(data[1][i][j] + " ");
            }
            writer.println();
        }

        writer.close();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                lab8_24GUI gui = new lab8_24GUI();
                gui.setVisible(true);
            }
        });
    }
}
