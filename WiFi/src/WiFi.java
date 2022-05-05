import java.util.Arrays;

class WiFi {

    /**
     * Implement your solution here
     */
    public static double computeDistance(int[] houses, int numOfAccessPoints) {
        Arrays.sort(houses);
        double maxDistance = houses[houses.length - 1] - houses[0];
        double minDistance = 0;
        while (minDistance != maxDistance) {
            double mid = minDistance + (maxDistance - minDistance) / 2;
            if (coverable(houses, numOfAccessPoints, mid)) {
                if (!coverable(houses, numOfAccessPoints, mid - 0.5)) {
                    return mid;
                }
                maxDistance = mid;
            } else {
                minDistance = mid;
            }
        }
        return maxDistance;
    }

    /**
     * Implement your solution here
     */
    public static boolean coverable(int[] houses, int numOfAccessPoints, double distance) {
        double midpoint = houses[0] + distance;
        numOfAccessPoints = numOfAccessPoints - 1;
        for (int i = 1; i < houses.length; i++) {
            if (houses[i] > midpoint + distance) {
                midpoint = houses[i] + distance;
                numOfAccessPoints = numOfAccessPoints - 1;
            }
        }
        if (numOfAccessPoints < 0) {
            return false;
        }
        return true;
    }
}
