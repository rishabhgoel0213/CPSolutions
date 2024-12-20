package DataStructures;

import java.util.*;

public class DisjointSetUnion
{
    Map<Object, Object> parent = new HashMap<>();
    Map<Object, Integer> size = new HashMap<>();

    public void makeSet(Object vertex)
    {
        parent.put(vertex, vertex);
        size.put(vertex, 1);
    }

    public boolean isRoot(Object vertex)
    {
        return vertex.equals(parent.get(vertex));
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
