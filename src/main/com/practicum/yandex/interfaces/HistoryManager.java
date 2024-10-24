package com.practicum.yandex.interfaces;

import com.practicum.yandex.tasks.Task;

import java.util.List;
import java.util.UUID;

public interface HistoryManager {
    void add(Task task);

    void remove(UUID uuid);

    List<Task> getHistory();
}
