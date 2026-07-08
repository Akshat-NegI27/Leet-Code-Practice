class Solution {
    static final int MOD = 1_000_000_007;

    public int[] sumAndMultiply(String s, int[][] queries) {
        int m = s.length();

        ArrayList<Integer> posList = new ArrayList<>();
        ArrayList<Integer> digitList = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            int d = s.charAt(i) - '0';
            if (d != 0) {
                posList.add(i);
                digitList.add(d);
            }
        }

        int t = posList.size();

        int[] pos = new int[t];
        int[] prefSum = new int[t + 1];
        long[] hash = new long[t + 1];
        long[] pow10 = new long[t + 1];

        pow10[0] = 1;
        for (int i = 0; i < t; i++) {
            pos[i] = posList.get(i);
            prefSum[i + 1] = prefSum[i] + digitList.get(i);
            hash[i + 1] = (hash[i] * 10 + digitList.get(i)) % MOD;
            pow10[i + 1] = (pow10[i] * 10) % MOD;
        }

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int l = queries[i][0];
            int r = queries[i][1];

            int left = lowerBound(pos, l);
            int right = upperBound(pos, r) - 1;

            if (left > right) {
                ans[i] = 0;
                continue;
            }

            int len = right - left + 1;
            int sum = prefSum[right + 1] - prefSum[left];

            long x = (hash[right + 1] - hash[left] * pow10[len]) % MOD;
            if (x < 0) x += MOD;

            ans[i] = (int) (x * sum % MOD);
        }

        return ans;
    }

    private int lowerBound(int[] arr, int target) {
        int l = 0, r = arr.length;
        while (l < r) {
            int mid = (l + r) >>> 1;
            if (arr[mid] >= target)
                r = mid;
            else
                l = mid + 1;
        }
        return l;
    }

    private int upperBound(int[] arr, int target) {
        int l = 0, r = arr.length;
        while (l < r) {
            int mid = (l + r) >>> 1;
            if (arr[mid] > target)
                r = mid;
            else
                l = mid + 1;
        }
        return l;
    }
}