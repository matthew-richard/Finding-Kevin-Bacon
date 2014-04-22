import java.util.ArrayList;

/**
    A directed graph implementation optimized for sparse graphs.

    @param <V> Type of vertex element
    @param <E> Type of edge element
*/
public class SparseGraph<V, E> implements Graph<V, E> {
    private class IncidenceVertex implements Vertex<V> {
        public IncidenceVertex(V d) {
            this.outgoing = new ArrayList<DirectedEdge>();
            this.incoming = new ArrayList<DirectedEdge>();
            this.data = d;
            this.label = null;
            this.removed = false;
        }

        public ArrayList<DirectedEdge> outgoing;
        public ArrayList<DirectedEdge> incoming;

        public V data;
        public Object label;
        public boolean removed;

        @Override
        public V get() { return data; }
        @Override
        public void put(V v) { data = v; }

        public SparseGraph<V, E> manufacturer() {
            return removed ? null : SparseGraph.this;
        }
    }

    private class DirectedEdge implements Edge<E> {
        private DirectedEdge(IncidenceVertex f, IncidenceVertex t, E d) {
            this.from = f;
            this.to = t;
            this.data = d;
            this.label = null;
            this.removed = false;
        }

        public IncidenceVertex from;
        public IncidenceVertex to;

        public E data;
        public Object label;
        public boolean removed;

        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (obj == this)
                return true;
            if (!(obj instanceof SparseGraph.IncidenceVertex))
                return false;

            DirectedEdge e = (DirectedEdge) obj;
            return e.from.equals(this.from) && e.to.equals(this.to);
        }

        @Override
        public E get() { return data; }
        @Override
        public void put(E e) { data = e; }

        public SparseGraph<V, E> manufacturer() {
            return removed ? null : SparseGraph.this;
        }
    }

    private ArrayList<IncidenceVertex> vertices;
    private ArrayList<DirectedEdge> edges;

    /*private <T extends Position<?>> T validatePosition(Position<?> p)
        throws IllegalArgumentException {
        if (p == null)
            throw new IllegalArgumentException("Inputted Vertex is null.");
        else if (!(p instanceof T))
            throw new IllegalArgumentException("Inputted Vertex unusable by SparseGraph.");
        T validee = p;
        if (!(v.manufacturer == this))
            throw new IllegalArgumentException("Inputted Vertex does not"
                + "belong to this SparseGraph.");
        return validee;
    }*/
    private IncidenceVertex validateVertex(Vertex<V> v)
        throws IllegalArgumentException {
        /*if (v == null)
            throw new IllegalArgumentException("Inputted Vertex is null.");
        else*/ if (!(v instanceof SparseGraph.IncidenceVertex))
            throw new IllegalArgumentException("Inputted Vertex unusable by SparseGraph.");
        IncidenceVertex validee = (IncidenceVertex) v;
        /*if (!(validee.manufacturer() == this))
            throw new IllegalArgumentException("Inputted Vertex does not"
                + "belong to this SparseGraph.");*/
        return validee;
    }

    private DirectedEdge validateEdge(Edge<E> e)
        throws IllegalArgumentException {
        if (e == null)
            throw new IllegalArgumentException("Inputted Edge is null.");
        else if (!(e instanceof SparseGraph.DirectedEdge))
            throw new IllegalArgumentException("Inputted Edge unusable by SparseGraph.");
        DirectedEdge validee = (DirectedEdge)e;
        if (!(validee.manufacturer() == this))
            throw new IllegalArgumentException("Inputted Edge does not"
                + "belong to this SparseGraph.");
        return validee;
    }

    public SparseGraph() {
        this.vertices = new ArrayList<IncidenceVertex>();
        this.edges = new ArrayList<DirectedEdge>();
    } 

    @Override
    public Vertex<V> insert(V v) {
        IncidenceVertex newVertex = new IncidenceVertex(v);
        this.vertices.add(newVertex);
        return newVertex;
    }

    @Override
    public Edge<E> insert(Vertex<V> from, Vertex<V> to, E e) {
        DirectedEdge toInsert;
        IncidenceVertex f;
        IncidenceVertex t;
        ArrayList<DirectedEdge> toSearch;
        if (from == to)
            throw new IllegalArgumentException("Can't create self-loops.");
        f = this.validateVertex(from);
        t = this.validateVertex(to);
        toInsert = new DirectedEdge(f, t, e);
        toSearch = f.outgoing.size() <= t.incoming.size() ? f.outgoing : t.incoming;
        if (toSearch.contains(toInsert))
            throw new IllegalArgumentException("Can't insert duplicate edges.");
        return null;
    }

    @Override
    public V remove(Vertex<V> v) {
        return null;
    }

    @Override
    public E remove(Edge<E> e) {
        return null;
    }

    @Override
    public Iterable<Vertex<V>> vertices() {
        return null;
    }

    @Override
    public Iterable<Edge<E>> edges() {
        return null;
    }

    @Override
    public Iterable<Edge<E>> outgoing(Vertex<V> v) {
        return null;
    }

    @Override
    public Iterable<Edge<E>> incoming(Vertex<V> v) {
        return null;
    }

    @Override
    public Vertex<V> from(Edge<E> e) {
        return null;
    }

    @Override
    public Vertex<V> to(Edge<E> e) {
        return null;
    }

    @Override
    public void label(Vertex<V> v, Object l) {
        return;
    }

    @Override
    public void label(Edge<E> e, Object l) {
        return;
    }

    @Override
    public Object label(Vertex<V> v) {
        return null;
    }

    @Override
    public Object label(Edge<E> e) {
        return null;
    }

    @Override
    public void clearLabels() {
        return;
    }

    /**
        Converts to GraphViz-compatible string format.
    */
    public String toString() {
        return null;
    }
}
