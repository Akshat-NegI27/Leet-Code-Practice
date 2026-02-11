class Solution {
    public int longestBalanced(int[] nums) {
        int n = nums.length;
        int ans = 0;

        for (int target = 1; target <= n; target++) {
            Map<Integer, Integer> freq = new HashMap<>();
            int left = 0;
            int even = 0, odd = 0;

            for (int right = 0; right < n; right++) {
                int val = nums[right];
                if (freq.getOrDefault(val, 0) == 0) {
                    if (val % 2 == 0)
                        even++;
                    else
                        odd++;
                }
                freq.put(val, freq.getOrDefault(val, 0) + 1);

                while (even > target || odd > target) {
                    int remove = nums[left++];
                    freq.put(remove, freq.get(remove) - 1);
                    if (freq.get(remove) == 0) {
                        if (remove % 2 == 0)
                            even--;
                        else
                            odd--;
                        freq.remove(remove);
                    }
                }

                if (even == target && odd == target) {
                    ans = Math.max(ans, right - left + 1);
                }
            }
        }

        return ans;
    }
}