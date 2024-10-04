package com.practicum.yandex.repositories;

import com.practicum.yandex.interfaces.HistoryManager;
import com.practicum.yandex.tasks.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private final ArrayList<Task> lastTasks;

    public InMemoryHistoryManager() {
        lastTasks = new ArrayList<>();
    }

    public ArrayList<Task> getHistory() {
        return this.lastTasks;
    }

    public void add(Task task) {
        if (task != null) {
            if (this.lastTasks.size() == 10) {
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
