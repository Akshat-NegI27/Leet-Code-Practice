class SegmentTree {
    private final int n;
    private final int[] tree;

    public SegmentTree(int[] nums) {
        this.n = nums.length;
        this.tree = new int[n * 4];
        build(nums, 0, 0, n - 1);
    }

    public void update(int i, int val) {
        update(0, 0, n - 1, i, val);
    }

    public int queryFirst(int target) {
        return queryFirst(0, 0, n - 1, target);
    }

    private void build(int[] nums, int treeIndex, int lo, int hi) {
        if (lo == hi) {
            tree[treeIndex] = nums[lo];
            return;
        }
        int mid = lo + (hi - lo) / 2;
        build(nums, 2 * treeIndex + 1, lo, mid);
        build(nums, 2 * treeIndex + 2, mid + 1, hi);
        tree[treeIndex] = merge(tree[2 * treeIndex + 1], tree[2 * treeIndex + 2]);
    }

    private void update(int treeIndex, int lo, int hi, int i, int val) {
        if (lo == hi) {
            tree[treeIndex] = val;
            return;
        }
        int mid = lo + (hi - lo) / 2;
        if (i <= mid) {
            update(2 * treeIndex + 1, lo, mid, i, val);
        } else {
            update(2 * treeIndex + 2, mid + 1, hi, i, val);
        }
        tree[treeIndex] = merge(tree[2 * treeIndex + 1], tree[2 * treeIndex + 2]);
    }

    private int queryFirst(int treeIndex, int lo, int hi, int target) {

        if (tree[treeIndex] < target) {
            return -1;
        }

        if (lo == hi) {

            update(lo, -1);
            return lo;
        }
        int mid = lo + (hi - lo) / 2;
        int leftChildMax = tree[2 * treeIndex + 1];

        if (leftChildMax >= target) {

            return queryFirst(2 * treeIndex + 1, lo, mid, target);
        } else {

            return queryFirst(2 * treeIndex + 2, mid + 1, hi, target);
        }
    }

    private int merge(int left, int right) {
        return Math.max(left, right);
    }
}

class Solution {
    public int numOfUnplacedFruits(int[] fruits, int[] baskets) {
        int ans = 0;
        SegmentTree tree = new SegmentTree(baskets);

        for (int fruit : fruits) {
            if (tree.queryFirst(fruit) == -1) {
                ans++;
            }
        }
        return ans;
    }
}