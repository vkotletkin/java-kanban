package com.practicum.yandex.tasks;

import com.practicum.yandex.tasks.statuses.TaskStatus;

import java.util.UUID;

public class SubTask extends Task {
    private final UUID epicTaskUUID;

    public SubTask(
            String name, String description, UUID uuid, TaskStatus taskStatus, UUID epicTaskUUID) {
        super(name, description, uuid, taskStatus);
        this.epicTaskUUID = epicTaskUUID;
    }

    public SubTask(String name, String description, TaskStatus taskStatus, UUID epicTaskUUID) {
        super(name, description, taskStatus);
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
