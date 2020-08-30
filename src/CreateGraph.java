// Author: Pranesh Reddy Jambula

public class CreateGraph {
    
    public static Graph<String, Integer> build(int size) {
        Graph<String, Integer> graph = new Graph<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                graph.addVertex(i + "," + j);
            }
        }

        // Connect corner points
        graph.addEdge(1, "0,0", "1,0");
        graph.addEdge(1, "0,0", "0,1");
        graph.addEdge(1, "0," + (size - 1), "0," + (size - 2));
        graph.addEdge(1, "0," + (size - 1), "1," + (size - 1));
        graph.addEdge(1, (size - 1) + ",0", (size - 2) + ",0");
        graph.addEdge(1, (size - 1) + ",0", (size - 1) + ",1");
        graph.addEdge(1, (size - 1) + "," + (size - 1), (size - 2) + "," + (size - 1));
        graph.addEdge(1, (size - 1) + "," + (size - 1), (size - 1) + "," + (size - 2));

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0 && (j > 0 && j < (size - 1))) {
                    graph.addEdge(1, i + "," + j, "0," + (j - 1));
                    graph.addEdge(1, i + "," + j, "1," + j);
                    graph.addEdge(1, i + "," + j, "0," + (j + 1));
                } else if (j == 0 && (i > 0 && i < (size - 1))) {
                    graph.addEdge(1, i + ",0", (i - 1) + ",0");
                    graph.addEdge(1, i + ",0", (i + 1) + ",0");
                    graph.addEdge(1, i + ",0", i + ",1");
                } else if (i == (size - 1) && (j > 0 && j < (size - 1))) {
                    graph.addEdge(1, i + "," + j, i + "," + (j - 1));
                    graph.addEdge(1, i + "," + j, i + "," + (j + 1));
                    graph.addEdge(1, i + "," + j, (i - 1) + "," + j);
                } else if (j == (size - 1) && (i > 0 && i < (size - 1))) {
                    graph.addEdge(1, i + "," + j, (i - 1) + "," + j);
                    graph.addEdge(1, i + "," + j, (i + 1) + "," + j);
                    graph.addEdge(1, i + "," + j, i + "," + (j - 1));
                }

                if ((i > 0 && i < (size - 1)) && (j > 0 && j < (size - 1))) {
                    graph.addEdge(1, i + "," + j, i + "," + (j - 1));
                    graph.addEdge(1, i + "," + j, i + "," + (j + 1));
                    graph.addEdge(1, i + "," + j, (i - 1) + "," + j);
                    graph.addEdge(1, i + "," + j, (i + 1) + "," + j);
                }
            }
        }
        return graph;
    }

    public static void main(String[] args) {
        Graph<String, Integer> g = build(4);
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                System.out.println("(" + i + "," + j +"): " + g.childVertices(i + "," + j));
            }
        }
    }
}