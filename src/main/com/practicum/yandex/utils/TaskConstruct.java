package com.practicum.yandex.utils;

public class TaskConstruct {
    private final String name;
    private final String description;

    public TaskConstruct(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
