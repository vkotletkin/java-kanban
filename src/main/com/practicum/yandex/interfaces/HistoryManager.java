package com.practicum.yandex.interfaces;

import com.practicum.yandex.tasks.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);

    List<Task> getHistory();
}
