package com.yandex.practicum.repositories;

import com.yandex.practicum.interfaces.HistoryManager;
import com.yandex.practicum.tasks.Task;

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

            this.lastTasks.add(task);
        }
    }
}
