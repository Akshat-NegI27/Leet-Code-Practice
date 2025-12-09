import java.util.*;

class Solution {
    public int specialTriplets(int[] nums) {
        int n = nums.length;
        int MOD = 1_000_000_007;

        Map<Integer, Long> right = new HashMap<>();
        Map<Integer, Long> left = new HashMap<>();

        for (int x : nums) {
            right.put(x, right.getOrDefault(x, 0L) + 1);
        }

        long ans = 0;

        for (int j = 0; j < n; j++) {
            int x = nums[j];
            right.put(x, right.get(x) - 1);

            int need = x * 2;

            long cntLeft = left.getOrDefault(need, 0L);
            long cntRight = right.getOrDefault(need, 0L);

            ans = (ans + (cntLeft * cntRight) % MOD) % MOD;

            left.put(x, left.getOrDefault(x, 0L) + 1);
        }

        return (int) ans;
    }
}
