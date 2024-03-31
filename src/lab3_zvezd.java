public class lab3_zvezd{
public static int index = 0;
public static int length = 0;

//    public static int[] array = {1, 2, -2, 1, 10, 3, 105, 4, -5, 4, 10, 1, 1, 3, 4, 7, 12, 10};
public static int[] array = {1,2,1,2,2,2,1,-2,1};


public static void Result() {
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

    index = startIndex;
    length = maxLen;
}

public static int[] Position() {
    return new int[]{index, length};
}

public static void PrintArray(){
    for(int i = index; i < index+length;i++){
        System.out.print(array[i]+" ");
    }
    System.out.println();

}

public static void main(String[] args) {
    Result();
    int[] positionAndLength = Position();
    System.out.println("Позиция первого элемента: " + positionAndLength[0]);
    System.out.println("Длина : " + positionAndLength[1]);

    PrintArray();
}
}
