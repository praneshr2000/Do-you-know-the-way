// Author: Pranesh Reddy Jambula

import java.util.*;

/**
 * Graph represent an object that essentially is a collection of 'vertices' or 'nodes'
 * connected together by 'edges'.
 *
 * The edges that are connect a parent and a child vertex are directed. Each vertex pair has
 * at least 0 edges connecting them.
 *
 * Each edge of the graph will contain a certain 'label'. There can be more than one
 * edges that that may have the same label. However, no 2 edges that have the same parent and child
 * vertex can have the same label. Each node will also have unique data stored.
 *
 * Example: V1 --A-- V2 --B-- V3 --C-- V4 is a Graph where nodes V1 is connected to V2 by edge
 *          labelled A, V2 is connected to V3 by edge labelled B and V3 is connected to V4 by edge
 *          labelled C.
 *
 * @param <V> the types of Vertices maintained by this Graph
 * @param <E> the types of Edges maintained by this Graph
 */
public class Graph<V, E extends Comparable<E>> {

    private final Map<V, Set<LabelledEdge<V, E>>> graphMap;

    private static final boolean CHECK_REP = false;

    // Abstraction Function:
    // AF(this) =
    // A map storing set of vertices and it's corresponding edges representing a directed graph
    // If the graph is empty, this.graphMap = []
    // If the graph contains nodes and edges, this.graphMap = [v_1 : [<v_2, e_1>]], where
    // v_1 is a vertex connected to vertex v_2 by edge labelled e_1
    // More generally, this.graphMap = [v_1 : [<v_x, e_y>,....], .... , v_i : [<v_j, e_a>,....]]
    // where 1 <= x, i, j <= number of vertices in graph and
    // 1 <= y, a <= number of edges in the graph

    // Representation Invariant:
    // graphMap != null &&
    // for all elements(graphMap), v_x != null. (where v_x represents a single element in graphMap) &&
    // for all elements(graphMap), E(v_x) != null. (where E(v_x) indicates all the edges going from vertex v_x).
    // In other words, the graph should not be null, all the vertices present in the graph should not be null
    // and corresponding edges to each vertex in the graph should not be null

    /**
     * @spec.effects Constructs an empty Graph
     */
    public Graph() {
        graphMap = new HashMap<>();
        checkRep();
    }

    /**
     * Constructs a new graph with one vertex containing specified 'data'
     *
     * @param data the vertex to be added in the graph
     * @spec.requires data != null
     * @spec.effects Constructs a graph with a vertex
     */
    public Graph(V data) {
        graphMap = new HashMap<>();
        Set<LabelledEdge<V, E>> edgeList = new TreeSet<>();
        graphMap.put(data, edgeList);
        checkRep();
    }

    /**
     * Adds a vertex into the graph with given 'data'
     *
     * @param data the vertex that is to be added to the graph.
     * @throws IllegalArgumentException if vertex already exists in the graph
     * @spec.requires data != null
     * @spec.modifies this
     * @spec.effects adds a new vertex to the graph
     */
    public void addVertex(V data) {
        if (containsVertex(data)) {
            throw new IllegalArgumentException("Vertex to be added already present in the graph");
        }
        Set<LabelledEdge<V, E>> edgeList = new TreeSet<>();
        graphMap.put(data, edgeList);
        checkRep();
    }

    /**
     * Adds an edge between two vertices in the graph with 'label' as the label
     *
     * @param label the edge to be added
     * @param vFrom the starting vertex of the edge to be added
     * @param vTo the vertex that the new edge connects to from the start vertex
     * @spec.requires label != null, vFrom != null, vTo != null
     * @throws IllegalArgumentException if vertex vFrom or vTo are not contained in the graph
     * @spec.modifies this
     * @spec.effects adds a new edge between a pair of vertices in the graph
     */
    public void addEdge(E label, V vFrom, V vTo) {
        if (!containsVertex(vFrom)) {
            throw new IllegalArgumentException("Parent vertex of the edge to be " +
                                               "added not present in the graph");
        }

        if (!containsVertex(vTo)) {
            throw new IllegalArgumentException("Child vertex of the edge to be " +
                                               "added not present in the graph");
        }
        LabelledEdge<V, E> newEdge = new LabelledEdge<V, E>(label, vTo);
        graphMap.get(vFrom).add(newEdge);
        checkRep();
    }

