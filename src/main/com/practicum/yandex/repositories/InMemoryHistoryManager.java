package com.practicum.yandex.repositories;

import com.practicum.yandex.interfaces.HistoryManager;
import com.practicum.yandex.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> lastTasks;
    private static final int MAX_ELEMENTS_IN_HISTORY = 10;

    public InMemoryHistoryManager() {
        lastTasks = new ArrayList<>();
    }

    public List<Task> getHistory() {
        return this.lastTasks;
    }

    public void add(Task task) {
        if (task != null) {
            if (this.lastTasks.size() == MAX_ELEMENTS_IN_HISTORY) {
                this.lastTasks.remove(0);
            }

            this.lastTasks.add(
                    new Task(
                            task.getName(),
                            task.getDescription(),
                            task.getUUID(),
                            task.getTaskStatus()));
        }
    }
}
