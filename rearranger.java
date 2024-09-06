import java.util.Arrays;

public class ArrayRearranger {

    public static void reArrange(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return; // No rearrangement needed for null, empty, or single-element arrays
        }

        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            // Move left pointer until we find a positive number
            while (left < arr.length && arr[left] < 0) {
                left++;
            }

            // Move right pointer until we find a negative number
            while (right >= 0 && arr[right] >= 0) {
                right--;
            }

            // Swap the positive number on the left with the negative number on the right
            if (left < right) {
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
            }
        }
    }

    public static void main(String[] args) {
        // Test case 1: Array with mixed positive and negative numbers
        int[] arr1 = {-1, 2, -3, 4, 5, -6, 7, -8, 9};
        System.out.println("Test case 1:");
        System.out.println("Before: " + Arrays.toString(arr1));
        reArrange(arr1);
        System.out.println("After:  " + Arrays.toString(arr1));

        // Test case 2: Array with all positive numbers
        int[] arr2 = {1, 2, 3, 4, 5};
        System.out.println("\nTest case 2:");
        System.out.println("Before: " + Arrays.toString(arr2));
        reArrange(arr2);
        System.out.println("After:  " + Arrays.toString(arr2));

        // Test case 3: Array with all negative numbers
        int[] arr3 = {-1, -2, -3, -4, -5};
        System.out.println("\nTest case 3:");
        System.out.println("Before: " + Arrays.toString(arr3));
        reArrange(arr3);
        System.out.println("After:  " + Arrays.toString(arr3));

        // Test case 4: Array with zeros
        int[] arr4 = {-2, 0, 1, -3, 0, 4, -5};
        System.out.println("\nTest case 4:");
        System.out.println("Before: " + Arrays.toString(arr4));
        reArrange(arr4);
        System.out.println("After:  " + Arrays.toString(arr4));

        // Test case 5: Empty array
        int[] arr5 = {};
        System.out.println("\nTest case 5:");
        System.out.println("Before: " + Arrays.toString(arr5));
        reArrange(arr5);
        System.out.println("After:  " + Arrays.toString(arr5));

        // Test case 6: Null array
        int[] arr6 = null;
        System.out.println("\nTest case 6:");
        System.out.println("Before: " + (arr6 == null ? "null" : Arrays.toString(arr6)));
        reArrange(arr6);
        System.out.println("After:  " + (arr6 == null ? "null" : Arrays.toString(arr6)));
    }
}