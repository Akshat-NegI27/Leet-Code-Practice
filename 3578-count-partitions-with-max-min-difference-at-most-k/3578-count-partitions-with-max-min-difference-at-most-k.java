import java.util.*;

class Solution {
    public int countPartitions(int[] nums, int k) {
        int n = nums.length;
        int MOD = 1_000_000_007;

        long[] dp = new long[n + 1];
        long[] pref = new long[n + 1];
        dp[0] = 1;
        pref[0] = 1;

        Deque<Integer> maxD = new ArrayDeque<>();
        Deque<Integer> minD = new ArrayDeque<>();

        int j = 0;

        for (int i = 0; i < n; i++) {
            while (!maxD.isEmpty() && nums[maxD.peekLast()] <= nums[i]) maxD.pollLast();
            maxD.addLast(i);

            while (!minD.isEmpty() && nums[minD.peekLast()] >= nums[i]) minD.pollLast();
            minD.addLast(i);

            while (!maxD.isEmpty() && !minD.isEmpty()
                    && (long)nums[maxD.peekFirst()] - (long)nums[minD.peekFirst()] > k) {
                if (maxD.peekFirst() == j) maxD.pollFirst();
                if (minD.peekFirst() == j) minD.pollFirst();
                j++;
            }

            long sum;
            if (j == 0) {
                sum = pref[i];
            } else {
                sum = pref[i] - pref[j - 1];
                if (sum < 0) sum += MOD;
            }

            dp[i + 1] = sum % MOD;
            pref[i + 1] = (pref[i] + dp[i + 1]) % MOD;
        }

        return (int)(dp[n] % MOD);
    }
}
