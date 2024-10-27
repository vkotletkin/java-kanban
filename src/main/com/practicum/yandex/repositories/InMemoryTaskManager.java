package com.practicum.yandex.repositories;

import com.practicum.yandex.interfaces.HistoryManager;
import com.practicum.yandex.interfaces.TaskManager;
import com.practicum.yandex.services.Managers;
import com.practicum.yandex.tasks.EpicTask;
import com.practicum.yandex.tasks.SubTask;
import com.practicum.yandex.tasks.Task;
import com.practicum.yandex.tasks.statuses.TaskStatus;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private final Map<UUID, Task> tasks;
    private final Map<UUID, SubTask> subtasks;
    private final Map<UUID, EpicTask> epicTasks;

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
    public Task createTask(String name, String description, TaskStatus taskStatus) {
        return new Task(name, description, taskStatus);
    }

    @Override
    public SubTask createSubTask(
            String name, String description, UUID uuid, TaskStatus taskStatus, UUID epicTaskUUID) {
        return new SubTask(name, description, uuid, taskStatus, epicTaskUUID);
    }

    @Override
    public SubTask createSubTask(
            String name, String description, TaskStatus taskStatus, UUID epicTaskUUID) {
        return new SubTask(name, description, taskStatus, epicTaskUUID);
    }

    @Override
    public EpicTask createEpicTask(String name, String description, UUID uuid) {
        return new EpicTask(name, description, uuid);
    }

    @Override
    public EpicTask createEpicTask(String name, String description) {
        return new EpicTask(name, description);
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<SubTask> getSubTasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<EpicTask> getEpicTasks() {
        return new ArrayList<>(epicTasks.values());
    }

    @Override
    public void deleteAllTasks() {
        for (UUID uuid : tasks.keySet()) {
            historyManager.remove(uuid);
        }
        tasks.clear();
    }

    @Override
    public void deleteAllSubTasks() {
        for (UUID uuid : subtasks.keySet()) {
            historyManager.remove(uuid);
        }
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
        for (UUID uuid : epicTasks.keySet()) {
            historyManager.remove(uuid);
        }

        for (UUID uuid : subtasks.keySet()) {
            historyManager.remove(uuid);
        }

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
    public void addNewTask(Task task) {
        tasks.put(task.getUUID(), task);
    }

    @Override
    public void addNewSubTask(SubTask subtask) {
        subtasks.put(subtask.getUUID(), subtask);
        updateEpicStatus(subtask.getEpicTaskUUID());
    }

    @Override
    public void addNewEpicTask(EpicTask epicTask) {
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

    public void updateEpicTask(EpicTask epicTask) {
        epicTasks.put(epicTask.getUUID(), epicTask);
    }

    @Override
    public void deleteTaskByUUID(UUID uuid) {
        historyManager.remove(uuid);
        tasks.remove(uuid);
    }

    @Override
    public void deleteSubTaskByUUID(UUID uuid) {
        UUID epicTaskUUID = subtasks.get(uuid).getEpicTaskUUID();
        historyManager.remove(uuid);
        subtasks.remove(uuid);
        updateEpicStatus(epicTaskUUID);
    }

    @Override
    public void deleteEpicTaskByUUID(UUID uuid) {
        epicTasks.remove(uuid);
        historyManager.remove(uuid);
        List<UUID> uuidsToDelete = new ArrayList<>();
        for (Map.Entry<UUID, SubTask> subTask : subtasks.entrySet()) {
            if (subTask.getValue().getEpicTaskUUID().equals(uuid)) {
                uuidsToDelete.add(subTask.getValue().getUUID());
            }
        }
        for (UUID uuidToDelete : uuidsToDelete) {
            subtasks.remove(uuidToDelete);
            historyManager.remove(uuidToDelete);
        }
    }

    @Override
    public List<SubTask> getEpicSubTasks(UUID epicUUID) {
        List<SubTask> epicSubtasks = new ArrayList<>();
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
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}
