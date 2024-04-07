public class lab3 {
    public static void main(String[] args) {
        int[] array = {1,3,2,2,2};
        String result = findLongestSubsequences(array);

        System.out.println("Самая длинная подпоследовательность :");
        String[] subsequences = result.split(";");
        for (String subsequence : subsequences) {
            String[] parts = subsequence.split(",");
            int startIndex = Integer.parseInt(parts[0]);
            int length = Integer.parseInt(parts[1]);

            System.out.print("Подпоследовательность: ");
            for (int i = startIndex; i < startIndex + length; i++) {
                System.out.print(array[i] + " ");
            }
            System.out.println("\nПозиция: " + startIndex + ", Длина: " + length);
        }
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
                    result.append(i).append(",").append(currentLength).append(";");
                } else if (currentLength == maxLength && mismatchCount <= 2) {
                    // Добавляем еще одну подпоследовательность той же длины
                    result.append(i).append(",").append(currentLength).append(";");
                }
            }
        }
        // Убираем последний символ ";", который лишний
        if (result.length() > 0) {
            result.setLength(result.length() - 1);
        }
        return result.toString();
    }
}
