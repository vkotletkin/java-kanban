package com.practicum.yandex.tasks;

import com.practicum.yandex.tasks.statuses.TaskStatus;

import java.util.UUID;

public class EpicTask extends Task {

    public EpicTask(String name, String description, UUID uuid) {
        super(name, description, uuid, TaskStatus.NEW);
    }

    public EpicTask(String name, String description) {
        super(name, description, TaskStatus.NEW);
    }

    public EpicTask(String name, String description, UUID uuid, TaskStatus taskStatus) {
        super(name, description, uuid, taskStatus);
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
