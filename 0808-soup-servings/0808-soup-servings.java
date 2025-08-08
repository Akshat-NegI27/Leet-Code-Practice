class Solution {
    public double soupServings(int n) {
        if (n >= 4800)
            return 1.0;
        int m = (n + 24) / 25;
        mem = new Double[m + 1][m + 1];
        return dfs(m, m);
    }

    private Double[][] mem;

    private double dfs(int a, int b) {
        if (a <= 0 && b <= 0)
            return 0.5;
        if (a <= 0)
            return 1.0;
        if (b <= 0)
            return 0.0;
        if (mem[a][b] != null)
            return mem[a][b];

        mem[a][b] = 0.25 * (dfs(a - 4, b) +
                dfs(a - 3, b - 1) +
                dfs(a - 2, b - 2) +
                dfs(a - 1, b - 3));
        return mem[a][b];
    }
}
