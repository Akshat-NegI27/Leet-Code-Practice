import java.util.*;

class Solution {
    static class State {
        int x, y, used;
        long cost;
        State(int x, int y, int used, long cost) {
            this.x = x;
            this.y = y;
            this.used = used;
            this.cost = cost;
        }
    }

    public int minCost(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        long INF = Long.MAX_VALUE / 4;

        long[][][] dist = new long[m][n][k + 1];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                Arrays.fill(dist[i][j], INF);

        List<int[]> cells = new ArrayList<>();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                cells.add(new int[]{grid[i][j], i, j});

        cells.sort(Comparator.comparingInt(a -> a[0]));

        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a.cost));
        dist[0][0][0] = 0;
        pq.offer(new State(0, 0, 0, 0));

        int[] dx = {1, 0};
        int[] dy = {0, 1};

        int[] ptr = new int[k + 1];

        while (!pq.isEmpty()) {
            State cur = pq.poll();
            if (cur.cost > dist[cur.x][cur.y][cur.used]) continue;

            if (cur.x == m - 1 && cur.y == n - 1)
                return (int) cur.cost;

            for (int d = 0; d < 2; d++) {
                int nx = cur.x + dx[d];
                int ny = cur.y + dy[d];
                if (nx < m && ny < n) {
                    long nc = cur.cost + grid[nx][ny];
                    if (nc < dist[nx][ny][cur.used]) {
                        dist[nx][ny][cur.used] = nc;
                        pq.offer(new State(nx, ny, cur.used, nc));
                    }
                }
            }

            if (cur.used < k) {
                while (ptr[cur.used] < cells.size()
                        && cells.get(ptr[cur.used])[0] <= grid[cur.x][cur.y]) {
                    int[] c = cells.get(ptr[cur.used]);
                    int nx = c[1], ny = c[2];
                    if (cur.cost < dist[nx][ny][cur.used + 1]) {
                        dist[nx][ny][cur.used + 1] = cur.cost;
                        pq.offer(new State(nx, ny, cur.used + 1, cur.cost));
                    }
                    ptr[cur.used]++;
                }
            }
        }
        return -1;
    }
}
