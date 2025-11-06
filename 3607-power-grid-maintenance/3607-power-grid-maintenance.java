class Solution {
    int[] parent, size;
    boolean[] online;
    Map<Integer, TreeSet<Integer>> compOnline;

    public int[] processQueries(int c, int[][] connections, int[][] queries) {
        parent = new int[c + 1];
        size = new int[c + 1];
        online = new boolean[c + 1];
        compOnline = new HashMap<>();

        for (int i = 1; i <= c; i++) {
            parent[i] = i;
            size[i] = 1;
            online[i] = true;
            compOnline.put(i, new TreeSet<>(List.of(i)));
        }

        for (int[] e : connections) union(e[0], e[1]);

        List<Integer> result = new ArrayList<>();

        for (int[] q : queries) {
            int type = q[0], x = q[1];

            if (type == 1) { // Maintenance query
                if (online[x]) {
                    result.add(x);
                } else {
                    int root = find(x);
                    TreeSet<Integer> active = compOnline.get(root);
                    if (active == null || active.isEmpty()) result.add(-1);
                    else result.add(active.first());
                }
            } else if (type == 2) { // Station goes offline
                if (online[x]) {
                    online[x] = false;
                    int root = find(x);
                    TreeSet<Integer> active = compOnline.get(root);
                    if (active != null) active.remove(x);
                }
            }
        }

        return result.stream().mapToInt(i -> i).toArray();
    }

    private int find(int x) {
        if (parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }

    private void union(int a, int b) {
        int ra = find(a), rb = find(b);
        if (ra == rb) return;

        if (size[ra] < size[rb]) {
            int tmp = ra; ra = rb; rb = tmp;
        }

        parent[rb] = ra;
        size[ra] += size[rb];

        TreeSet<Integer> setA = compOnline.get(ra);
        TreeSet<Integer> setB = compOnline.get(rb);

        if (setA == null) setA = new TreeSet<>();
        if (setB != null) setA.addAll(setB);

        compOnline.put(ra, setA);
        compOnline.remove(rb);
    }
}
