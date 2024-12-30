//https://codeforces.com/contest/151/problem/D

import DataStructures.DisjointSetUnion;
import static NumberTheory.ModularArithmetic.powMod;
import java.util.*;



public class QuantityOfStrings
{
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
            if(graph.isRoot(i))
            {
                count++;
            }
        }
        System.out.println(powMod(m, count));
    }
}
