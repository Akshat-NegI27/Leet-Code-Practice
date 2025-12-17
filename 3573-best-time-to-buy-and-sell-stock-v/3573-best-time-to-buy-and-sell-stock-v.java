class Solution {
    public long maximumProfit(int[] prices, int k) {
        int n = prices.length;

        long[] buy = new long[k + 1];
        long[] sell = new long[k + 1];
        long[] shortSell = new long[k + 1];

        for (int t = 0; t <= k; t++) {
            buy[t] = shortSell[t] = Long.MIN_VALUE / 4;
            sell[t] = 0;
        }

        for (int price : prices) {

            long[] newBuy = buy.clone();
            long[] newShort = shortSell.clone();
            long[] newSell = sell.clone();

            for (int t = 1; t <= k; t++) {

                newBuy[t] = Math.max(newBuy[t], sell[t - 1] - price);
                newShort[t] = Math.max(newShort[t], sell[t - 1] + price);
                newSell[t] = Math.max(newSell[t], buy[t] + price);
                newSell[t] = Math.max(newSell[t], shortSell[t] - price);
            }

            buy = newBuy;
            shortSell = newShort;
            sell = newSell;
        }

        return sell[k];
    }
}