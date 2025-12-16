class Solution {

    List<List<Integer>> tree;
    int[] present, future;
    int budget;
    Map<Integer, Pair> memo;

    public int maxProfit(int n, int[] present, int[] future, int[][] hierarchy, int budget) {

        this.present = present;
        this.future = future;
        this.budget = budget;
        this.memo = new HashMap<>();

        tree = new ArrayList<>();
        for (int i = 0; i < n; i++)
            tree.add(new ArrayList<>());

        for (int[] h : hierarchy) {
            int u = h[0] - 1;
            int v = h[1] - 1;
            tree.get(u).add(v);
        }

        Pair res = dfs(0, -1);

        int max = 0;
        for (int val : res.noDiscount)
            max = Math.max(max, val);
        return max;
    }

    private Pair dfs(int u, int parent) {

        int key = (u << 20) | (parent + 1);
        if (memo.containsKey(key))
            return memo.get(key);

        int[] noDiscount = new int[budget + 1];
        int[] withDiscount = new int[budget + 1];

        // Initialize DP arrays to zero
        Arrays.fill(noDiscount, 0);
        Arrays.fill(withDiscount, 0);

        // Process children
        for (int v : tree.get(u)) {
            if (v == parent)
                continue;

            Pair child = dfs(v, u);

            noDiscount = merge(noDiscount, child.noDiscount);
            withDiscount = merge(withDiscount, child.withDiscount);
        }

        int[] newDp0 = Arrays.copyOf(noDiscount, budget + 1);
        int[] newDp1 = Arrays.copyOf(noDiscount, budget + 1);

        // Buy current node at full price
        int full = present[u];
        for (int b = full; b <= budget; b++) {
            int profit = future[u] - full;
            newDp0[b] = Math.max(newDp0[b], withDiscount[b - full] + profit);
        }

        // Buy current node at half price (discount)
        int half = present[u] / 2;
        for (int b = half; b <= budget; b++) {
            int profit = future[u] - half;
            newDp1[b] = Math.max(newDp1[b], withDiscount[b - half] + profit);
        }

        Pair ans = new Pair(newDp0, newDp1);
        memo.put(key, ans);
        return ans;
    }

    private int[] merge(int[] A, int[] B) {
        int n = A.length;
        int[] merged = new int[n];
        Arrays.fill(merged, Integer.MIN_VALUE);

        for (int i = 0; i < n; i++) {
            if (A[i] == Integer.MIN_VALUE)
                continue;
            for (int j = 0; i + j < n; j++) {
                if (B[j] == Integer.MIN_VALUE)
                    continue;
                merged[i + j] = Math.max(merged[i + j], A[i] + B[j]);
            }
        }
        return merged;
    }

    static class Pair {
        int[] noDiscount;
        int[] withDiscount;

        Pair(int[] a, int[] b) {
            this.noDiscount = a;
            this.withDiscount = b;
        }
    }
}