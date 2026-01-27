class Solution {
    public int minCost(int n, int[][] edges) {
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();

        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            graph[u].add(new int[]{v, w});
            graph[v].add(new int[]{u, 2 * w});
        }

        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;

        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[1]));
        pq.offer(new long[]{0, 0});

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            int u = (int) cur[0];
            long d = cur[1];

            if (d > dist[u]) continue;
            if (u == n - 1) return (int) d;

            for (int[] nxt : graph[u]) {
                int v = nxt[0];
                long w = nxt[1];
                if (dist[v] > d + w) {
                    dist[v] = d + w;
                    pq.offer(new long[]{v, dist[v]});
                }
            }
        }

        return -1;
    }
}