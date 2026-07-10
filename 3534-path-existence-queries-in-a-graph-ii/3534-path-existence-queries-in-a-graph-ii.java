class Solution {
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i];
            arr[i][1] = i;
        }

        Arrays.sort(arr, (a, b) -> Integer.compare(a[0], b[0]));

        int[] pos = new int[n];
        int[] val = new int[n];
        for (int i = 0; i < n; i++) {
            val[i] = arr[i][0];
            pos[arr[i][1]] = i;
        }

        int[] next = new int[n];
        int r = 0;
        for (int i = 0; i < n; i++) {
            if (r < i) r = i;
            while (r + 1 < n && val[r + 1] - val[i] <= maxDiff) r++;
            next[i] = r;
        }

        int LOG = 18;
        int[][] up = new int[LOG][n];
        for (int i = 0; i < n; i++) up[0][i] = next[i];
        for (int k = 1; k < LOG; k++) {
            for (int i = 0; i < n; i++) {
                up[k][i] = up[k - 1][up[k - 1][i]];
            }
        }

        int[] prev = new int[n];
        int l = n - 1;
        for (int i = n - 1; i >= 0; i--) {
            if (l > i) l = i;
            while (l - 1 >= 0 && val[i] - val[l - 1] <= maxDiff) l--;
            prev[i] = l;
        }

        int[][] down = new int[LOG][n];
        for (int i = 0; i < n; i++) down[0][i] = prev[i];
        for (int k = 1; k < LOG; k++) {
            for (int i = 0; i < n; i++) {
                down[k][i] = down[k - 1][down[k - 1][i]];
            }
        }

        int[] ans = new int[queries.length];

        for (int qi = 0; qi < queries.length; qi++) {
            int u = pos[queries[qi][0]];
            int v = pos[queries[qi][1]];

            if (u == v) {
                ans[qi] = 0;
                continue;
            }

            if (u < v) {
                if (next[u] == u) {
                    ans[qi] = -1;
                    continue;
                }
                int cur = u;
                int steps = 0;
                for (int k = LOG - 1; k >= 0; k--) {
                    if (up[k][cur] < v) {
                        cur = up[k][cur];
                        steps += 1 << k;
                    }
                }
                if (next[cur] < v) ans[qi] = -1;
                else ans[qi] = steps + 1;
            } else {
                if (prev[u] == u) {
                    ans[qi] = -1;
                    continue;
                }
                int cur = u;
                int steps = 0;
                for (int k = LOG - 1; k >= 0; k--) {
                    if (down[k][cur] > v) {
                        cur = down[k][cur];
                        steps += 1 << k;
                    }
                }
                if (prev[cur] > v) ans[qi] = -1;
                else ans[qi] = steps + 1;
            }
        }

        return ans;
    }
}