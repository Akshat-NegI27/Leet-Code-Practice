
class SegmentTree {
    private final int n;
    private List<Integer> tree;

    public SegmentTree(List<Integer> nums) {
        this.n = nums.size();
        this.tree = new ArrayList<>(n * 4);
        // Initialize the tree with a placeholder size
        for(int i = 0; i < n * 4; i++) {
            tree.add(0);
        }
        build(nums, 0, 0, n - 1);
    }

    public void update(int i, int val) {
        update(0, 0, n - 1, i, val);
    }

    public int queryFirst(int target) {
        return queryFirst(0, 0, n - 1, target);
    }

    private void build(List<Integer> nums, int treeIndex, int lo, int hi) {
        if (lo == hi) {
            tree.set(treeIndex, nums.get(lo));
            return;
        }
        int mid = lo + (hi - lo) / 2;
        build(nums, 2 * treeIndex + 1, lo, mid);
        build(nums, 2 * treeIndex + 2, mid + 1, hi);
        tree.set(treeIndex, merge(tree.get(2 * treeIndex + 1), tree.get(2 * treeIndex + 2)));
    }

    private void update(int treeIndex, int lo, int hi, int i, int val) {
        if (lo == hi) {
            tree.set(treeIndex, val);
            return;
        }
        int mid = lo + (hi - lo) / 2;
        if (i <= mid) {
            update(2 * treeIndex + 1, lo, mid, i, val);
        } else {
            update(2 * treeIndex + 2, mid + 1, hi, i, val);
        }
        tree.set(treeIndex, merge(tree.get(2 * treeIndex + 1), tree.get(2 * treeIndex + 2)));
    }

    private int queryFirst(int treeIndex, int lo, int hi, int target) {
        if (tree.get(treeIndex) < target) {
            return -1;
        }

        if (lo == hi) {
            update(lo, -1);
            return lo;
        }
        
        int mid = lo + (hi - lo) / 2;
        int leftChildIndex = 2 * treeIndex + 1;
        int rightChildIndex = 2 * treeIndex + 2;

        if (tree.get(leftChildIndex) >= target) {
            int result = queryFirst(leftChildIndex, lo, mid, target);
            if (result != -1) {
                return result;
            }
        }
        
        if (tree.get(rightChildIndex) >= target) {
            return queryFirst(rightChildIndex, mid + 1, hi, target);
        }
        
        return -1;
    }

    private int merge(int left, int right) {
        return Math.max(left, right);
    }
}

class Solution {
    public int numOfUnplacedFruits(int[] fruits, int[] baskets) {
        List<Integer> fruitList = new ArrayList<>();
        for (int fruit : fruits) {
            fruitList.add(fruit);
        }

        List<Integer> basketList = new ArrayList<>();
        for (int basket : baskets) {
            basketList.add(basket);
        }

        int ans = 0;
        SegmentTree tree = new SegmentTree(basketList);
        for (int fruit : fruitList) {
            if (tree.queryFirst(fruit) == -1) {
                ans++;
            }
        }
        return ans;
    }
}