import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.experimental.theories.DataPoint;
import org.junit.runner.RunWith;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Iterator;


@RunWith (Theories.class)
public class TestGraph {
    private interface Fixture {
        Graph<String, Integer> init();
    }

    @DataPoint
    public static final Fixture sparse = new Fixture() {
        public Graph<String, Integer> init() {
            return new SparseGraph<String, Integer>();
        }
    };

    @Theory
    public void verticesInitiallyEmpty(Fixture f) {
        Graph<String, Integer> g = f.init();
        assertTrue(!g.vertices().iterator().hasNext());
    }

    @Theory
    public void edgesInitiallyEmpty(Fixture f) {
        Graph<String, Integer> g = f.init();
        assertTrue(!g.edges().iterator().hasNext());
    }

    @Theory
    public void insertedVertexInVertices(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> ins = g.insert("Inserted");
        assertEquals(g.vertices().iterator().next(), ins);
    }
   
    @Theory
    public void insertedVertexNoOutgoing(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> ins = g.insert("Inserted");
        assertTrue(!g.outgoing(ins).iterator().hasNext());
    }
   
    @Theory
    public void insertedVertexNoIncoming(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> ins = g.insert("Inserted");
        assertTrue(!g.incoming(ins).iterator().hasNext());
    }

    @Theory
    public void insertedVertexNoLabel(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> ins = g.insert("Inserted");
        assertEquals(g.label(ins), null);
    }

    @Theory
    public void insertedVertexCorrectValue(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> ins = g.insert("Inserted");
        assertEquals(ins.get(), "Inserted");
    }

    @Theory
    public void puttedVertexCorrectValue(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> ins = g.insert("Inserted");
        ins.put("Putted");
        assertEquals(ins.get(), "Putted");
    }

    @Theory
    public void removedVertexNotInVertices(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> ins = g.insert("Inserted");
        g.remove(ins);
        assertTrue(!g.vertices().iterator().hasNext());
    }
    
    @Theory
    public void removedEdgeNotInEdges(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = g.insert("Bye");
        Edge<Integer> ins = g.insert(a, b, 43);
        g.remove(ins);
        assertTrue(!g.edges().iterator().hasNext());
    }


    @Theory
    public void insertedEdgeInEdges(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = g.insert("Bye");
        Edge<Integer> ins = g.insert(a, b, 43);
        assertEquals(g.edges().iterator().next(), ins);
    }

    @Theory
    public void insertedEdgeFrom(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = g.insert("Bye");
        Edge<Integer> ins = g.insert(a, b, 43);
        assertEquals(g.from(ins), a);
    }

    @Theory
    public void insertedEdgeTo(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = g.insert("Bye");
        Edge<Integer> ins = g.insert(a, b, 43);
        assertEquals(g.to(ins), b);
    }

    @Theory
    public void insertedEdgeOutgoing(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = g.insert("Bye");
        Edge<Integer> ins = g.insert(a, b, 43);
        assertEquals(g.outgoing(a).iterator().next(), ins);
    }

    @Theory
    public void insertedEdgeIncoming(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = g.insert("Bye");
        Edge<Integer> ins = g.insert(a, b, 43);
        assertEquals(g.incoming(b).iterator().next(), ins);
    }

    @Theory
    public void insertedEdgeCorrectValue(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = g.insert("Bye");
        Edge<Integer> ins = g.insert(a, b, 43);
        assertEquals((int)ins.get(), 43);
    }

    @Theory
    public void puttedEdgeCorrectValue(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = g.insert("Bye");
        Edge<Integer> ins = g.insert(a, b, 43);
        ins.put(12);
        assertEquals((int)ins.get(), 12);
    }

    @Theory
    public void insertedEdgeNoLabel(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = g.insert("Bye");
        Edge<Integer> ins = g.insert(a, b, 43);
        assertEquals(g.label(ins), null);
    }

