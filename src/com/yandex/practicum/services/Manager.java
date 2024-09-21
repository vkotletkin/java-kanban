package com.yandex.practicum.services;

import com.yandex.practicum.tasks.EpicTask;
import com.yandex.practicum.tasks.SubTask;
import com.yandex.practicum.tasks.Task;
import com.yandex.practicum.tasks.statuses.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Manager {
    private final HashMap<UUID, Task> tasks;
    private final HashMap<UUID, SubTask> subtasks;
    private final HashMap<UUID, EpicTask> epicTasks;

    public Manager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epicTasks = new HashMap<>();
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<EpicTask> getEpicTasks() {
        return new ArrayList<>(epicTasks.values());
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllSubTasks() {
        subtasks.clear();
        for (EpicTask epicTask : epicTasks.values()) {
            epicTasks.put(
                    epicTask.getUUID(),
                    new EpicTask(
                            epicTask.getName(),
                            epicTask.getDescription(),
                            epicTask.getUUID(),
                            TaskStatus.NEW));
        }
    }

    public void deleteAllEpicTasks() {
        epicTasks.clear();
        subtasks.clear();
    }

    public Task getTaskByUUID(UUID uuid) {
        return tasks.get(uuid);
    }

    public SubTask getSubTaskByUUID(UUID uuid) {
        return subtasks.get(uuid);
    }

    public EpicTask getEpicTaskByUUID(UUID uuid) {
        return epicTasks.get(uuid);
    }

    public void createNewTask(Task task) {
        tasks.put(task.getUUID(), task);
    }

    public void createNewSubTask(SubTask subtask) {
        subtasks.put(subtask.getUUID(), subtask);
        updateEpicStatus(subtask.getEpicTaskUUID());
    }

    public void createNewEpicTask(EpicTask epicTask) {
        epicTasks.put(epicTask.getUUID(), epicTask);
    }

    public void updateTask(Task task) {
        tasks.put(task.getUUID(), task);
    }

    public void updateSubTask(SubTask subtask) {
        subtasks.put(subtask.getUUID(), subtask);
        updateEpicStatus(subtask.getEpicTaskUUID());
    }

    private void updateEpicStatus(UUID uuid) {
        EpicTask changedEpicTask = epicTasks.get(uuid);
        epicTasks.put(
                uuid,
                new EpicTask(
                        changedEpicTask.getName(),
                        changedEpicTask.getDescription(),
                        changedEpicTask.getUUID(),
                        calculateEpicTaskStatus(changedEpicTask.getUUID())));
    }

    public void updateEpicTask(EpicTask epicTask) {
        epicTasks.put(epicTask.getUUID(), epicTask);
    }

    public void deleteTaskByUUID(UUID uuid) {
        tasks.remove(uuid);
    }

    public void deleteSubTaskByUUID(UUID uuid) {
        UUID epicTaskUUID = subtasks.get(uuid).getEpicTaskUUID();
        subtasks.remove(uuid);
        updateEpicStatus(epicTaskUUID);
    }

    public void deleteEpicTaskByUUID(UUID uuid) {
        epicTasks.remove(uuid);
        ArrayList<UUID> uuidsToDelete = new ArrayList<>();
        for (Map.Entry<UUID, SubTask> subTask : subtasks.entrySet()) {
            if (subTask.getValue().getEpicTaskUUID().equals(uuid)) {
                uuidsToDelete.add(subTask.getValue().getUUID());
            }
        }
        for (UUID uuidToDelete : uuidsToDelete) {
            subtasks.remove(uuidToDelete);
        }
    }

    public ArrayList<SubTask> getEpicSubTasks(UUID epicUUID) {
        ArrayList<SubTask> epicSubtasks = new ArrayList<>();
        for (SubTask subtask : subtasks.values()) {
            if (subtask.getEpicTaskUUID().equals(epicUUID)) {
                epicSubtasks.add(subtask);
            }
        }
        return epicSubtasks;
    }

    private TaskStatus calculateEpicTaskStatus(UUID uuid) {
        int newSubTaskCounter = 0;
        int inProgressTaskCounter = 0;
        int doneTaskCounter = 0;

        for (SubTask subtask : subtasks.values()) {
            if (subtask.getEpicTaskUUID().equals(uuid)) {
                switch (subtask.getTaskStatus()) {
                    case NEW -> newSubTaskCounter++;
                    case IN_PROGRESS -> inProgressTaskCounter++;
                    case DONE -> doneTaskCounter++;
                }
            }
        }

        if (doneTaskCounter == 0 && inProgressTaskCounter == 0) {
            return TaskStatus.NEW;
        } else if (newSubTaskCounter == 0 && inProgressTaskCounter == 0) {
            return TaskStatus.DONE;
        } else {
            return TaskStatus.IN_PROGRESS;
        }
    }
}
