class SegmentTree {
    int n;
    int[] xs;
    int[] coveredCount;
    int[] coveredWidth;

    SegmentTree(int[] xs) {
        this.xs = xs;
        this.n = xs.length - 1;
        this.coveredCount = new int[4 * n];
        this.coveredWidth = new int[4 * n];
    }

    void add(int l, int r, int val) {
        add(0, 0, n - 1, l, r, val);
    }

    int getCoveredWidth() {
        return coveredWidth[0];
    }

    void add(int idx, int lo, int hi, int l, int r, int val) {
        if (r <= xs[lo] || xs[hi + 1] <= l)
            return;

        if (l <= xs[lo] && xs[hi + 1] <= r) {
            coveredCount[idx] += val;
        } else {
            int mid = (lo + hi) / 2;
            add(2 * idx + 1, lo, mid, l, r, val);
            add(2 * idx + 2, mid + 1, hi, l, r, val);
        }

        if (coveredCount[idx] > 0) {
            coveredWidth[idx] = xs[hi + 1] - xs[lo];
        } else if (lo == hi) {
            coveredWidth[idx] = 0;
        } else {
            coveredWidth[idx] = coveredWidth[2 * idx + 1] + coveredWidth[2 * idx + 2];
        }
    }
}

class Solution {
    public double separateSquares(int[][] squares) {
        List<int[]> events = new ArrayList<>();
        TreeSet<Integer> xsSet = new TreeSet<>();

        for (int[] sq : squares) {
            int x = sq[0], y = sq[1], l = sq[2];
            events.add(new int[] { y, 1, x, x + l });
            events.add(new int[] { y + l, -1, x, x + l });
            xsSet.add(x);
            xsSet.add(x + l);
        }

        events.sort(Comparator.comparingInt(a -> a[0]));

        int[] xs = new int[xsSet.size()];
        int idx = 0;
        for (int v : xsSet)
            xs[idx++] = v;

        double halfArea = getArea(events, xs) / 2.0;

        SegmentTree tree = new SegmentTree(xs);
        long area = 0;
        int prevY = 0;

        for (int[] e : events) {
            int y = e[0], delta = e[1], xl = e[2], xr = e[3];
            int coveredWidth = tree.getCoveredWidth();
            long areaGain = (long) coveredWidth * (y - prevY);

            if (area + areaGain >= halfArea) {
                return prevY + (halfArea - area) / coveredWidth;
            }

            area += areaGain;
            tree.add(xl, xr, delta);
            prevY = y;
        }
        return 0.0;
    }

    private long getArea(List<int[]> events, int[] xs) {
        SegmentTree tree = new SegmentTree(xs);
        long total = 0;
        int prevY = 0;

        for (int[] e : events) {
            int y = e[0], delta = e[1], xl = e[2], xr = e[3];
            total += (long) tree.getCoveredWidth() * (y - prevY);
            tree.add(xl, xr, delta);
            prevY = y;
        }
        return total;
    }
}