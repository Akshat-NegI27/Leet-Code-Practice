class Solution {
    public int longestBalanced(int[] nums) {
        int n = nums.length;
        int ans = 0;

        for (int l = 0; l < n; l++) {
            Map<Integer, Integer> even = new HashMap<>();
            Map<Integer, Integer> odd = new HashMap<>();

            for (int r = l; r < n; r++) {
                if (nums[r] % 2 == 0) {
                    even.put(nums[r], even.getOrDefault(nums[r], 0) + 1);
                } else {
                    odd.put(nums[r], odd.getOrDefault(nums[r], 0) + 1);
                }

                if (even.size() == odd.size()) {
                    ans = Math.max(ans, r - l + 1);
                }
            }
        }

        return ans;
    }
}