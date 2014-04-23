import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

/**
    Six Degrees of Kevin Bacon.

    Do NOT USE MAPS in your code for BFS below! Trust us, you can
    make do just with the existing Graph interface and with Java's
    Queue interface (since BFS needs a queue internally).
*/
public final class Kevin {
    // Graph holding movies and actors as vertices, relationships
    // as edges. All are simply strings.
    private static Graph<String, String> graph =
        new SparseGraph<String, String>();

    // Vertices for the actor we're trying to connect to Kevin
    // Bacon and for Kevin Bacon himself.
    private static Vertex<String> actor = null;
    private static Vertex<String> bacon = null;

    // Shut up checkstyle.
    private Kevin() {}

    // Read input file and turn it into a Graph.
    //
    // There's one line for each movie, with the fields separated
    // by "/". The first field is the movie, the remaining fields
    // are actors.
    //
    // Generates a bipartite graph in which both movies and actors
    // are vertices. A graph in which all vertices are actors and
    // movies are edges would be HORRIBLE instead (why?).
    //
    // This function also sets up the "actor" and "bacon" globals
    // that will be used to direct the breadth-first-search.
    private static void readInput(String filename, String who)
        throws FileNotFoundException, IOException {
        // keep track of all vertices created so far by name
        Map<String, Vertex<String>> vertices = new HashMap<>();

        // how we read the input
        BufferedReader reader = new BufferedReader(new FileReader(
            new File(filename)));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] data = line.split("/");

            // find or create vertex for the movie
            Vertex<String> m = vertices.get(data[0]);
            if (m == null) {
                m = graph.insert(data[0]);
                vertices.put(data[0], m);
            }

            for (int i = 1; i < data.length; i++) {
                // find or create vertex for the actor
                Vertex<String> a = vertices.get(data[i]);
                if (a == null) {
                    a = graph.insert(data[i]);
                    vertices.put(data[i], a);
                }

                // double-check for special actors
                if (a.get().equals("Bacon, Kevin")) {
                    bacon = a;
                }
                if (a.get().equals(who)) {
                    actor = a;
                }

                // create two edges, from and to the movie
                graph.insert(m, a, "features");
                graph.insert(a, m, "acts in");
            }
        }
    }

    // Perform a breadth-first search (BFS) starting from Kevin Bacon
    // and stopping when (a) the graph is exhausted or (b) we found
    // the actor we're looking for. Then print the path from the actor
    // back to Kevin Bacon and exit the program. Since we're using BFS
    // we can be sure that the resulting path is among the shortest ones.
    private static void solveBacon() {
        Queue<Vertex<String>> q = new LinkedList<Vertex<String>>();
        q.add(bacon);
        graph.label(bacon, 0);
/*
        // find path
        Vertex<String> top = q.peek();
        while (top != actor) { // while queue !empty
            int distance = (Integer) graph.label(top) + 1;
            Iterable<Edge<String>> outs = graph.outgoing(top);
            for (Edge<String> e : outs) {
                Vertex<String> to = graph.to(e);
                if (graph.label(to) == null) {
                    q.add(to);
                    graph.label(to, distance);
                }
            }
            q.remove();
            top = q.peek();
        }

        // print path
        int doubleBacon = (Integer) graph.label(top);
        Vertex<String> curr = top;
        for (int i = doubleBacon; i >= 0; i--) {
            System.out.println(curr.get());
            Iterable<Edge<String>> ins = graph.incoming(curr);
            for (Edge<String> e : ins) {
                Vertex<String> v = graph.from(e);
                Integer label = (Integer) graph.label(v);
                if (label != null && label == i - 1) {
                    curr = v;
                    break;
                }
            }
        }
        System.out.println(doubleBacon / 2);
        System.exit(0);*/
    }

    /**
        Main method.
        @param args Command line arguments.
        @throws FileNotFoundException If database file cannot be opened.
        @throws IOException If database file cannot be read properly.
    */
    public static void main(String[] args)
        throws FileNotFoundException, IOException {
        // read the input, initialize globals
        readInput(args[0], args[1]);

        // check that we could find both actors, quit if not
        if (actor == null) {
            System.out.printf("Error: Can't find %s in database.\n", args[1]);
            System.exit(1);
        }
        if (bacon == null) {
            System.out.printf("Error: Can't find Bacon, Kevin in database.\n");
            System.exit(1);
        }

        // play "six degrees of Kevin Bacon" using breadth-first search
        solveBacon();
    }
}
