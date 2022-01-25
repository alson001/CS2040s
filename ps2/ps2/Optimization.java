/**
 * The Optimization class contains a static routine to find the maximum in an array that changes direction at most once.
 */
public class Optimization {

    /**
     * A set of test cases.
     */
    static int[][] testCases = {
            {1, 3, 5, 7, 9, 11, 10, 8, 6, 4},
            {67, 65, 43, 42, 23, 17, 9, 100},
            {4, -100, -80, 15, 20, 25, 30},
            {2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83}
    };

    /**
     * Returns the maximum item in the specified array of integers which changes direction at most once.
     *
     * @param dataArray an array of integers which changes direction at most once.
     * @return the maximum item in data Array
     */
    public static int searchMax(int[] dataArray) {
        // TODO: Implement this
        int low = 0;
        int max = dataArray.length - 1;
        int midtemp = low + (max-low)/2;
        /* if its first decreasing then increasing and max is larger than low, it means the max is always going to be
        the largest. However, we need to check if max is more than mid as it can still first increase then decrease
        which can cause the low to be smaller than max in certain cases.
         */
        if(dataArray[max] > dataArray[low] && dataArray[max] > dataArray[midtemp]){
            return dataArray[max];}
        while(low != max){
            if((max == low + 1) && dataArray[low] >= dataArray[max]){
                return dataArray[low];
            }
            if((max == low + 1) && dataArray[low] < dataArray[max]){
                return dataArray[max];
            }
            int mid = low + (max-low)/2;
            if(dataArray[mid] > dataArray[mid + 1] && dataArray[mid] > dataArray[mid - 1]){
                return dataArray[mid];
            }
            if(dataArray[mid] > dataArray[mid + 1] && dataArray[mid] < dataArray[mid - 1]){
                max = mid;
            }
            else {
                low = mid + 1;
            }
        }
        return dataArray[low];
    }
    /**
     * A routine to test the searchMax routine.
     */
    public static void main(String[] args) {
        for (int[] testCase : testCases) {
            System.out.println(searchMax(testCase));
        }
    }
}
