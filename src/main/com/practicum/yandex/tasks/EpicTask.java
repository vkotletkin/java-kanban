package com.practicum.yandex.tasks;

import com.practicum.yandex.tasks.statuses.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class EpicTask extends Task {

    private final LocalDateTime endTime;

    public EpicTask(String name, String description, UUID uuid) {
        super(name, description, uuid, TaskStatus.NEW, LocalDateTime.now(), Duration.ofSeconds(0));
        endTime = LocalDateTime.now();
    }

    public EpicTask(String name, String description) {
        super(name, description, TaskStatus.NEW, LocalDateTime.now(), Duration.ofSeconds(0));
        endTime = LocalDateTime.now();
    }

    public EpicTask(String name, String description, UUID uuid, TaskStatus taskStatus) {
        super(name, description, uuid, taskStatus, LocalDateTime.now(), Duration.ofSeconds(0));
        endTime = LocalDateTime.now();
    }

    public EpicTask(
            String name,
            String description,
            UUID uuid,
            TaskStatus taskStatus,
            LocalDateTime startTime,
            LocalDateTime endTime,
            Duration duration) {
        super(name, description, uuid, taskStatus, startTime, duration);
        this.endTime = endTime;
    }

    public EpicTask(
            String name,
            String description,
            TaskStatus taskStatus,
            LocalDateTime startTime,
            LocalDateTime endTime,
            Duration duration) {
        super(name, description, taskStatus, startTime, duration);
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "EpicTask{"
                + "name='"
                + getName()
                + '\''
                + ", description='"
                + getDescription()
                + '\''
                + ", uuid="
                + getUUID()
                + ", taskStatus="
                + getTaskStatus()
                + '}';
    }
}
