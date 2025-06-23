class Solution {
    public long kMirror(int k, int n) {
        long ans = 0;
        StringBuilder A = new StringBuilder("0");

        while (n > 0) {
            while (true) {
                A = nextKMirror(A, k);
                long num = Long.parseLong(A.toString(), k);
                if (isPalindrome(num)) {
                    ans += num;
                    n--;
                    break;
                }
            }
        }
        return ans;
    }

    // Function to check if a number is palindrome in decimal
    private boolean isPalindrome(long num) {
        String s = Long.toString(num);
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) return false;
        }
        return true;
    }

    private StringBuilder nextKMirror(StringBuilder A, int k) {
        int len = A.length();
        for (int i = len / 2; i < len; i++) {
            int nextNum = A.charAt(i) - '0' + 1;
            if (nextNum < k) {
                A.setCharAt(i, (char) (nextNum + '0'));
                A.setCharAt(len - 1 - i, (char) (nextNum + '0'));
                for (int j = len / 2; j < i; j++) {
                    A.setCharAt(j, '0');
                    A.setCharAt(len - 1 - j, '0');
                }
                return A;
            }
        }
        StringBuilder newA = new StringBuilder();
        newA.append('1');
        for (int i = 0; i < len - 1; i++) newA.append('0');
        newA.append('1');
        return newA;
    }
}
