class Solution {
    long power(long base, long exp) {
        long res = 1;
        base %= 1000000007;
        while (exp > 0) {
            if (exp % 2 == 1)
                res = (res * base) % 1000000007;
            base = (base * base) % 1000000007;
            exp /= 2;
        }
        return res;
    }

    public int xorAfterQueries(int[] nums, int[][] queries) {
        int n = nums.length;
        int T = (int) Math.sqrt(n);
        int mod = 1000000007;

        List<int[]>[] groups = new ArrayList[T];
        for (int i = 0; i < T; i++)
            groups[i] = new ArrayList<>();

        for (int[] q : queries) {
            int l = q[0], r = q[1], k = q[2], v = q[3];
            if (k < T) {
                groups[k].add(new int[] { l, r, v });
            } else {
                for (int i = l; i <= r; i += k) {
                    nums[i] = (int) ((1L * nums[i] * v) % mod);
                }
            }
        }

        long[] dif = new long[n + T];
        for (int k = 1; k < T; k++) {
            if (groups[k].isEmpty())
                continue;
            Arrays.fill(dif, 1L);
            for (int[] q : groups[k]) {
                int l = q[0], r = q[1], v = q[2];
                dif[l] = (dif[l] * v) % mod;
                int R = ((r - l) / k + 1) * k + l;
                dif[R] = (dif[R] * power(v, mod - 2)) % mod;
            }
            for (int i = k; i < n; i++) {
                dif[i] = (dif[i] * dif[i - k]) % mod;
            }
            for (int i = 0; i < n; i++) {
                nums[i] = (int) ((1L * nums[i] * dif[i]) % mod);
            }
        }

        int ans = 0;
        for (int x : nums)
            ans ^= x;
        return ans;
    }
}