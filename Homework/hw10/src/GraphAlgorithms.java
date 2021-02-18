import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various graph algorithms.
 *
 * @author Hye Lim Kim
 * @version 1.0
 * @userid hkim946
 * @GTID 903197983
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !(graph.getAdjList().containsKey(start))) {
            throw new IllegalArgumentException("Start or graph does not exist and start must be in the graph");
        }
        ArrayList<Vertex<T>> visited = new ArrayList<>();
        HashSet<Vertex<T>> now = new HashSet<>();
        Queue<Vertex<T>> list = new LinkedList<>();
        list.add(start);
        now.add(start);
        visited.add(start);

        Vertex<T> curr;
        while (!list.isEmpty()) {
            curr = list.remove();
            List<VertexDistance<T>> neighbors = graph.getAdjList().get(curr);
            for (int i = 0; i < neighbors.size(); i++) {
                Vertex<T> neighbor = neighbors.get(i).getVertex();
                if (!now.contains(neighbor)) {
                    list.add(neighbor);
                    now.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }
        return visited;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * NOTE: You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !(graph.getAdjList().containsKey(start))) {
            throw new IllegalArgumentException("Start and graph doesn't exist and start must be in the graph");
        }
        ArrayList<Vertex<T>> visited = new ArrayList<>();
        dfsHelper(start, graph, visited);
        return visited;
    }

    /**
     * Recursive method for depth first search
     *
     * @param curr current vertex being looked at
     * @param graph graph of the search
     * @param visited array list of vertices already visited
     * @param <T> type
     */
    private static <T> void dfsHelper(Vertex<T> curr, Graph<T> graph,
                                      ArrayList<Vertex<T>> visited) {
        visited.add(curr);
        List<VertexDistance<T>> neighbors = graph.getAdjList().get(curr);

        for (int i = 0; i < neighbors.size(); i++) {
            Vertex<T> neighbor = neighbors.get(i).getVertex();
            if (!visited.contains(neighbor)) {
                dfsHelper(neighbor, graph, visited);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null || !(graph.getAdjList().containsKey(start))) {
            throw new IllegalArgumentException("Start and graph doesn't exist and start must be in the graph");
        }
        PriorityQueue<VertexDistance<T>> pq = new PriorityQueue<>();
        Map<Vertex<T>, Integer> visited = new HashMap<>();
        visited.put(start, 0);
        pq.add(new VertexDistance<T>(start, 0));
        List<VertexDistance<T>> pairs;
        Set<Vertex<T>> vertexList = graph.getAdjList().keySet();
        Map<Vertex<T>, Integer> map = new HashMap<>();
        for (Vertex<T> vertex : vertexList) {
            map.put(vertex, Integer.MAX_VALUE);
        }
        VertexDistance<T> minPair;
        Integer distance;
        while (!pq.isEmpty()) {
            minPair = pq.poll();
            while (minPair != null && !(minPair.getDistance() == visited.get(minPair.getVertex()))) {
                if (!pq.isEmpty()) {
                    minPair = pq.poll();
                } else {
                    minPair = null;
                }
            }
            if (minPair != null) {
                pairs = graph.getAdjList().get(minPair.getVertex());
                visited.put(minPair.getVertex(), minPair.getDistance());
                map.put(minPair.getVertex(), minPair.getDistance());
                for (VertexDistance<T> minAdjacent : pairs) {
                    if (!(minAdjacent.getVertex() == null || (null != visited.get(minAdjacent.getVertex())))) {
                        visited.put(minAdjacent.getVertex(), Integer.MAX_VALUE);
                        distance = Integer.MAX_VALUE;
                    } else {
                        distance = visited.get(minAdjacent.getVertex());
                    }
                    if (distance > (minPair.getDistance() + minAdjacent.getDistance())) {
                        int minD = minPair.getDistance() + minAdjacent.getDistance();
                        visited.put(minAdjacent.getVertex(), minD);
                        pq.add(new VertexDistance<T>(minAdjacent.getVertex(), minD));
                    }
                }
            }
        }
        return map;
    }
}
