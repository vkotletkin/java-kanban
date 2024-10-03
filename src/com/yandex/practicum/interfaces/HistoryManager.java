package com.yandex.practicum.interfaces;

import com.yandex.practicum.tasks.Task;
import java.util.ArrayList;

public interface HistoryManager {
    void add(Task task);

    ArrayList<Task> getHistory();
}
