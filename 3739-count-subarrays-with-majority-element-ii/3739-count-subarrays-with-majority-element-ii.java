class Solution {
    static class Fenwick {
        int[] bit;
        Fenwick(int n) {
            bit = new int[n + 2];
        }

        void add(int idx, int val) {
            while (idx < bit.length) {
                bit[idx] += val;
                idx += idx & -idx;
            }
        }

        int query(int idx) {
            int res = 0;
            while (idx > 0) {
                res += bit[idx];
                idx -= idx & -idx;
            }
            return res;
        }
    }

    public long countMajoritySubarrays(int[] nums, int target) {
        int n = nums.length;
        int offset = n + 1;
        Fenwick ft = new Fenwick(2 * n + 5);

        long ans = 0;
        int pref = 0;


        ft.add(offset + pref, 1);

        for (int x : nums) {
            pref += (x == target) ? 1 : -1;

            
            ans += ft.query(offset + pref - 1);

            ft.add(offset + pref, 1);
        }

        return ans;
    }
}