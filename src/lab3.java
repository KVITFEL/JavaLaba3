import java.util.Scanner;

public class lab3 {

    static Scanner value = new Scanner(System.in);
    static int[] array;

    public static void main(String[] args) {
        GerArray();
        Solution();
        //PrintArray();

    }
    public static void GerArray() {

        System.out.print("Задайте размерность массива: ");
        int size = value.nextInt();
        array = new int[size];
        System.out.println("Введите значения массива: ");

        for (int i = 0; i < array.length; i++) {
            System.out.print(i + 1 + ") ");
            array[i] = value.nextInt();
        }
    }

    public static void Solution() {
        int count = 0, countMax = 0;
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] == array[i + 1]) {
                count++;
            }
            if(array[i] != array[i + 1]){
                count = 0;
            }
            if (count > countMax) {
                countMax = count;
            }
        }
        countMax++; // поскольку числа смотрятся парами, пропадает одно значение счетчика
        System.out.println("Максимальное кол-во одинаковых элементов: " +countMax);

    }
}

//    public static void PrintArray() {
//        for (int i = 0; i < array.length; i++) {
//            System.out.println(i + 1 + ") " + array[i]);
//        }
//    }


