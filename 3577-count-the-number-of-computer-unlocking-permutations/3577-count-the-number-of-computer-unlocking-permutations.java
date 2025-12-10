class Solution {
    private int MOD = 1000000007;
    
    public int countPermutations(int[] complexity) {
        int n = complexity.length;
        
        // Quick check: can each computer be unlocked?
        for (int i = 1; i < n; i++) {
            boolean found = false;
            for (int j = 0; j < i; j++) {
                if (complexity[j] < complexity[i]) {
                    found = true;
                    break;
                }
            }
            if (!found) return 0;
        }
        
        return count(complexity, new boolean[n], 0);
    }
    
    private int count(int[] complexity, boolean[] used, int pos) {
        if (pos == complexity.length) return 1;
        if (pos == 0) {
            used[0] = true;
            return count(complexity, used, 1);
        }
        
        long result = 0;
        for (int i = 1; i < complexity.length; i++) {
            if (used[i]) continue;
            
            boolean canUnlock = false;
            for (int j = 0; j < i; j++) {
                if (used[j] && complexity[j] < complexity[i]) {
                    canUnlock = true;
                    break;
                }
            }
            
            if (canUnlock) {
                used[i] = true;
                result = (result + count(complexity, used, pos + 1)) % MOD;
                used[i] = false;
            }
        }
        
        return (int) result;
    }
}