    @Theory
    public void labelVertexAssignsLabel(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> ins = g.insert("Hi");
        g.label(ins, true);
        assertEquals(g.label(ins), true);
    }

    @Theory
    public void labelEdgeAssignsLabel(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = g.insert("Bye");
        Edge<Integer> ins = g.insert(a, b, 43);
        g.label(ins, true);
        assertEquals(g.label(ins), true);
    }

    @Theory
    public void clearLabelsClearsOnAllVerticesAndEdges(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = g.insert("Bye");
        Edge<Integer> c = g.insert(a, b, 43);
        g.label(a, true);
        g.label(b,true);
        g.label(c, true);
        g.clearLabels();
        assertEquals(g.label(a), null);
        assertEquals(g.label(b), null);
        assertEquals(g.label(b), null);
    }


    // Testing exception-throwing:

    @Theory @Test (expected=IllegalArgumentException.class)
    public void cantInsertDuplicateEdge(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = g.insert("Bye");
        Edge<Integer> ins = g.insert(a, b, 43);
        Edge<Integer> dup = g.insert(a, b, 12);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void noSelfLoops(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hello");
        Edge<Integer> ins = g.insert(a, a, 36);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void cantRemoveVertexWithOutgoing(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = g.insert("Bye");
        Edge<Integer> ins = g.insert(a, b, 43);
        g.remove(a);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void cantRemoveVertexWithIncoming(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = g.insert("Bye");
        Edge<Integer> ins = g.insert(a, b, 43);
        g.remove(b);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void insertEdgeStopsNull(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Edge<Integer> ins = g.insert(a, null, 43);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void insertEdgeStopsRemoved(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = g.insert("Bye");
        g.remove(b);
        Edge<Integer> ins = g.insert(a, b, 43);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void insertEdgeStopsForeign(Fixture f) {
        Graph<String, Integer> g = f.init();
        Graph<String, Integer> h = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = h.insert("Bye");
        Edge<Integer> ins = g.insert(a, b, 43);
    }


    @Theory @Test (expected=IllegalArgumentException.class)
    public void removeVertexStopsNull(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        g.remove((Vertex<String>)null);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void removeVertexStopsRemoved(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        g.remove(a);
        g.remove(a);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void removeVertexStopsForeign(Fixture f) {
        Graph<String, Integer> g = f.init();
        Graph<String, Integer> h = f.init();
        Vertex<String> a = h.insert("Hi");
        g.remove(a);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void removeEdgeStopsNull(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = g.insert("Bye");
        Edge<Integer> ins = g.insert(a, b, 43);
        g.remove((Edge<Integer>) null);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void removeEdgeStopsRemoved(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = g.insert("Bye");
        Edge<Integer> ins = g.insert(a, b, 43);
        g.remove(ins);
        g.remove(ins);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void removeEdgeStopsForeign(Fixture f) {
        Graph<String, Integer> g = f.init();
        Graph<String, Integer> h = f.init();
        Vertex<String> a = h.insert("Hi");
        Vertex<String> b = h.insert("Bye");
        Edge<Integer> ins = h.insert(a, b, 43);
        g.remove(ins);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void outgoingStopsNull(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        g.outgoing(null);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void outgoingStopsRemoved(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        g.remove(a);
        g.outgoing(a);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void outgoingStopsForeign(Fixture f) {
        Graph<String, Integer> g = f.init();
        Graph<String, Integer> h = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = h.insert("Bye");
        g.outgoing(b);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void incomingStopsNull(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        g.incoming(null);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void incomingStopsRemoved(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        g.remove(a);
        g.incoming(a);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void incomingStopsForeign(Fixture f) {
        Graph<String, Integer> g = f.init();
        Graph<String, Integer> h = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = h.insert("Bye");
        g.incoming(b);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void fromStopsNull(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = g.insert("Bye");
        Edge<Integer> ins = g.insert(a, b, 43);
        g.from(null);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void fromStopsRemoved(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = g.insert("Bye");
        Edge<Integer> ins = g.insert(a, b, 43);
        g.remove(ins);
        g.from(ins);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void fromStopsForeign(Fixture f) {
        Graph<String, Integer> g = f.init();
        Graph<String, Integer> h = f.init();
        Vertex<String> a = h.insert("Hi");
        Vertex<String> b = h.insert("Bye");
        Edge<Integer> ins = h.insert(a, b, 43);
        g.from(ins);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void toStopsNull(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = g.insert("Bye");
        Edge<Integer> ins = g.insert(a, b, 43);
        g.to(null);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void toStopsRemoved(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = g.insert("Bye");
        Edge<Integer> ins = g.insert(a, b, 43);
        g.remove(ins);
        g.to(ins);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void toStopsForeign(Fixture f) {
        Graph<String, Integer> g = f.init();
        Graph<String, Integer> h = f.init();
        Vertex<String> a = h.insert("Hi");
        Vertex<String> b = h.insert("Bye");
        Edge<Integer> ins = h.insert(a, b, 43);
        g.to(ins);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void labelVertexStopsNull(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        g.label((Vertex<String>)null, true);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void labelVertexStopsRemoved(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        g.remove(a);
        g.label(a, true);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void labelVertexStopsForeign(Fixture f) {
        Graph<String, Integer> g = f.init();
        Graph<String, Integer> h = f.init();
        Vertex<String> b = h.insert("Bye");
        g.label(b, true);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void labelEdgeStopsNull(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = g.insert("Bye");
        Edge<Integer> ins = g.insert(a, b, 43);
        g.label((Edge<Integer>)null, true);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void labelEdgeStopsRemoved(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = g.insert("Bye");
        Edge<Integer> ins = g.insert(a, b, 43);
        g.remove(ins);
        g.label(ins, true);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void labelEdgeStopsForeign(Fixture f) {
        Graph<String, Integer> g = f.init();
        Graph<String, Integer> h = f.init();
        Vertex<String> a = h.insert("Hi");
        Vertex<String> b = h.insert("Bye");
        Edge<Integer> ins = h.insert(a, b, 43);
        g.label(ins, true);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void getLabelVertexStopsNull(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        g.label((Vertex<String>)null);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void getLabelVertexStopsRemoved(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        g.remove(a);
        g.label(a);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void getLabelVertexStopsForeign(Fixture f) {
        Graph<String, Integer> g = f.init();
        Graph<String, Integer> h = f.init();
        Vertex<String> a = h.insert("Hi");
        g.label(a);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void getLabelEdgeStopsNull(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = g.insert("Bye");
        Edge<Integer> ins = g.insert(a, b, 43);
        g.label((Edge<Integer>)null);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void getLabelEdgeStopsRemoved(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = g.insert("Bye");
        Edge<Integer> ins = g.insert(a, b, 43);
        g.remove(ins);
        g.label(ins);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void getLabelEdgeStopsForeign(Fixture f) {
        Graph<String, Integer> g = f.init();
        Graph<String, Integer> h = f.init();
        Vertex<String> a = h.insert("Hi");
        Vertex<String> b = h.insert("Bye");
        Edge<Integer> ins = h.insert(a, b, 43);
        g.label(ins);
    } 
    
    @Theory @Test (expected=IllegalArgumentException.class)
    public void cantAssignNullLabelVertex(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> ins = g.insert("Hi");
        g.label(ins, null);
    }

    @Theory @Test (expected=IllegalArgumentException.class)
    public void cantAssignNullLabelEdge(Fixture f) {
        Graph<String, Integer> g = f.init();
        Vertex<String> a = g.insert("Hi");
        Vertex<String> b = g.insert("Bye");
        Edge<Integer> ins = g.insert(a, b, 43);
        g.label(ins, null);
    }
}
