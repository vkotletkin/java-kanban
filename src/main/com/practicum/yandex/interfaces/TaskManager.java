package com.practicum.yandex.interfaces;

import com.practicum.yandex.tasks.EpicTask;
import com.practicum.yandex.tasks.SubTask;
import com.practicum.yandex.tasks.Task;
import com.practicum.yandex.tasks.statuses.TaskStatus;

import java.util.ArrayList;
import java.util.UUID;

public interface TaskManager {
    Task createTask(String name, String description, UUID uuid, TaskStatus taskStatus);

    Task createTask(String name, String description, TaskStatus taskStatus);

    SubTask createSubTask(
            String name, String description, UUID uuid, TaskStatus taskStatus, UUID epicTaskUUID);

    SubTask createSubTask(
            String name, String description, TaskStatus taskStatus, UUID epicTaskUUID);

    EpicTask createEpicTask(String name, String description, UUID uuid);

    EpicTask createEpicTask(String name, String description);

    ArrayList<Task> getTasks();

    ArrayList<SubTask> getSubTasks();

    ArrayList<EpicTask> getEpicTasks();

    void deleteAllTasks();

    void deleteAllSubTasks();

    void deleteAllEpicTasks();

    Task getTaskByUUID(UUID uuid);

    SubTask getSubTaskByUUID(UUID uuid);

    EpicTask getEpicTaskByUUID(UUID uuid);

    void addNewTask(Task task);

    void addNewSubTask(SubTask subtask);

    void addNewEpicTask(EpicTask epicTask);

    void updateTask(Task task);

    void updateSubTask(SubTask subtask);

    void updateEpicTask(EpicTask epicTask);

    void deleteTaskByUUID(UUID uuid);

    void deleteSubTaskByUUID(UUID uuid);

    void deleteEpicTaskByUUID(UUID uuid);

    ArrayList<SubTask> getEpicSubTasks(UUID epicUUID);

    ArrayList<Task> getHistory();
}
