public class SegmentTree {
    private int[] tree;  // segment tree array
    private int n;       // size of the original array

    public SegmentTree(int[] arr) {
        n = arr.length;
        tree = new int[4 * n]; // safe size
        build(arr, 1, 0, n - 1);
    }

    // Build the tree with the initial array values
    private void build(int[] arr, int idx, int start, int end) {
        if (start == end) {
            // Leaf node
            tree[idx] = arr[start];
            return;
        }
        int mid = (start + end) / 2;
        // Build left child
        build(arr, 2 * idx, start, mid);
        // Build right child
        build(arr, 2 * idx + 1, mid + 1, end);
        // Internal node is the sum of its children
        tree[idx] = tree[2 * idx] + tree[2 * idx + 1];
    }

    // Update a single element (arr[pos] = newValue)
    public void update(int pos, int newValue) {
        updateHelper(1, 0, n - 1, pos, newValue);
    }

    private void updateHelper(int idx, int start, int end, int pos, int value) {
        if (start == end) {
            // Leaf node
            tree[idx] = value;
            return;
        }
        int mid = (start + end) / 2;
        if (pos <= mid) {
            // Go left
            updateHelper(2 * idx, start, mid, pos, value);
        } else {
            // Go right
            updateHelper(2 * idx + 1, mid + 1, end, pos, value);
        }
        // Recompute current node value
        tree[idx] = tree[2 * idx] + tree[2 * idx + 1];
    }

    // Query sum in range [left, right]
    public int query(int left, int right) {
        return queryHelper(1, 0, n - 1, left, right);
    }

    private int queryHelper(int idx, int start, int end, int left, int right) {
        if (right < start || end < left) {
            // No overlap
            return 0;  // For sum query, return 0
        }
        if (left <= start && end <= right) {
            // Total overlap
            return tree[idx];
        }
        // Partial overlap
        int mid = (start + end) / 2;
        int sumLeft = queryHelper(2 * idx, start, mid, left, right);
        int sumRight = queryHelper(2 * idx + 1, mid + 1, end, left, right);
        return sumLeft + sumRight;
    }
}

