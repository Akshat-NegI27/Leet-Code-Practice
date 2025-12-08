class Solution {
    public boolean isThree(int n) {
        if (n < 4) return false;

        int root = (int) Math.sqrt(n);
        if (root * root != n) return false;

        return isPrime(root);
    }

    private boolean isPrime(int num) {
        if (num < 2) return false;
        int r = (int) Math.sqrt(num);
        for (int i = 2; i <= r; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
}
