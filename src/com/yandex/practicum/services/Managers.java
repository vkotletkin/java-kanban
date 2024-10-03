package com.yandex.practicum.services;

import com.yandex.practicum.interfaces.HistoryManager;
import com.yandex.practicum.interfaces.TaskManager;
import com.yandex.practicum.repositories.InMemoryHistoryManager;
import com.yandex.practicum.repositories.InMemoryTaskManager;

public class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
