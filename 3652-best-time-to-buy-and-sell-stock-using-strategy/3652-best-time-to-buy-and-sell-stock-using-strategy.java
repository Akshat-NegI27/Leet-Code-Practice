class Solution {
    public long maxProfit(int[] prices, int[] strategy, int k) {
        int n = prices.length;
        int half = k / 2;

        long[] orig = new long[n];
        long[] gainFirst = new long[n];
        long[] gainSecond = new long[n];

        long base = 0;
        for (int i = 0; i < n; i++) {
            orig[i] = (long) strategy[i] * prices[i];
            base += orig[i];

            gainFirst[i] = -orig[i];
            gainSecond[i] = prices[i] - orig[i];
        }

        long[] prefFirst = new long[n + 1];
        long[] prefSecond = new long[n + 1];

        for (int i = 0; i < n; i++) {
            prefFirst[i + 1] = prefFirst[i] + gainFirst[i];
            prefSecond[i + 1] = prefSecond[i] + gainSecond[i];
        }

        long bestGain = 0;

        for (int l = 0; l + k <= n; l++) {
            int mid = l + half;

            long g1 = prefFirst[mid] - prefFirst[l];        // first half
            long g2 = prefSecond[l + k] - prefSecond[mid];  // second half

            bestGain = Math.max(bestGain, g1 + g2);
        }

        return base + bestGain;
    }
}
