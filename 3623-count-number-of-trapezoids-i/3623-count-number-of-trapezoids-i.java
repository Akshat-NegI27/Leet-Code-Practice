class Solution {
    static final long MOD = 1_000_000_007;
    public int countTrapezoids(int[][] points) {
        Map<Integer,Integer> cnt = new HashMap<>();
        for (int[] p : points) cnt.put(p[1], cnt.getOrDefault(p[1],0)+1);
        long sum = 0, sumsq = 0;
        for (int k : cnt.values()) {
            if (k < 2) continue;
            long s = (long)k * (k - 1) / 2 % MOD;
            sum = (sum + s) % MOD;
            sumsq = (sumsq + s * s % MOD) % MOD;
        }
        long res = ( (sum * sum % MOD - sumsq + MOD) % MOD ) * ((MOD + 1) / 2) % MOD;
        return (int)res;
    }
}