package com.practicum.yandex.utils;

public class TaskConstruct {
    private String name;
    private String description;

    TaskConstruct(String name, String description) {
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
