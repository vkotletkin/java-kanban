package com.practicum.yandex.services;

import com.practicum.yandex.interfaces.HistoryManager;
import com.practicum.yandex.interfaces.TaskManager;
import com.practicum.yandex.repositories.FileBackedTaskManager;
import com.practicum.yandex.repositories.InMemoryHistoryManager;
import com.practicum.yandex.repositories.InMemoryTaskManager;

public class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static TaskManager getFileBackedTaskManager() {
        return new FileBackedTaskManager();
    }

    public static TaskManager getFileBackedTaskManager(String pathToFileSave) {
        return new FileBackedTaskManager(pathToFileSave);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
