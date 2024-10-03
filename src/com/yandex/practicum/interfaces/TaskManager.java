package com.yandex.practicum.interfaces;

import com.yandex.practicum.tasks.EpicTask;
import com.yandex.practicum.tasks.SubTask;
import com.yandex.practicum.tasks.Task;
import com.yandex.practicum.tasks.statuses.TaskStatus;

import java.util.ArrayList;
import java.util.UUID;

public interface TaskManager {
    Task createTask(String name, String description, UUID uuid, TaskStatus taskStatus);

    SubTask createSubTask(
            String name, String description, UUID uuid, TaskStatus taskStatus, UUID epicTaskUUID);

    EpicTask createEpicTask(String name, String description, UUID uuid);

    ArrayList<Task> getTasks();

    ArrayList<SubTask> getSubTasks();

    ArrayList<EpicTask> getEpicTasks();

    void deleteAllTasks();

    void deleteAllSubTasks();

    void deleteAllEpicTasks();

    Task getTaskByUUID(UUID uuid);

    SubTask getSubTaskByUUID(UUID uuid);

    EpicTask getEpicTaskByUUID(UUID uuid);

    void createNewTask(Task task);

    void createNewSubTask(SubTask subtask);

    void createNewEpicTask(EpicTask epicTask);

    void updateTask(Task task);

    void updateSubTask(SubTask subtask);

    void updateEpicTask(EpicTask epicTask);

    void deleteTaskByUUID(UUID uuid);

    void deleteSubTaskByUUID(UUID uuid);

    void deleteEpicTaskByUUID(UUID uuid);

    ArrayList<SubTask> getEpicSubTasks(UUID epicUUID);

    ArrayList<Task> getHistory();
}
