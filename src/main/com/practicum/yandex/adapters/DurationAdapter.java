package com.practicum.yandex.adapters;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.practicum.yandex.interfaces.TaskManager;
import com.practicum.yandex.services.Managers;
import com.practicum.yandex.tasks.Task;
import com.practicum.yandex.tasks.statuses.TaskStatus;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

public class DurationAdapter extends TypeAdapter<Duration> {
    @Override
    public void write(final JsonWriter jsonWriter, final Duration duration) throws IOException {
        jsonWriter.value(duration.toString());
    }

    @Override
    public Duration read(final JsonReader jsonReader) throws IOException {
        return Duration.parse(jsonReader.nextString());
    }
}
