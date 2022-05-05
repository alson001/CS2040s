import java.util.Arrays;

class InversionCounter {

    public static long countSwaps(int[] arr) {
        return mergeSortAndCount(arr, 0, arr.length - 1);
    }
    private static long mergeSortAndCount(int[] arr, int l, int r) {
        if (r - l <= 0) { return 0;}
            int mid = l + (r - l)/2;
            int left2 = mid + 1;
            return  mergeSortAndCount(arr, l, mid) + mergeSortAndCount(arr, left2, r) +
                    mergeAndCount(arr, l, mid, left2, r );
    }

    /**
     * Given an input array so that arr[left1] to arr[right1] is sorted and arr[left2] to arr[right2] is sorted
     * (also left2 = right1 + 1), merges the two so that arr[left1] to arr[right2] is sorted, and returns the
     * minimum amount of adjacent swaps needed to do so.
     */
    public static long mergeAndCount(int[] arr, int left1, int right1, int left2, int right2) {
        long numberOfSwaps = 0;
        int k = left1, j = left2;
        int[] temp = new int[right2 - left1 + 1];
        for (int i = 0; i < temp.length; i++) {
            if (j > right2) {
                temp[i] = arr[k];
                k += 1;
                continue;
            } else if (k > right1) {
                temp[i] = arr[j];
                j += 1;
                continue;
            }
            int leftSize = right1 - k + 1;
            if (arr[j] < arr[k]) {
                temp[i] = arr[j];
                j += 1;
                numberOfSwaps += leftSize;
            } else {
                temp[i] = arr[k];
                k += 1;
            }
        }
        for (int i = 0; i < temp.length; i++) {
            arr[i + left1] = temp[i];

        }
        return numberOfSwaps;
    }
}
