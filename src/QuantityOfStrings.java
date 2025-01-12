//https://codeforces.com/contest/151/problem/D - Accepted

import java.util.*;

public class QuantityOfStrings
{
    public static class DisjointSetUnion
    {
        public Map<Object, Object> parent = new HashMap<>();
        public Map<Object, Integer> size = new HashMap<>();

        public void makeSet(Object vertex)
        {
            parent.put(vertex, vertex);
            size.put(vertex, 1);
        }

        //O(n) on average
        private Object findRootNaive(Object vertex)
        {
            Object dp = parent.get(vertex);
            while(!dp.equals(vertex))
            {
                vertex = dp;
                dp = parent.get(vertex);
            }
            return vertex;
        }

        //O(logn) on average
        public Object findRoot(Object vertex)
        {
            Object dp = parent.get(vertex);
            while(!dp.equals(vertex))
            {
                parent.put(vertex, parent.get(dp));
                vertex = dp;
                dp = parent.get(vertex);
            }

            return vertex;
        }

        //O(logn) on average
        private void unionSetsNaive(Object vertexA, Object vertexB)
        {
            Object rootA = findRoot(vertexA);
            Object rootB = findRoot(vertexB);
            if(!rootA.equals(rootB))
            {
                parent.put(rootB, rootA);
            }
        }

        //Approximately Constant Time per Query
        public void unionSets(Object vertexA, Object vertexB)
        {
            if(!parent.containsKey(vertexA) || !parent.containsKey(vertexB))
            {
                return;
            }
            Object rootA = findRoot(vertexA);
            Object rootB = findRoot(vertexB);
            if(!rootA.equals(rootB))
            {
                if(size.get(rootA) < size.get(rootB))
                {
                    parent.put(rootA, rootB);
                    size.put(rootB, size.get(rootA) + size.get(rootB));
                }
                else
                {
                    parent.put(rootB, rootA);
                    size.put(rootA, size.get(rootA) + size.get(rootB));

                }
            }
        }
    }

    static long mod = 1000000007L;
    public static long powMod(long base, long exp)
    {
        long b = 1;
        long A = base;
        if((exp & 1) == 1)
        {
            b = base % mod;
        }
        exp  = (exp >> 1);
        while(exp > 0)
        {
            A = (A * A) % mod;
            if((exp & 1) == 1)
            {
                b = (A * b) % mod;
            }
            exp = (exp >> 1);
        }

        return b % mod;
    }

    public static boolean isRoot(DisjointSetUnion sets, Object vertex)
    {
        return vertex.equals(sets.parent.get(vertex));
    }

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();

        if(n < k)
        {
            System.out.println(powMod(m, n));
            return;
        }

        if(n == k)
        {
            System.out.println(powMod(m, (n + 1) / 2));
            return;
        }

        DisjointSetUnion graph = new DisjointSetUnion();
        for(int i = 0; i < n; i++)
        {
            graph.makeSet(i);
        }
        Map<Integer, PriorityQueue<Integer>> palindromeConnections = new HashMap<>();
        palindromeConnections.put(0, new PriorityQueue<>());
        palindromeConnections.get(0).add(k - 1);
        for(int i = 0; i < n; i++)
        {
            PriorityQueue<Integer> nextPQ = palindromeConnections.get(i);
            if(!nextPQ.isEmpty() && nextPQ.peek() > 2)
            {
                nextPQ.add(nextPQ.peek() - 2);
            }
            palindromeConnections.put(i + 1, nextPQ);
            PriorityQueue<Integer> thisPQ = palindromeConnections.get(i);
            for(int dist : thisPQ)
            {
                if(i + dist < n)
                {
                    graph.unionSets(i, i + dist);
                }
            }
        }

        int count = 0;
        for(int i = 0; i < n; i++)
        {
            if(isRoot(graph, i))
            {
                count++;
            }
        }
        System.out.println(powMod(m, count));
    }
}
