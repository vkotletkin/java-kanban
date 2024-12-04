package com.practicum.yandex.managers;

import com.practicum.yandex.exceptions.NotFoundException;
import com.practicum.yandex.exceptions.TimeIntersectionException;
import com.practicum.yandex.interfaces.HistoryManager;
import com.practicum.yandex.interfaces.TaskManager;
import com.practicum.yandex.services.Managers;
import com.practicum.yandex.tasks.EpicTask;
import com.practicum.yandex.tasks.SubTask;
import com.practicum.yandex.tasks.Task;
import com.practicum.yandex.tasks.statuses.TaskStatus;
import com.practicum.yandex.utils.TimeMetrics;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private final Map<UUID, Task> tasks;
    private final Map<UUID, SubTask> subtasks;
    private final Map<UUID, EpicTask> epicTasks;

    private final HistoryManager historyManager;

    private final Set<Task> prioritizedTasks;

    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epicTasks = new HashMap<>();
        historyManager = Managers.getDefaultHistory();
        prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));
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
    public Task createTask(
            String name,
            String description,
            UUID uuid,
            TaskStatus taskStatus,
            LocalDateTime startTime,
            Duration duration) {
        return new Task(name, description, uuid, taskStatus, startTime, duration);
    }

    @Override
    public Task createTask(
            String name,
            String description,
            TaskStatus taskStatus,
            LocalDateTime startTime,
            Duration duration) {
        return new Task(name, description, taskStatus, startTime, duration);
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
    public SubTask createSubTask(
            String name,
            String description,
            UUID uuid,
            TaskStatus taskStatus,
            UUID epicTaskUUID,
            LocalDateTime startTime,
            Duration duration) {
        return new SubTask(name, description, uuid, taskStatus, epicTaskUUID, startTime, duration);
    }

    @Override
    public SubTask createSubTask(
            String name,
            String description,
            TaskStatus taskStatus,
            UUID epicTaskUUID,
            LocalDateTime startTime,
            Duration duration) {
        return new SubTask(name, description, taskStatus, epicTaskUUID, startTime, duration);
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
    public EpicTask createEpicTask(
            String name,
            String description,
            UUID uuid,
            TaskStatus taskStatus,
            LocalDateTime startTime,
            LocalDateTime endTime,
            Duration duration) {
        return new EpicTask(name, description, uuid, taskStatus, startTime, endTime, duration);
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
            prioritizedTasks.remove(tasks.get(uuid));
        }
        tasks.clear();
    }

    @Override
    public void deleteAllSubTasks() {
        for (UUID uuid : subtasks.keySet()) {
            historyManager.remove(uuid);
            prioritizedTasks.remove(subtasks.get(uuid));
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
            prioritizedTasks.remove(subtasks.get(uuid));
        }

        epicTasks.clear();
        subtasks.clear();
    }

    @Override
    public Task getTaskByUUID(UUID uuid) {
        Task task = tasks.get(uuid);
        historyManager.add(task);
        return task;
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
        if (isIntervalsIntersection(task)) {
            System.out.println(
                    "Время начала задачи пересекается с существующей. Добавление отклонено.");
            return;
        }
        tasks.put(task.getUUID(), task);
        prioritizedTasks.add(task);
    }

    private boolean isIntervalsIntersection(Task task) {
        List<Boolean> intervalsCheckResult =
                prioritizedTasks.stream()
                        .map(
                                streamTask ->
                                        checkIntersectionOnIntervals(
                                                streamTask.getTimeMetrics(), task.getTimeMetrics()))
                        .toList();

        return intervalsCheckResult.contains(true);
    }

    @Override
    public void addNewSubTask(SubTask subtask) {
        if (isIntervalsIntersection(subtask)) {
            throw new TimeIntersectionException(
                    "Время начала задачи пересекается с существующей. Добавление отклонено.");
        }
        subtasks.put(subtask.getUUID(), subtask);
        updateEpicStatus(subtask.getEpicTaskUUID());
        prioritizedTasks.add(subtask);
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
        TimeMetrics timeMetrics = calculateEpicTimeMetrics(uuid);
        epicTasks.put(
                uuid,
                new EpicTask(
                        changedEpicTask.getName(),
                        changedEpicTask.getDescription(),
                        changedEpicTask.getUUID(),
                        calculateEpicTaskStatus(changedEpicTask.getUUID()),
                        timeMetrics.startDateTime(),
                        timeMetrics.endDateTime(),
                        timeMetrics.duration()));
    }

    @Override
    public void updateEpicTask(EpicTask epicTask) {
        epicTasks.put(epicTask.getUUID(), epicTask);
    }

    @Override
    public void deleteTaskByUUID(UUID uuid) {
        historyManager.remove(uuid);
        prioritizedTasks.remove(tasks.get(uuid));
        tasks.remove(uuid);
    }

    @Override
    public void deleteSubTaskByUUID(UUID uuid) {
        UUID epicTaskUUID = subtasks.get(uuid).getEpicTaskUUID();
        historyManager.remove(uuid);
        prioritizedTasks.remove(subtasks.get(uuid));
        subtasks.remove(uuid);
        updateEpicStatus(epicTaskUUID);
    }

    @Override
    public void deleteEpicTaskByUUID(UUID uuid) {
        epicTasks.remove(uuid);
        historyManager.remove(uuid);
        List<UUID> uuidsToDelete =
                subtasks.values().stream()
                        .filter(subTask -> subTask.getEpicTaskUUID().equals(uuid))
                        .map(Task::getUUID)
                        .toList();

        for (UUID uuidToDelete : uuidsToDelete) {
            subtasks.remove(uuidToDelete);
            historyManager.remove(uuidToDelete);
        }
    }

    @Override
    public List<SubTask> getEpicSubTasks(UUID epicUUID) {
        return subtasks.values().stream()
                .filter(subTask -> subTask.getEpicTaskUUID().equals(epicUUID))
                .toList();
    }

    private TimeMetrics calculateEpicTimeMetrics(UUID uuid) {
        List<LocalDateTime> startDateTimes = new ArrayList<>();
        List<Duration> subtasksDurations = new ArrayList<>();

        for (SubTask subtask : subtasks.values()) {
            if (subtask.getEpicTaskUUID().equals(uuid)) {
                startDateTimes.add(subtask.getStartTime());
                subtasksDurations.add(subtask.getDuration());
            }
        }

        Optional<LocalDateTime> earlierStartDateTime = Optional.empty();
        Optional<LocalDateTime> lastStartDateTime = Optional.empty();

        if (startDateTimes.size() != 0) {
            startDateTimes.sort(LocalDateTime::compareTo);
            earlierStartDateTime = Optional.ofNullable(startDateTimes.get(0));
            lastStartDateTime = Optional.ofNullable(startDateTimes.get(startDateTimes.size() - 1));
        }

        Duration sumOfDurations = Duration.ofSeconds(0);

        for (Duration duration : subtasksDurations) {
            sumOfDurations = sumOfDurations.plus(duration);
        }

        if (earlierStartDateTime.isPresent() && lastStartDateTime.isPresent()) {
            return new TimeMetrics(
                    earlierStartDateTime.get(), lastStartDateTime.get(), sumOfDurations);
        }
        return new TimeMetrics(null, null, sumOfDurations);
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

    @Override
    public Set<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    private boolean checkIntersectionOnIntervals(
            TimeMetrics firstTaskTimeMetrics, TimeMetrics secondTaskTimeMetrics) {
        return !(firstTaskTimeMetrics.endDateTime().isBefore(secondTaskTimeMetrics.startDateTime())
                || firstTaskTimeMetrics
                        .startDateTime()
                        .isAfter(secondTaskTimeMetrics.endDateTime()));
    }
}
