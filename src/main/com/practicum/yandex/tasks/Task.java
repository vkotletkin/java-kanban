package com.practicum.yandex.tasks;

import com.practicum.yandex.tasks.statuses.TaskStatus;

import java.util.Objects;
import java.util.UUID;

public class Task {

    private final String name;
    private final String description;
    private final UUID uuid;
    private final TaskStatus taskStatus;

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

    public Task(String name, String description, UUID uuid, TaskStatus taskStatus) {
        this.name = name;
        this.description = description;
        this.uuid = uuid;
        this.taskStatus = taskStatus;
    }

    public Task(String name, String description, TaskStatus taskStatus) {
        this.name = name;
        this.description = description;
        this.uuid = UUID.randomUUID();
        this.taskStatus = taskStatus;
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
}
