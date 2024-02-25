import java.util.Scanner;

public class lab3_zvezd {

    static Scanner value = new Scanner(System.in);
    static int[] numbers;
    //static int[] numbers = {4, 7, 6, 4, 6, 7, 7, 7, 7, 1, 5, 3, 3, 7, 3, 3};
    //static int[] numbers = {3, 3, 7, 3, 3, 7, 6, 4, 6, 7, 7, 7, 7, 1};
    //static int[] numbers = {6, 3, 3, 7, 3, 3, 7, 4, 6, 7, 7, 7, 7, 1};
    //static int[] numbers = {3, 7, 3, 3, 7, 4, 6, 7, 7, 7, 7, 1, 4, 7};
    public static void main(final String[] args) {
        //индекс начала последовательности и длина
        GerArray();

        int[] arrIndex = LongInd(numbers);

        for (int i = arrIndex[0]; i < (arrIndex[0] + arrIndex[1]); i++) {
            System.out.print(numbers[i] + " ");//Вывод самой длинной последовательности
        }
    }
    public static void GerArray() {

        System.out.print("Задайте размерность массива: ");
        int size = value.nextInt();
        numbers = new int[size];
        System.out.println("Введите значения массива: ");

        for (int i = 0; i < numbers.length; i++) {
            System.out.print(i + 1 + ") ");
            numbers[i] = value.nextInt();
        }
    }
    public static int[] LongInd(final int[] numbers) {
        int startIndexSeq = 0;
        int lengthSeq = 0;

        int maxIndex = 0;//самая длинная посл-ть
        int maxLength = 0;//->!

        int otherPairs = 0;

        boolean inSeq = false;//находимся внутри посл-ти?

        int i = 0;

        while (i < (numbers.length - 1)) {
            if (!inSeq) {
                if (numbers[i] == numbers[i + 1]) {
                    inSeq = true;
                    startIndexSeq = i;
                    lengthSeq = 2;
                    i++;

                    if (startIndexSeq > 0) {
                        startIndexSeq--;
                        lengthSeq++;
                        otherPairs++;
                    }
                } else {
                    i++;
                }
            } else {
                if (numbers[i] == numbers[i + 1]) {
                    lengthSeq++;
                    i++;
                } else {
                    if (otherPairs == 2) {
                        if (maxLength < lengthSeq) {
                            maxIndex = startIndexSeq;
                            maxLength = lengthSeq;
                        }

                        inSeq = false;
                        startIndexSeq = 0;
                        lengthSeq = 0;
                        otherPairs = 0;
                        i++;
                    } else {
                        otherPairs++;

                        if ((i < (numbers.length - 2)) && (numbers[i] == numbers[i + 2])) {
                            lengthSeq += 2;
                            i += 2;
                        } else {
                            lengthSeq++;

                            if (maxLength < lengthSeq) {
                                maxIndex = startIndexSeq;
                                maxLength = lengthSeq;
                            }

                            inSeq = false;
                            startIndexSeq = 0;
                            lengthSeq = 0;
                            otherPairs = 0;
                            i++;
                        }
                    }
                }
            }
        }
        return new int[]{maxIndex, maxLength};
    }
}

