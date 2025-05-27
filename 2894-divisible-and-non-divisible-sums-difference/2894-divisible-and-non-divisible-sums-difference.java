class Solution {
    public int differenceOfSums(int n, int m) {
        final int sum = (1 + n) * n / 2;
        final int num2 = getDivisibleSum(n, m);
        final int num1 = sum - num2;
        return num1 - num2;
    }

    private int getDivisibleSum(int n, int m) {
        final int last = n / m * m;
        if (last == 0)
            return 0;
        final int first = m;
        final int count = (last - first) / m + 1;
        return (first + last) * count / 2;
    }
}