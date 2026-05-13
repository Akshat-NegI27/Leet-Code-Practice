class Solution {
    public int minMoves(int[] nums, int limit) {
        final int n = nums.length;
        int ans = n;
        int[] delta = new int[limit * 2 + 2];

        for (int i = 0; i < n / 2; ++i) {
            final int a = nums[i];
            final int b = nums[n - 1 - i];
            --delta[Math.min(a, b) + 1];
            --delta[a + b];
            ++delta[a + b + 1];
            ++delta[Math.max(a, b) + limit + 1];
        }

        for (int i = 2, moves = n; i <= limit * 2; ++i) {
            moves += delta[i];
            ans = Math.min(ans, moves);
        }

        return ans;
    }
}