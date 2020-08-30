// Author: Pranesh Reddy Jambula

import java.util.*;

public class ShortestPath {

    public static Route minPath(String startLocation, String endLocation, Graph<String, Integer> graph) {

        Comparator<Route> comparator = new Comparator<Route>() {
            @Override
            public int compare(Route o1, Route o2) {
                return Integer.compare(o1.getDistance(), o2.getDistance());
            }
        };

        Queue<Route> active = new PriorityQueue<>(comparator);
        Set<String> finishedVertices = new HashSet<>();
        Route initialPath = new Route(startLocation);
        active.add(initialPath);

        while (!active.isEmpty()) {
            Route currentPath = active.remove();
            String currentDestination = currentPath.getDestination();

            if (currentDestination.equals(endLocation)) {
                return currentPath;
            }

            for (LabelledEdge<String, Integer> edges : graph.listOfLabelledEdge(currentDestination)) {
                if (!finishedVertices.contains(currentDestination)) {
                    Route updatedPath = currentPath.addConnection(edges.getVTo(), edges.getLabel());
                    active.add(updatedPath);
                }
            }
            finishedVertices.add(currentDestination);
        }
        return null;
    }
}
