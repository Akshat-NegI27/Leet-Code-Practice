class Solution {
    public int countMajoritySubarrays(int[] nums, int target) {
        int n = nums.length;

        int[] pref = new int[n + 1];
        for (int i = 0; i < n; i++) {
            pref[i + 1] = pref[i] + (nums[i] == target ? 1 : -1);
        }

        int[] vals = pref.clone();
        java.util.Arrays.sort(vals);

        java.util.Map<Integer, Integer> rank = new java.util.HashMap<>();
        int idx = 1;
        for (int v : vals) {
            if (!rank.containsKey(v)) {
                rank.put(v, idx++);
            }
        }

        Fenwick bit = new Fenwick(idx + 2);
        long ans = 0;

        for (int p : pref) {
            int r = rank.get(p);
            ans += bit.query(r - 1);

            bit.add(r, 1);
        }

        return (int) ans;
    }

    static class Fenwick {
        int[] tree;

        Fenwick(int n) {
            tree = new int[n];
        }

        void add(int i, int delta) {
            while (i < tree.length) {
                tree[i] += delta;
                i += i & -i;
            }
        }

        int query(int i) {
            int sum = 0;
            while (i > 0) {
                sum += tree[i];
                i -= i & -i;
            }
            return sum;
        }
    }
}