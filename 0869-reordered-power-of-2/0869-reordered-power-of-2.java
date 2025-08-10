class Solution {
    public boolean reorderedPowerOf2(int n) {
        int[] nCounter = counter(n);
        for (int i = 0; i < 30; ++i) {
            if (arraysAreEqual(nCounter, counter(1 << i))) {
                return true;
            }
        }
        return false;
    }

    private int[] counter(int n) {
        int[] count = new int[10];
        while (n > 0) {
            count[n % 10]++;
            n /= 10;
        }
        return count;
    }

    private boolean arraysAreEqual(int[] a, int[] b) {
        if (a.length != b.length) {
            return false;
        }
        for (int i = 0; i < a.length; ++i) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }
}