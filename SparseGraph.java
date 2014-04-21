/**
    A directed graph implementation optimized for sparse graphs.

    @param <V> Type of vertex element
    @param <E> Type of edge element
*/
public class SparseGraph<V, E> implements Graph<V, E> {
    @Override
    public Vertex<V> insert(V v) {
        return null;
    }

    @Override
    public Edge<E> insert(Vertex<V> from, Vertex<V> to, E e) {
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
