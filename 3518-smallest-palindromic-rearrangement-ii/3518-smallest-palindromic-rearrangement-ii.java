import java.util.*;

class Solution {
    private final int MAX = 1_000_001;

    public String smallestPalindrome(String s, int k) {
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        if (!isPalindromePossible(count)) {
            return "";
        }

        int[] halfCount = new int[26];
        char midChar = 0;

        for (int i = 0; i < 26; i++) {
            halfCount[i] = count[i] / 2;
            if ((count[i] & 1) == 1) {
                midChar = (char) ('a' + i);
            }
        }

        int totalPerm = countArrangements(halfCount);
        if (k > totalPerm) {
            return "";
        }

        StringBuilder left = new StringBuilder();
        int halfLen = 0;
        for (int x : halfCount)
            halfLen += x;

        for (int pos = 0; pos < halfLen; pos++) {
            for (int i = 0; i < 26; i++) {
                if (halfCount[i] == 0)
                    continue;

                halfCount[i]--;

                int ways = countArrangements(halfCount);

                if (ways >= k) {
                    left.append((char) ('a' + i));
                    break;
                } else {
                    k -= ways;
                    halfCount[i]++;
                }
            }
        }

        StringBuilder ans = new StringBuilder();
        ans.append(left);
        if (midChar != 0)
            ans.append(midChar);
        ans.append(left.reverse());

        return ans.toString();
    }

    private boolean isPalindromePossible(int[] count) {
        int odd = 0;
        for (int x : count) {
            if ((x & 1) == 1)
                odd++;
        }
        return odd <= 1;
    }

    private int countArrangements(int[] count) {
        int total = 0;
        for (int x : count)
            total += x;

        long res = 1;

        for (int freq : count) {
            if (freq == 0)
                continue;

            res *= nCk(total, freq);

            if (res >= MAX)
                return MAX;

            total -= freq;
        }

        return (int) res;
    }

    private int nCk(int n, int k) {
        k = Math.min(k, n - k);

        long res = 1;

        for (int i = 1; i <= k; i++) {
            res = res * (n - i + 1) / i;

            if (res >= MAX)
                return MAX;
        }

        return (int) res;
    }
}