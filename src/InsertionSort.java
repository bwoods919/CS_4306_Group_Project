import java.util.Arrays;

public class InsertionSort {
    
    public static void main(String[] args) {
        int[] array = {5, 2, 8, 4, 1, 9};
        System.out.println("Before sorting: " + Arrays.toString(array));
        insertionSort(array);
        System.out.println("After sorting: " + Arrays.toString(array));
    }
    
    public static void insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
            System.out.println(Arrays.toString(array)); // print array after each pass
        }
    }
}
