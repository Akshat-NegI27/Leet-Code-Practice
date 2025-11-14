class Solution {
    public int[][] rangeAddQueries(int n, int[][] queries) {
        int[][] ans = new int[n][n];
        int[][] prefix = new int[n][n + 1];

        for (int[] q : queries) {
            int row1 = q[0];
            int col1 = q[1];
            int row2 = q[2];
            int col2 = q[3];

            for (int i = row1; i <= row2; i++) {
                prefix[i][col1] += 1;
                prefix[i][col2 + 1] -= 1;
            }
        }

        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = 0; j < n; j++) {
                sum += prefix[i][j];
                ans[i][j] = sum;
            }
        }
        return ans;
    }
}