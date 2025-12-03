import java.util.*;

class Solution {

    static class Pair implements Comparable<Pair> {
        int a, b;
        Pair(int a, int b) { this.a = a; this.b = b; }
        public int compareTo(Pair o) {
            if (a != o.a) return a - o.a;
            return b - o.b;
        }
        public boolean equals(Object o) {
            if (!(o instanceof Pair)) return false;
            Pair p = (Pair)o;
            return a == p.a && b == p.b;
        }
        public int hashCode() { return Objects.hash(a, b); }
    }

    static class Quad implements Comparable<Quad> {
        Pair p1, p2;
        Quad(Pair a, Pair b) { this.p1 = a; this.p2 = b; }
        public int compareTo(Quad o) {
            int c = p1.compareTo(o.p1);
            if (c != 0) return c;
            return p2.compareTo(o.p2);
        }
    }

    public int countTrapezoids(int[][] points) {
        int n = points.length;
        int n2 = n * (n - 1) / 2;

        Quad[] slope_inter = new Quad[n2];
        Quad[] mid_slope = new Quad[n2];

        int k = 0;

        for (int i = 0; i < n - 1; i++) {
            int x0 = points[i][0], y0 = points[i][1];
            for (int j = i + 1; j < n; j++) {
                int x1 = points[j][0], y1 = points[j][1];

                int a = y1 - y0;
                int b = x0 - x1;
                int c = y0 * x1 - y1 * x0;

                if (a == 0 && b < 0) {
                    b = -b; c = -c;
                } else if (a < 0) {
                    a = -a; b = -b; c = -c;
                }

                int gm = gcd(Math.abs(a), Math.abs(b));
                int gc = gcd(gm, Math.abs(c));

                Pair slope = new Pair(a / gm, b / gm);
                Pair inter = new Pair(c / gc, gm / gc);

                Pair mid = new Pair(x0 + x1, y0 + y1);

                slope_inter[k] = new Quad(slope, inter);
                mid_slope[k] = new Quad(mid, slope);

                k++;
            }
        }

        Arrays.sort(slope_inter);
        Arrays.sort(mid_slope);

        int ans = 0;

        {
            ArrayList<Integer> groups = new ArrayList<>();
            Quad last = slope_inter[0];
            int sameM = 1;
            int sameB = 1;

            for (int i = 1; i < n2; i++) {
                Quad cur = slope_inter[i];
                if (cur.p1.equals(last.p1)) {
                    sameM++;
                    if (cur.p2.equals(last.p2)) {
                        sameB++;
                    } else {
                        groups.add(sameB);
                        sameB = 1;
                        last.p2 = cur.p2;
                    }
                } else {
                    groups.add(sameB);
                    int sum = 0;
                    for (int x : groups) sum += x * (sameM - x);
                    ans += sum / 2;
                    groups.clear();
                    last = cur;
                    sameM = sameB = 1;
                }
            }
            groups.add(sameB);
            int sum = 0;
            for (int x : groups) sum += x * (sameM - x);
            ans += sum / 2;
        }

        {
            ArrayList<Integer> groups = new ArrayList<>();
            Quad last = mid_slope[0];
            int sameMid = 1;
            int sameM = 1;

            for (int i = 1; i < n2; i++) {
                Quad cur = mid_slope[i];
                if (cur.p1.equals(last.p1)) {
                    sameMid++;
                    if (cur.p2.equals(last.p2)) {
                        sameM++;
                    } else {
                        groups.add(sameM);
                        sameM = 1;
                        last.p2 = cur.p2;
                    }
                } else {
                    groups.add(sameM);
                    int sum = 0;
                    for (int x : groups) sum += x * (sameMid - x);
                    ans -= sum / 2;
                    groups.clear();
                    last = cur;
                    sameMid = sameM = 1;
                }
            }

            groups.add(sameM);
            int sum = 0;
            for (int x : groups) sum += x * (sameMid - x);
            ans -= sum / 2;
        }

        return ans;
    }

    static int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}
