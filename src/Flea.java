import java.util.*;

public class Flea
{
    static class LargeLongArray
    {
        private final long[][] arr;
        private final int m;
        public LargeLongArray(int n, int m)
        {
            arr = new long[n][m];
            this.m = m;
        }
        public long get(long i)
        {
            return arr[(int) (i / m)][(int) (i % m)];
        }
        public void set(long i, long val)
        {
            arr[(int) (i / m)][(int) (i % m)] = val;
        }
    }
    static class UnionFind
    {
        public LargeLongArray parent;
        public LargeLongArray size;
        public long count;

        public UnionFind(long n, long m)
        {
            parent = new LargeLongArray((int) n, (int) m);
            size = new LargeLongArray((int) n, (int) m);
            count = n * m;
            for (long i = 0; i < n * m; i++)
            {
                parent.set(i, i);
                size.set(i, 1L);
            }
        }

        public long find(long x)
        {
            if (parent.get(x) != x)
            {
                parent.set(x, find(parent.get(x)));
            }
            return parent.get(x);
        }

        public boolean union(long a, long b)
        {
            long rootA = find(a);
            long rootB = find(b);
            if (rootA == rootB)
            {
                return false;
            }
            if (size.get(rootA) < size.get(rootB))
            {
                parent.set(rootA, rootB);
                size.set(rootB, size.get(rootA) + size.get(rootB));
            }
            else
            {
                parent.set(rootB, rootA);
                size.set(rootA, size.get(rootA) + size.get(rootB));
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
        int s = in.nextInt();

        UnionFind uf = new UnionFind(n, m);
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < m; j++)
            {
                if(i < n - s)
                {
                    uf.union((long) i * m + j, (long) (i + s) * m + j);
                }
                if(j >= s)
                {
                    uf.union((long) i * m + j, (long) i * m + (j - s));
                }
                if(j < m - s)
                {
                    uf.union((long) i * m + j, (long) i * m + (j + s));
                }
                if(i >= s)
                {
                    uf.union((long) i * m + j, (long) (i - s) * m + j);
                }
            }
        }
        LargeLongArray numOfSize = new LargeLongArray(n, m);
        for(int i = 0; i < n * m; i++)
        {
            numOfSize.set(i, 0);
        }
        for(int i = 0; i < n * m; i++)
        {
            if(uf.parent.get(i) == i)
            {
                numOfSize.set(uf.size.get(i) - 1, numOfSize.get(uf.size.get(i) - 1) + uf.size.get(i));
            }
        }
        for(int i = n * m - 1; i >= 0; i--)
        {
            if(numOfSize.get(i) != 0)
            {
                System.out.println(numOfSize.get(i));
                break;
            }
        }

    }
}
