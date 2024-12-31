package FailedCode;//https://codeforces.com/problemset/problem/547/B

import DataStructures.DisjointSetUnion;
import java.util.*;

public class MikeAndFeet
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] a = new int[n];
        for(int i = 0; i < n; i++)
        {
            a[i] = in.nextInt();
        }
        Integer[] sortedIndexes = new Integer[n];
        for(int i = 0; i < n; i++)
        {
            sortedIndexes[i] = i;
        }
        Arrays.sort(sortedIndexes, (i, j) -> -Integer.compare(a[i], a[j]));

        DisjointSetUnion groups = new DisjointSetUnion();
        int[] maxStrength = new int[n];
        for(int j = 0; j < sortedIndexes.length; j++)
        {
            int i = sortedIndexes[j];
            groups.makeSet(i);
            groups.unionSets(i, i + 1);
            groups.unionSets(i, i - 1);
            int size = groups.size.get(groups.findRoot(i));
            if(maxStrength[size - 1] < a[i])
            {
                maxStrength[size - 1] = a[i];
            }

        }
        int prevMax = maxStrength[n - 1];
        for(int i = n - 2; i >= 0; i--)
        {
            if(maxStrength[i] < prevMax)
            {
                maxStrength[i] = prevMax;
            }
            else
            {
                prevMax = maxStrength[i];
            }
        }
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < n; i++)
        {
            str.append(maxStrength[i]).append(" ");
        }
        System.out.println(str);

    }
}
