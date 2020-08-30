// Author: Pranesh Reddy Jambula

/**
 * LabelledEdge object represents an edge or a connection between nodes.
 * This will denote a connection to a certain child vertex and has a certain label
 * for identification.
 *
 * @param <E> the type of edge connection
 * @param <V> the type of child vertex
 */
public class LabelledEdge<V, E extends Comparable<E>>
                         implements Comparable<LabelledEdge<V, E>> {
    private final E label;
    private final V vTo;

    // Abstraction function:
    // AF(this) =
    // A labelled edge storing an edge represented by this.label
    // and it's target vertex represented by this.vTo

    // Representation Invariant:
    // label != null && vTo != null

    /**
     * Constructs a new labelled edge to vertex 'vTo' with a given 'label'
     *
     * @param label label of the edge to be added
     * @param vTo vertex to which the edge is to be added
     * @spec.requires label != null and vTo != null
     * @spec.modifies this
     */
    public LabelledEdge(E label, V vTo) {
        this.label = label;
        this.vTo = vTo;
        checkRep();
    }

    /**
     * Gives the label of the edge
     *
     * @return edge label
     */
    public E getLabel() {
        checkRep();
        return label;
    }

    /**
     * Gives the target vertex of the edge
     *
     * @return edge's target vertex
     */
    public V getVTo() {
        checkRep();
        return vTo;
    }

    /**
     * Returns a string representation of the edge in a format: "vertex(label)"
     * Valid example outputs are "v1(l1)", "v2(l2)"
     *
     * @return edge and vertex it goes to
     */
    public String toString() {
        checkRep();
        return vTo + "(" + label + ")";
    }

    /**
     * Compares this LablledEdge object to another LabelledEdge object
     *
     * @param other the object that we are comparing to
     * @spec.requires other != null
     * @return integer greater than 0, if this object is greater than the other object
     *         0, if the objects are equal
     *         integer less than 0, if this object is less than the other object
     */
    public int compareTo(LabelledEdge<V, E> other) {
        checkRep();
        if (getVTo().equals(other.getVTo())) {
            return getLabel().compareTo(other.getLabel());
        } else {
            return getVTo().toString().compareTo(other.getVTo().toString());
        }
    }

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() {
        assert (vTo != null);
        assert (label != null);
    }

}

