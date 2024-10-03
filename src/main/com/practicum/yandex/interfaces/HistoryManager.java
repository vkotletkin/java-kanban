package com.practicum.yandex.interfaces;

import com.practicum.yandex.tasks.Task;

import java.util.ArrayList;

public interface HistoryManager {
    void add(Task task);

    ArrayList<Task> getHistory();
}
