package com.practicum.yandex.repositories;

import com.practicum.yandex.interfaces.HistoryManager;
import com.practicum.yandex.tasks.Task;
import com.practicum.yandex.utils.Node;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    // TODO DELETE private final List<Task> lastTasks;

    // TODO DELETE private static final int MAX_ELEMENTS_IN_HISTORY = 10;
    private final Map<UUID, Node> lastTasks = new HashMap<>();

    public InMemoryHistoryManager() {
        lastTasks = new ArrayList<>();
    }

    public List<Task> getHistory() {
        return this.lastTasks;
    }

    public void add(Task task) {
        if (task != null) {
            // TODO Delete
            //            if (this.lastTasks.size() == MAX_ELEMENTS_IN_HISTORY) {
            //                this.lastTasks.remove(0);
            //            }

            this.lastTasks.add(
                    new Task(
                            task.getName(),
                            task.getDescription(),
                            task.getUUID(),
                            task.getTaskStatus()));
        }
    }
}

class TaskLinkedList {

}
