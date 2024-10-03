package com.practicum.yandex.repositories;

import com.practicum.yandex.tasks.Task;
import com.practicum.yandex.interfaces.HistoryManager;

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