    /**
     * Removes a vertex from the graph with given 'data'
     *
     * @param data the vertex that is to be removed from the graph.
     * @throws IllegalArgumentException if vertex is not present in the graph
     * @spec.requires data != null
     * @spec.modifies this
     * @spec.effects removes vertex from the graph
     */
    public void removeVertex(V data) {
        if (!containsVertex(data)) {
            throw new IllegalArgumentException("Vertex not present in the graph");
        }
        graphMap.remove(data);
        checkRep();
    }

    /**
     * Removes an edge between two vertices in the graph with 'label' as the edge label
     *
     * @param label the edge to be removed
     * @param vFrom the starting vertex of the edge to be removed
     * @param vTo the vertex that the edge connects to from the start vertex
     * @spec.requires label != null, vFrom != null, vTo != null
     * @throws IllegalArgumentException if vertex vFrom or vTo are not contained in the graph
     *         or the edge with 'label' does not exist
     * @spec.modifies this
     * @spec.effects removes an edge between a pair of vertices in the graph
     */
    public void removeEdge(E label, V vFrom, V vTo) {
        if (!containsVertex(vFrom)) {
            throw new IllegalArgumentException("Parent vertex of the edge to be " +
                                               "removed not present in the graph");
        }

        if (!containsVertex(vTo)) {
            throw new IllegalArgumentException("Child vertex of the edge to be " +
                                               "removed not present in the graph");
        }

        LabelledEdge<V, E> edgeToRemove = new LabelledEdge<V, E>(label, vTo);
        if (!graphMap.get(vFrom).contains(edgeToRemove)) {
            throw new IllegalArgumentException("Edge with given label between vertices not present");
        }

        graphMap.get(vFrom).remove(edgeToRemove);
        checkRep();
    }

    /**
     * Checks if a given vertex is present in the graph
     *
     * @param vertex the desired vertex to be checked if present in the graph
     * @spec.requires vertex != null
     * @return true if vertex present in the graph, else false
     */
    public boolean containsVertex(V vertex) {
        checkRep();
        return graphMap.containsKey(vertex);
    }

    /**
     * Returns all the Vertices that connects to a given vertex
     *
     * @param from vertex of which we want the children of
     * @spec.requires from != null
     * @throws IllegalArgumentException if 'from' is not present in the graph
     * @return vertices that 'from' joins to
     */
    public List<String> childVertices(V from) {
        if (!graphMap.containsKey(from)) {
            throw new IllegalArgumentException("Vertex not present in the graph");
        }

        List<String> returnList = new ArrayList<>();
        for (LabelledEdge<V, E> children : graphMap.get(from)) {
            returnList.add(children.toString());
        }
        checkRep();
        return Collections.unmodifiableList(returnList);
    }


    /**
     * Returns all vertices present in the graph
     *
     * @return vertices in the graph
     */
    public Set<V> listVertices() {
        checkRep();
        return Collections.unmodifiableSet(graphMap.keySet());
    }

    /**
     * Returns all the vertices and it's corresponding edges
     *
     * @return edges and the corresponding vertices present in the graph
     */
    public String toString() {
        checkRep();
        return graphMap.toString();
    }

    /**
     * Returns set of LabelledEdge of a parent vertex
     *
     * @param from parent vertex whose LabelledEdges are returned
     * @return set of labelled edges
     */
    public Set<LabelledEdge<V, E>> listOfLabelledEdge(V from) {
        if (!graphMap.containsKey(from)) {
            throw new IllegalArgumentException("Vertex not present in the graph");
        }
        return Collections.unmodifiableSet(graphMap.get(from));
    }

    /**
     * Throws an exception if the representation invariant is violated
     */
    private void checkRep() {
        assert (graphMap != null);
        if (CHECK_REP) {
            for(V vertex : graphMap.keySet()) {
                assert (vertex != null) : "vertex is null";
                for (LabelledEdge<V, E> edge : graphMap.get(vertex)) {
                    assert (edge != null) : "edge is null";
                }
            }
        }
    }

}
