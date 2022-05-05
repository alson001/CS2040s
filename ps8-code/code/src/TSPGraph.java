import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class TSPGraph implements IApproximateTSP {

    private HashMap<Integer, Integer> parent;

    @Override
    public void MST(TSPMap map) {
        // TODO: implement this method
        TreeMapPriorityQueue<Double, Integer> treeMap = new TreeMapPriorityQueue<>();
        int count = map.getCount();
        //initialize priority queue
        for (int k = 0; k < count; k++) {
            treeMap.add(k, Double.MAX_VALUE);
        }
        treeMap.decreasePriority(0, 0.0);

        //initialize parent hashmap
        this.parent = new HashMap<>();
        parent.put(0, null);

        while (!treeMap.isEmpty()) {
            int temp = treeMap.extractMin();
            for (int i = 1; i < count; i++) {
                if (i != temp && treeMap.lookup(i) != null) {
                    double distance = map.pointDistance(temp, i);
                    if (treeMap.lookup(i) > distance) {
                        treeMap.decreasePriority(i, distance);
                        parent.put(i, temp);
                    }
                }
            }
        }

        for (int j = 1; j < count; j++) {
            int temp = parent.get(j);
            map.setLink(j, temp);
        }
    }

    @Override
    public void TSP(TSPMap map) {
        MST(map);
        // TODO: implement the rest of this method.
        List<Integer> edges = new ArrayList<>();
        edges.add(0);
        dfs(0, edges);
        for(int i = 0; i < edges.size() - 1 ; i++) {
            map.setLink(edges.get(i), edges.get(i+1));
        }
        map.setLink(edges.get(edges.size() - 1),0);
    }

    private void dfs(int index, List<Integer> list) {
        for (int i = 1; i < parent.size(); i++) {
            if (parent.get(i) == index) {
                list.add(i);
                dfs(i,list);
            }
        }
    }

    public boolean checkPath(int next, boolean[] visited) {
        if (next == -1) {
            return false;
        } else if (visited[next] == true) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isValidTour(TSPMap map) {
        // Note: this function should work with any map, and not just results from TSP().
        // TODO: implement this method

        boolean[] visited = new boolean[map.getCount()];
        int start = 0;
        int next = map.getLink(start);
        int edges = 1;
        while (next != start) {
            if (checkPath(next, visited)) {
                visited[next] = true;
                edges++;
                next = map.getLink(next);
            } else {
                return false;
            }
        }

        return next == 0 && edges == map.getCount();
    }

    public double distance(int point, double currDistance, TSPMap map) {
        int nextPoint = map.getPoint(point).getLink();
        double distanceToNextPoint = map.pointDistance(point,nextPoint);
        if(nextPoint == 0) {
            return currDistance + distanceToNextPoint;
        } else {
            return distance(nextPoint,currDistance+distanceToNextPoint, map);
        }
    }
    @Override
    public double tourDistance(TSPMap map) {
        // Note: this function should with with *any* map, and not just results from TSP().
        if(isValidTour(map)) {
            return distance(0, 0, map);
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        TSPMap map = new TSPMap(args.length > 0 ? args[0] : "C:\\Users\\alson\\Downloads\\ps8-code\\code\\hundredpoints.txt");
        TSPGraph graph = new TSPGraph();

        // graph.MST(map);
        // graph.TSP(map);
        System.out.println(graph.isValidTour(map));
        System.out.println(graph.tourDistance(map));
    }
}
