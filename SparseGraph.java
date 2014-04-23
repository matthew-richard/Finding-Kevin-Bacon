import java.util.Collection;
import java.util.ArrayList;

/**
    A directed graph implementation optimized for sparse graphs.

    Incidence lists are used to keep track of adjacencies.

    @param <V> Type of vertex element
    @param <E> Type of edge element
*/
public final class SparseGraph<V, E> implements Graph<V, E> {
    private final class IncidenceVertex implements Vertex<V> {
        public Collection<DirectedEdge> outgoing;
        public Collection<DirectedEdge> incoming;

        public V data;
        public Object label;
        public boolean removed;

        public IncidenceVertex(V d) {
            this.outgoing = new ArrayList<DirectedEdge>();
            this.incoming = new ArrayList<DirectedEdge>();
            this.data = d;
            this.label = null;
            this.removed = false;
        }

        @Override
        public V get() { return this.data; }
        @Override
        public void put(V v) { this.data = v; }

        public SparseGraph<V, E> manufacturer() {
            return this.removed ? null : SparseGraph.this;
        }
    }

    private final class DirectedEdge implements Edge<E> {
        public IncidenceVertex from;
        public IncidenceVertex to;

        public E data;
        public Object label;
        public boolean removed;

        private DirectedEdge(IncidenceVertex f, IncidenceVertex t, E d) {
            this.from = f;
            this.to = t;
            this.data = d;
            this.label = null;
            this.removed = false;
        }

        // Easier to check for duplicate edges, using Collection.contains(),
        // if we override edges to compare only their to-from vertices and not
        // their data. Something similar was done in ChainingHashMap's Entry
        // subclass in the previous assignment (it only compares keys, not
        // data), so I'm assuming this is ok.
        @Override @SuppressWarnings("unchecked")
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            } else if (obj == this) {
                return true;
            } else if (!(obj instanceof SparseGraph.DirectedEdge)) {
                return false;
            }

