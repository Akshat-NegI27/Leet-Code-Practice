class Solution {

    static class Pair {
        long count;
        long sum;

        Pair(long c, long s) {
            count = c;
            sum = s;
        }
    }

    String digits;
    Pair[][][][] memo;

    public long totalWaviness(long num1, long num2) {
        return solve(num2) - solve(num1 - 1);
    }

    private long solve(long n) {
        if (n <= 0)
            return 0;

        digits = String.valueOf(n);
        int m = digits.length();

        memo = new Pair[m + 1][11][11][17];

        return dfs(0, true, false, 10, 10, 0).sum;
    }

    private Pair dfs(int pos, boolean tight, boolean started,
            int prev2, int prev1, int len) {

        if (pos == digits.length()) {
            return new Pair(1, 0);
        }

        if (!tight && memo[pos][prev2][prev1][len] != null) {
            return memo[pos][prev2][prev1][len];
        }

        int limit = tight ? digits.charAt(pos) - '0' : 9;

        long totalCount = 0;
        long totalSum = 0;

        for (int d = 0; d <= limit; d++) {
            boolean nextTight = tight && (d == limit);

            if (!started && d == 0) {
                Pair child = dfs(pos + 1, nextTight, false,
                        10, 10, 0);

                totalCount += child.count;
                totalSum += child.sum;
            } else {
                int add = 0;

                if (len >= 2) {
                    boolean peak = (prev1 > prev2 && prev1 > d);

                    boolean valley = (prev1 < prev2 && prev1 < d);

                    if (peak || valley)
                        add = 1;
                }

                int nPrev2 = (len == 0) ? d : prev1;
                int nPrev1 = d;

                Pair child = dfs(pos + 1, nextTight, true,
                        nPrev2, nPrev1, len + 1);

                totalCount += child.count;
                totalSum += child.sum + (long) add * child.count;
            }
        }

        Pair ans = new Pair(totalCount, totalSum);

        if (!tight) {
            memo[pos][prev2][prev1][len] = ans;
        }

        return ans;
    }
}