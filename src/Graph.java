import java.util.*;

// A small helper class to represent an edge (for weighted adjacency lists)
class Edge {
    int to, weight;
    Edge(int to, int weight) {
        this.to = to;
        this.weight = weight;
    }
}

public class Graph {
    private final int n;                 // Number of vertices
    private final List<Edge>[] adj;      // Adjacency list: adj[u] = list of edges from u

    @SuppressWarnings("unchecked")
    public Graph(int n) {
        this.n = n;
        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
    }

    /**
     * Add an edge from u to v with weight w.
     * If the graph is undirected, call addEdge(u, v, w) and addEdge(v, u, w).
     */
    public void addEdge(int u, int v, int w) {
        adj[u].add(new Edge(v, w));
    }

    // -------------------------------------------------------------------------
    // 1) BFS (Breadth-First Search) -- typically for unweighted shortest paths
    //    or simple traversal.
    // -------------------------------------------------------------------------
    public List<Integer> bfs(int start) {
        List<Integer> traversalOrder = new ArrayList<>();
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();

        visited[start] = true;
        queue.offer(start);

        while (!queue.isEmpty()) {
            int u = queue.poll();
            traversalOrder.add(u);
            // Explore neighbors
            for (Edge e : adj[u]) {
                if (!visited[e.to]) {
                    visited[e.to] = true;
                    queue.offer(e.to);
                }
            }
        }
        return traversalOrder;
    }

    // -------------------------------------------------------------------------
    // 2) DFS (Depth-First Search) -- graph traversal, cycle detection, etc.
    // -------------------------------------------------------------------------
    public List<Integer> dfs(int start) {
        List<Integer> order = new ArrayList<>();
        boolean[] visited = new boolean[n];
        dfsHelper(start, visited, order);
        return order;
    }

    private void dfsHelper(int u, boolean[] visited, List<Integer> order) {
        visited[u] = true;
        order.add(u);
        for (Edge e : adj[u]) {
            if (!visited[e.to]) {
                dfsHelper(e.to, visited, order);
            }
        }
    }

    // -------------------------------------------------------------------------
    // 3) Dijkstra's Algorithm for Single-Source Shortest Path in O((E + V)logV)
    //    Works for graphs with nonnegative edge weights.
    //    Returns dist[] array with shortest distance from 'start' to each vertex.
    // -------------------------------------------------------------------------
    public int[] dijkstra(int start) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        // Min-heap storing pairs (distance, node)
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.offer(new int[]{0, start});

        boolean[] visited = new boolean[n];

        while (!pq.isEmpty()) {
            int[] top = pq.poll();
            int curDist = top[0];
            int u = top[1];

            if (visited[u]) continue;
            visited[u] = true;

            for (Edge e : adj[u]) {
                int v = e.to, w = e.weight;
                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    pq.offer(new int[]{dist[v], v});
                }
            }
        }
        return dist;
    }

    // -------------------------------------------------------------------------
    // 4) Topological Sort (Kahn's Algorithm)
    //    - Valid for Directed Acyclic Graphs (DAG).
    //    - Returns an empty list if there is a cycle (no valid topological order).
    // -------------------------------------------------------------------------
    public List<Integer> topologicalSort() {
        int[] inDegree = new int[n];
        // Calculate in-degree of each node
        for (int u = 0; u < n; u++) {
            for (Edge e : adj[u]) {
                inDegree[e.to]++;
            }
        }

        // Initialize queue with all vertices having inDegree == 0
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        List<Integer> order = new ArrayList<>();
        while (!queue.isEmpty()) {
            int u = queue.poll();
            order.add(u);
            // Decrease the inDegree of neighbors
            for (Edge e : adj[u]) {
                if (--inDegree[e.to] == 0) {
                    queue.offer(e.to);
                }
            }
        }

        // If topological sort is not possible (graph has a cycle),
        // not all nodes will appear in 'order'.
        if (order.size() < n) {
            // Could throw an exception or return an empty list to signal a cycle
            return new ArrayList<>();
        }
        return order;
    }

    // -------------------------------------------------------------------------
    // 5) Prim's Algorithm for Minimum Spanning Tree (MST)
    //    - Assumes undirected, weighted graph.
    //    - For an undirected edge (u,v,w), you must call:
    //         addEdge(u, v, w) AND addEdge(v, u, w).
    //    - Returns the total cost (weight) of the MST.
    //    - If the graph is not connected, MST will cover only reachable nodes.
    // -------------------------------------------------------------------------
    public int primMST(int start) {
        int totalCost = 0;
        boolean[] visited = new boolean[n];
        // (weight, node)
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        // Start with an arbitrary node (start)
        pq.offer(new int[]{0, start});
        int visitedCount = 0;

        while (!pq.isEmpty() && visitedCount < n) {
            int[] top = pq.poll();
            int w = top[0];
            int u = top[1];
            if (visited[u]) continue;

            visited[u] = true;
            visitedCount++;
            totalCost += w; // Add edge weight to MST total cost

            // Expand edges from u
            for (Edge e : adj[u]) {
                if (!visited[e.to]) {
                    pq.offer(new int[]{e.weight, e.to});
                }
            }
        }
        // If visitedCount < n, the graph isn't fully connected,
        // but totalCost is for the reachable subgraph’s MST
        return totalCost;
    }

    // Get number of vertices (optional)
    public int size() {
        return n;
    }
}

