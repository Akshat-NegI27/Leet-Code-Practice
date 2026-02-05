class Solution {
    public int[] constructTransformedArray(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];

        for (int i = 0; i < n; i++) {
            int move = nums[i];

            if (move == 0) {
                result[i] = 0;
            } else {
                int idx = (i + move) % n;
                if (idx < 0) idx += n;
                result[i] = nums[idx];
            }
        }

        return result;
    }
}