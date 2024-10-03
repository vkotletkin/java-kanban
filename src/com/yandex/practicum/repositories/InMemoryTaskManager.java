package com.yandex.practicum.repositories;

import com.yandex.practicum.interfaces.HistoryManager;
import com.yandex.practicum.interfaces.TaskManager;
import com.yandex.practicum.services.Managers;
import com.yandex.practicum.tasks.EpicTask;
import com.yandex.practicum.tasks.SubTask;
import com.yandex.practicum.tasks.Task;
import com.yandex.practicum.tasks.statuses.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<UUID, Task> tasks;
    private final HashMap<UUID, SubTask> subtasks;
    private final HashMap<UUID, EpicTask> epicTasks;

    private final HistoryManager historyManager;

    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epicTasks = new HashMap<>();
        historyManager = Managers.getDefaultHistory();
    }

    @Override
    public Task createTask(String name, String description, UUID uuid, TaskStatus taskStatus) {
        return new Task(name, description, uuid, taskStatus);
    }

    @Override
    public SubTask createSubTask(
            String name, String description, UUID uuid, TaskStatus taskStatus, UUID epicTaskUUID) {
        return new SubTask(name, description, uuid, taskStatus, epicTaskUUID);
    }

    @Override
    public EpicTask createEpicTask(String name, String description, UUID uuid) {
        return new EpicTask(name, description, uuid);
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public ArrayList<EpicTask> getEpicTasks() {
        return new ArrayList<>(epicTasks.values());
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
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

    @Override
    public void deleteAllEpicTasks() {
        epicTasks.clear();
        subtasks.clear();
    }

    @Override
    public Task getTaskByUUID(UUID uuid) {
        historyManager.add(tasks.get(uuid));
        return tasks.get(uuid);
    }

    @Override
    public SubTask getSubTaskByUUID(UUID uuid) {
        historyManager.add(subtasks.get(uuid));
        return subtasks.get(uuid);
    }

    @Override
    public EpicTask getEpicTaskByUUID(UUID uuid) {
        historyManager.add(epicTasks.get(uuid));
        return epicTasks.get(uuid);
    }

    @Override
    public void createNewTask(Task task) {
        tasks.put(task.getUUID(), task);
    }

    @Override
    public void createNewSubTask(SubTask subtask) {
        subtasks.put(subtask.getUUID(), subtask);
        updateEpicStatus(subtask.getEpicTaskUUID());
    }

    @Override
    public void createNewEpicTask(EpicTask epicTask) {
        epicTasks.put(epicTask.getUUID(), epicTask);
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getUUID(), task);
    }

    @Override
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

    @Override
    public void updateEpicTask(EpicTask epicTask) {
        epicTasks.put(epicTask.getUUID(), epicTask);
    }

    @Override
    public void deleteTaskByUUID(UUID uuid) {
        tasks.remove(uuid);
    }

    @Override
    public void deleteSubTaskByUUID(UUID uuid) {
        UUID epicTaskUUID = subtasks.get(uuid).getEpicTaskUUID();
        subtasks.remove(uuid);
        updateEpicStatus(epicTaskUUID);
    }

    @Override
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

    @Override
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

    @Override
    public ArrayList<Task> getHistory() {
        return historyManager.getHistory();
    }
}