            DirectedEdge e = (DirectedEdge) obj;
            return e.from.equals(this.from) && e.to.equals(this.to);
        }

        @Override
        public int hashCode() {
            E temp = this.data;
            this.data = null;
            int hash = super.hashCode();
            this.data = temp;
            return hash;
        }

        @Override
        public E get() { return this.data; }
        @Override
        public void put(E e) { this.data = e; }

        public SparseGraph<V, E> manufacturer() {
            return this.removed ? null : SparseGraph.this;
        }
    }

    private Collection<IncidenceVertex> vertices;
    private Collection<DirectedEdge> edges;

    /** New SparseGraph instance. */
    public SparseGraph() {
        this.vertices = new ArrayList<IncidenceVertex>();
        this.edges = new ArrayList<DirectedEdge>();
    }

    @SuppressWarnings("unchecked") // We're typesafe because we use instanceof.
    private IncidenceVertex validateVertex(Vertex<V> v)
        throws IllegalArgumentException {
        if (v == null) {
            throw new IllegalArgumentException("Inputted Vertex is null.");
        } else if (!(v instanceof SparseGraph.IncidenceVertex)) {
            throw new IllegalArgumentException("Inputted Vertex unusable"
                + "by SparseGraph.");
        }

        IncidenceVertex validee = (IncidenceVertex) v;
        if (!(validee.manufacturer() == this)) {
            throw new IllegalArgumentException("Inputted Vertex does not "
                + "belong to this SparseGraph.");
        }
        return validee;
    }

    @SuppressWarnings("unchecked") // We're typesafe because we use instanceof.
    private DirectedEdge validateEdge(Edge<E> e)
        throws IllegalArgumentException {
        if (e == null) {
            throw new IllegalArgumentException("Inputted Edge is null.");
        } else if (!(e instanceof SparseGraph.DirectedEdge)) {
            throw new IllegalArgumentException("Inputted Edge unusable "
                + "by SparseGraph.");
        }
        DirectedEdge validee = (DirectedEdge) e;
        if (!(validee.manufacturer() == this)) {
            throw new IllegalArgumentException("Inputted Edge does not"
                + "belong to this SparseGraph.");
        }
        return validee;
    }

    @Override
    public Vertex<V> insert(V v) {
        IncidenceVertex newVertex = new IncidenceVertex(v);
        this.vertices.add(newVertex);
        return newVertex;
    }

    @Override
    public Edge<E> insert(Vertex<V> from, Vertex<V> to, E e) {
        DirectedEdge insert;
        IncidenceVertex f;
        IncidenceVertex t;

        // validation
        Collection<DirectedEdge> search;
        if (from == to) {
            throw new IllegalArgumentException("Can't create self-loops.");
        }
        f = this.validateVertex(from);
        t = this.validateVertex(to);
        insert = new DirectedEdge(f, t, e);
        search = f.outgoing.size() <= t.incoming.size()
            ? f.outgoing : t.incoming;
        if (search.contains(insert)) {
            throw new IllegalArgumentException("Can't insert duplicate edges.");
        }

        f.outgoing.add(insert);
        t.incoming.add(insert);
        this.edges.add(insert);

        return insert;
    }

    @Override
    public V remove(Vertex<V> vertex) {
        // validation
        IncidenceVertex v = this.validateVertex(vertex);
        if (!(v.outgoing.isEmpty() && v.incoming.isEmpty())) {
            throw new IllegalArgumentException("Can't remove Vertex with"
                + "incoming or outgoing edges");
        }

        v.removed = true;
        this.vertices.remove(v);

        return v.data;
    }

    @Override
    public E remove(Edge<E> edge) {
        DirectedEdge e = this.validateEdge(edge);

        e.removed = true;
        e.from.outgoing.remove(e);
        e.to.incoming.remove(e);
        this.edges.remove(e);

        return e.data;
    }

    @Override
    public Iterable<Vertex<V>> vertices() {
        Collection<Vertex<V>> verts = new ArrayList<Vertex<V>>();
        verts.addAll(this.vertices);
        return verts;
    }

    @Override
    public Iterable<Edge<E>> edges() {
        Collection<Edge<E>> edgs = new ArrayList<Edge<E>>();
        edgs.addAll(this.edges);
        return edgs;
    }

    @Override
    public Iterable<Edge<E>> outgoing(Vertex<V> vertex) {
        IncidenceVertex v = this.validateVertex(vertex);
        Collection<Edge<E>> outs = new ArrayList<Edge<E>>();
        outs.addAll(v.outgoing);
        return outs;
    }

    @Override
    public Iterable<Edge<E>> incoming(Vertex<V> vertex) {
        IncidenceVertex v = this.validateVertex(vertex);
        Collection<Edge<E>> ins = new ArrayList<Edge<E>>();
        ins.addAll(v.incoming);
        return ins;
    }

    @Override
    public Vertex<V> from(Edge<E> edge) {
        DirectedEdge e = this.validateEdge(edge);
        return e.from;
    }

    @Override
    public Vertex<V> to(Edge<E> edge) {
        DirectedEdge e = this.validateEdge(edge);
        return e.to;
    }

    @Override
    public void label(Vertex<V> vertex, Object l) {
        IncidenceVertex v = this.validateVertex(vertex);
        if (l == null) {
            throw new IllegalArgumentException("Can't label null.");
        }
        v.label = l;
        return;
    }

    @Override
    public void label(Edge<E> edge, Object l) {
        DirectedEdge e = this.validateEdge(edge);
        if (l == null) {
            throw new IllegalArgumentException("Can't label null.");
        }
        e.label = l;
        return;
    }

    @Override
    public Object label(Vertex<V> vertex) {
        IncidenceVertex v = this.validateVertex(vertex);
        return v.label;
    }

    @Override
    public Object label(Edge<E> edge) {
        DirectedEdge e = this.validateEdge(edge);
        return e.label;
    }

    @Override
    public void clearLabels() {
        for (IncidenceVertex v : this.vertices) {
            v.label = null;
        }
        for (DirectedEdge e : this.edges) {
            e.label = null;
        }
        return;
    }

    /**
        Converts to GraphViz-compatible string format.

        @return The string.
    */
    public String toString() {
        String result = "digraph {\n";

        for (IncidenceVertex v : this.vertices) {
            result += "  \"" + v.data.toString() + "\";\n";
        }
        for (DirectedEdge e : this.edges) {
            result += "  \"" + e.from.data.toString() + "\""
                        + " -> "
                        + "\"" + e.to.data.toString() + "\""
                        + " [label=\"" + e.data.toString() + "\"];\n";
        }

        result += "}";
        return result;
    }
}
