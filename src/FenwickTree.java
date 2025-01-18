public class FenwickTree {
    private int[] fenw;  // fenw[i] stores partial sums
    private int n;

    public FenwickTree(int n) {
        this.n = n;
        fenw = new int[n + 1];  // 1-based indexing
    }

    // Build from array (optional convenience constructor)
    public FenwickTree(int[] arr) {
        this(arr.length);
        for (int i = 0; i < arr.length; i++) {
            update(i + 1, arr[i]);
        }
    }

    // Add 'value' to index 'i' (1-based index for Fenwicks)
    public void update(int i, int value) {
        while (i <= n) {
            fenw[i] += value;
            i += i & (-i);
        }
    }

    // Returns the sum from 1 to i
    public int query(int i) {
        int s = 0;
        while (i > 0) {
            s += fenw[i];
            i -= i & (-i);
        }
        return s;
    }

    // Returns the sum from l to r (1-based)
    public int rangeQuery(int l, int r) {
        return query(r) - query(l - 1);
    }
}


