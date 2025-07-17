class Solution {
    public int maximumLength(int[] nums, int k) {
        int[][] dp = new int[k][k];
        for (final int x : nums)
            for (int y = 0; y < k; ++y)
                dp[x % k][y] = dp[y][x % k] + 1;
        return Arrays.stream(dp).flatMapToInt(Arrays::stream).max().getAsInt();
    }
}