import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class ArrayMerger {

    public static class MergeResult {
        public int[] mergedArray;
        public MergeStatistics statistics;

        public MergeResult(int[] mergedArray, MergeStatistics statistics) {
            this.mergedArray = mergedArray;
            this.statistics = statistics;
        }
    }

    public static class MergeStatistics {
        public int comparisons;
        public int swaps;
        public long executionTimeNanos;
        public int[] originalArray1;
        public int[] originalArray2;

        public MergeStatistics(int[] arr1, int[] arr2) {
            this.originalArray1 = Arrays.copyOf(arr1, arr1.length);
            this.originalArray2 = Arrays.copyOf(arr2, arr2.length);
        }

        @Override
        public String toString() {
            return String.format(
                "Merge Statistics:\n" +
                "Comparisons: %d\n" +
                "Swaps: %d\n" +
                "Execution Time: %.3f ms\n" +
                "Original Array 1: %s\n" +
                "Original Array 2: %s",
                comparisons, swaps, executionTimeNanos / 1_000_000.0,
                Arrays.toString(originalArray1), Arrays.toString(originalArray2)
            );
        }
    }

    public static MergeResult mergeArrays(int[] arr1, int[] arr2, boolean ascending) {
        if (arr1 == null || arr2 == null) {
            throw new IllegalArgumentException("Input arrays cannot be null");
        }

        MergeStatistics stats = new MergeStatistics(arr1, arr2);
        long startTime = System.nanoTime();

        int n1 = arr1.length;
        int n2 = arr2.length;
        int[] mergedArray = new int[n1 + n2];

        Comparator<Integer> comparator = ascending ? Integer::compare : (a, b) -> Integer.compare(b, a);

        int i = 0, j = 0, k = 0;

        while (i < n1 && j < n2) {
            stats.comparisons++;
            if (comparator.compare(arr1[i], arr2[j]) <= 0) {
                mergedArray[k++] = arr1[i++];
            } else {
                mergedArray[k++] = arr2[j++];
            }
            stats.swaps++;
        }

        while (i < n1) {
            mergedArray[k++] = arr1[i++];
            stats.swaps++;
        }

        while (j < n2) {
            mergedArray[k++] = arr2[j++];
            stats.swaps++;
        }

        stats.executionTimeNanos = System.nanoTime() - startTime;
        return new MergeResult(mergedArray, stats);
    }

    public static void printArray(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    public static int[] generateRandomArray(int size, int min, int max) {
        Random random = new Random();
        return random.ints(size, min, max + 1).toArray();
    }

    public static void runTestCase(int[] arr1, int[] arr2, boolean ascending) {
        System.out.println("\nTest Case:");
        System.out.println("Array 1: " + Arrays.toString(arr1));
        System.out.println("Array 2: " + Arrays.toString(arr2));
        System.out.println("Ascending: " + ascending);

        try {
            MergeResult result = mergeArrays(arr1, arr2, ascending);
            System.out.println("Merged Array: " + Arrays.toString(result.mergedArray));
            System.out.println(result.statistics);

            // Verify the result
            int[] expectedResult = Arrays.copyOf(arr1, arr1.length + arr2.length);
            System.arraycopy(arr2, 0, expectedResult, arr1.length, arr2.length);
            Arrays.sort(expectedResult);
            if (!ascending) {
                for (int i = 0; i < expectedResult.length / 2; i++) {
                    int temp = expectedResult[i];
                    expectedResult[i] = expectedResult[expectedResult.length - 1 - i];
                    expectedResult[expectedResult.length - 1 - i] = temp;
                }
            }
            
            boolean isCorrect = Arrays.equals(result.mergedArray, expectedResult);
            System.out.println("Correct result: " + isCorrect);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Test case 1: Basic ascending order
        runTestCase(new int[]{1, 3, 5}, new int[]{2, 4, 6}, true);

        // Test case 2: Basic descending order
        runTestCase(new int[]{5, 3, 1}, new int[]{6, 4, 2}, false);

        // Test case 3: Empty arrays
        runTestCase(new int[]{}, new int[]{}, true);

        // Test case 4: One empty array
        runTestCase(new int[]{1, 2, 3}, new int[]{}, true);

        // Test case 5: Arrays with duplicate elements
        runTestCase(new int[]{1, 2, 2, 3}, new int[]{2, 3, 4, 4}, true);

        // Test case 6: Large random arrays
        int[] largeArr1 = generateRandomArray(10000, 1, 1000);
        int[] largeArr2 = generateRandomArray(10000, 1, 1000);
        Arrays.sort(largeArr1);
        Arrays.sort(largeArr2);
        runTestCase(largeArr1, largeArr2, true);

        // Test case 7: Error handling - null input
        runTestCase(null, new int[]{1, 2, 3}, true);
    }
}