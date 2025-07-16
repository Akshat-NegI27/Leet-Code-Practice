import java.util.*;

class Solution {
    public int maximumLength(int[] nums) {
        int[][] dp = new int[2][2];

        for (int x : nums) {
            for (int y = 0; y < 2; ++y) {
                dp[x % 2][y] = dp[y][x % 2] + 1;
            }
        }

        int maxLen = 0;
        for (int i = 0; i < 2; ++i)
            for (int j = 0; j < 2; ++j)
                maxLen = Math.max(maxLen, dp[i][j]);

        return maxLen;
    }
}