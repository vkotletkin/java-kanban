package com.practicum.yandex.tasks;

import com.practicum.yandex.tasks.statuses.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class SubTask extends Task {
    private final UUID epicTaskUUID;

    public SubTask(String name, String description, TaskStatus taskStatus, UUID epicTaskUUID) {
        super(name, description, taskStatus, LocalDateTime.now(), Duration.ofSeconds(0));
        this.epicTaskUUID = epicTaskUUID;
    }

    public SubTask(
            String name, String description, UUID uuid, TaskStatus taskStatus, UUID epicTaskUUID) {
        super(name, description, uuid, taskStatus, LocalDateTime.now(), Duration.ofSeconds(0));
        this.epicTaskUUID = epicTaskUUID;
    }

    public SubTask(
            String name,
            String description,
            TaskStatus taskStatus,
            UUID epicTaskUUID,
            LocalDateTime startTime,
            Duration duration) {
        super(name, description, taskStatus, startTime, duration);
        this.epicTaskUUID = epicTaskUUID;
    }

    public SubTask(
            String name,
            String description,
            UUID uuid,
            TaskStatus taskStatus,
            UUID epicTaskUUID,
            LocalDateTime startTime,
            Duration duration) {
        super(name, description, uuid, taskStatus, startTime, duration);
        this.epicTaskUUID = epicTaskUUID;
    }

    public UUID getEpicTaskUUID() {
        return epicTaskUUID;
    }

    @Override
    public String toString() {
        return "SubTask{"
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
                + ", epicTaskUUID="
                + epicTaskUUID
                + '}';
    }
}
