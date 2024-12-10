package com.practicum.yandex.tasks;

import com.practicum.yandex.tasks.statuses.TaskStatus;
import com.practicum.yandex.utils.TimeMetrics;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Task {

    private final String name;
    private final String description;
    private final UUID uuid;
    private final TaskStatus taskStatus;
    private final LocalDateTime startTime;
    private final Duration duration;

    @Override
    public String toString() {
        return "Task{"
                + "name='"
                + name
                + '\''
                + ", description='"
                + description
                + '\''
                + ", uuid="
                + uuid
                + ", taskStatus="
                + taskStatus
                + '}';
    }

    public Task(String name, String description, TaskStatus taskStatus) {
        this.name = name;
        this.description = description;
        this.uuid = UUID.randomUUID();
        this.taskStatus = taskStatus;
        this.startTime = LocalDateTime.now();
        this.duration = Duration.ZERO;
    }

    public Task(String name, String description, UUID uuid, TaskStatus taskStatus) {
        this.name = name;
        this.description = description;
        this.uuid = uuid;
        this.taskStatus = taskStatus;
        this.startTime = LocalDateTime.now();
        this.duration = Duration.ZERO;
    }

    public Task(
            String name,
            String description,
            TaskStatus taskStatus,
            LocalDateTime startTime,
            Duration duration) {
        this.name = name;
        this.description = description;
        this.uuid = UUID.randomUUID();
        this.taskStatus = taskStatus;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Task(
            String name,
            String description,
            UUID uuid,
            TaskStatus taskStatus,
            LocalDateTime startTime,
            Duration duration) {
        this.name = name;
        this.description = description;
        this.uuid = uuid;
        this.taskStatus = taskStatus;
        this.startTime = startTime;
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(uuid, task.uuid);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public UUID getUUID() {
        return uuid;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public TimeMetrics getTimeMetrics() {
        return new TimeMetrics(getStartTime(), getEndTime(), getDuration());
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration.toMinutes());
    }
}
