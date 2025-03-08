import java.util.*;

public class HongcowBuildsANation
{
    static class UnionFind
    {
        public int[] parent;
        public int[] size;
        public int count;

        public UnionFind(int n)
        {
            parent = new int[n];
            size = new int[n];
            count = n;
            for (int i = 0; i < n; i++)
            {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public int find(int x)
        {
            if (parent[x] != x)
            {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public boolean union(int a, int b)
        {
            int rootA = find(a);
            int rootB = find(b);
            if (rootA == rootB)
            {
                return false;
            }
            if (size[rootA] < size[rootB])
            {
                parent[rootA] = rootB;
                size[rootB] += size[rootA];
            }
            else
            {
                parent[rootB] = rootA;
                size[rootA] += size[rootB];
            }
            count--;
            return true;
        }
    }

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        UnionFind uf = new UnionFind(n);
        boolean[] isGovernment = new boolean[n];
        for(int i = 0; i < k; i++)
        {
            isGovernment[in.nextInt() - 1] = true;
        }
        for(int i = 0; i < m; i++)
        {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
            uf.union(u, v);
        }
        boolean[] isRoot = new boolean[n];
        for(int i = 0; i < n; i++)
        {
            isRoot[i] = uf.parent[i] == i;
        }

        int total = 0;
        int bestGovRoot = -1;
        boolean[] isGovRoot = new boolean[n];
        for(int i = 0; i < n; i++)
        {
            if(isGovernment[i])
            {
                int root = uf.find(i);
                isGovRoot[root] = true;
                if(bestGovRoot == -1 || uf.size[root] > uf.size[bestGovRoot])
                {
                    bestGovRoot = root;
                }
            }
            if(isRoot[i])
            {
                total += uf.size[i] * (uf.size[i] - 1) / 2;
            }
        }
        total -= m;

        int delta = 0;
        for(int i = 0; i < n; i++)
        {
            if(isRoot[i] && !isGovRoot[i])
            {
                total += (uf.size[bestGovRoot] + delta) * uf.size[i];
                delta += uf.size[i];
            }
        }

        System.out.println(total);
    }


}
