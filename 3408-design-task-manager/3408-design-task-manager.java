import java.util.*;

class Task {
    public final int userId;
    public final int taskId;
    public final int priority;

    public Task(int userId, int taskId, int priority) {
        this.userId = userId;
        this.taskId = taskId;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return String.format("Task{user=%d,id=%d,prio=%d}", userId, taskId, priority);
    }
}

class TaskManager {
    private final Map<Integer, Task> taskMap = new HashMap<>();

    private final PriorityQueue<Task> heap = new PriorityQueue<>(
        (a, b) -> {
            if (a.priority != b.priority) return Integer.compare(b.priority, a.priority);
            return Integer.compare(b.taskId, a.taskId); // FIX: larger taskId first
        }
    );

    public TaskManager(List<List<Integer>> tasks) {
        if (tasks == null) return;
        for (List<Integer> t : tasks) {
            add(t.get(0), t.get(1), t.get(2));
        }
    }

    public void add(int userId, int taskId, int priority) {
        Task task = new Task(userId, taskId, priority);
        taskMap.put(taskId, task);
        heap.offer(task);
    }

    public void edit(int taskId, int newPriority) {
        Task old = taskMap.get(taskId);
        if (old == null) return;
        Task updated = new Task(old.userId, taskId, newPriority);
        taskMap.put(taskId, updated);
        heap.offer(updated);
    }

    public void rmv(int taskId) {
        taskMap.remove(taskId);
    }

    public int execTop() {
        while (!heap.isEmpty()) {
            Task top = heap.poll();
            Task canonical = taskMap.get(top.taskId);
            if (canonical == null) continue; // deleted
            if (canonical.priority != top.priority || canonical.userId != top.userId) continue; // stale
            taskMap.remove(top.taskId);
            return top.userId;
        }
        return -1;
    }

    public List<Task> snapshotOrdered() {
        List<Task> list = new ArrayList<>(taskMap.values());
        list.sort((a, b) -> {
            if (a.priority != b.priority) return Integer.compare(b.priority, a.priority);
            return Integer.compare(b.taskId, a.taskId); // FIX
        });
        return list;
    }

    public int size() {
        return taskMap.size();
    }
}
