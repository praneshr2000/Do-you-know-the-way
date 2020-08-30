// Author: Pranesh Reddy Jambula

import java.util.ArrayList;
import java.util.List;

public class Route {
    private int distance;
    private String startPoint;
    private List<Connection> route;

    public Route(String startPoint){
        this.startPoint = startPoint;
        this.distance = 0;
        this.route = new ArrayList<>();
    }

    public String getStart() {
        return this.startPoint;
    }

    public int getDistance(){
        return this.distance;
    }

    public String getDestination(){
        if (route.isEmpty()){
            return startPoint;
        }
        return route.get(route.size() - 1).getDestination();
    }

    public Route addConnection(String newDestination, int connectionDistance) {
        Route newRoute = new Route(startPoint);
        newRoute.route.addAll(this.route);
        newRoute.route.add(new Connection(this.getDestination(), newDestination, connectionDistance));
        newRoute.distance = this.distance + connectionDistance;
        return newRoute;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(startPoint);
        for(Connection connec : route) {
        	sb.append(";");
            sb.append(connec.getDestination());
        }
        return sb.toString();
    }



    public class Connection{
        private String start;
        private int distance;
        private String destination;

        public Connection(String start, String destination, int distance) {
            this.start = start;
            this.destination = destination;
            this.distance = distance;
        }

        public String getStart() {
            return start;
        }

        public String getDestination(){
            return destination;
        }

        public int getDistance() {
            return distance;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            sb.append(start.toString());
            sb.append(" -> ");
            sb.append(destination.toString());
            sb.append(" (");
            sb.append(distance + "");
            sb.append(")]");
            return sb.toString();
        }

    }
}