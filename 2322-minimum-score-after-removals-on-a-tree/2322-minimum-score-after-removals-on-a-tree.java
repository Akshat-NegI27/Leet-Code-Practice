class Solution {
    List<Integer>[] tree;
    int[] xor;
    int[] in, out;
    int timer = 0;

    public int minimumScore(int[] nums, int[][] edges) {
        int n = nums.length;
        tree = new ArrayList[n];
        xor = new int[n];
        in = new int[n];
        out = new int[n];

        for (int i = 0; i < n; i++)
            tree[i] = new ArrayList<>();
        for (int[] e : edges) {
            tree[e[0]].add(e[1]);
            tree[e[1]].add(e[0]);
        }

        dfs(0, -1, nums);

        int minScore = Integer.MAX_VALUE;

        for (int u = 1; u < n; u++) {
            for (int v = u + 1; v < n; v++) {
                int a, b, c;
                if (isAncestor(u, v)) {

                    a = xor[v];
                    b = xor[u] ^ xor[v];
                    c = xor[0] ^ xor[u];
                } else if (isAncestor(v, u)) {

                    a = xor[u];
                    b = xor[v] ^ xor[u];
                    c = xor[0] ^ xor[v];
                } else {

                    a = xor[u];
                    b = xor[v];
                    c = xor[0] ^ xor[u] ^ xor[v];
                }
                int maxXor = Math.max(a, Math.max(b, c));
                int minXor = Math.min(a, Math.min(b, c));
                minScore = Math.min(minScore, maxXor - minXor);
            }
        }

        return minScore;
    }

    private void dfs(int node, int parent, int[] nums) {
        in[node] = timer++;
        xor[node] = nums[node];
        for (int nei : tree[node]) {
            if (nei == parent)
                continue;
            dfs(nei, node, nums);
            xor[node] ^= xor[nei];
        }
        out[node] = timer++;
    }

    private boolean isAncestor(int u, int v) {

        return in[u] <= in[v] && out[v] <= out[u];
    }
}