public class UnionFind {
    private int[] parent;
    private int[] size;  // You could also call this 'rank'
    private int count;   // Optional: track the number of disjoint sets

    public UnionFind(int n) {
        parent = new int[n];
        size = new int[n];
        count = n;  // Initially, each element is its own set
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int find(int x) {
        // Path Compression
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if (rootA == rootB) {
            return false;  // Already in the same set
        }
        // Union by Size
        if (size[rootA] < size[rootB]) {
            parent[rootA] = rootB;
            size[rootB] += size[rootA];
        } else {
            parent[rootB] = rootA;
            size[rootA] += size[rootB];
        }
        count--;
        return true;
    }

    public boolean connected(int a, int b) {
        return find(a) == find(b);
    }

    public int getCount() {
        return count;
    }
}


