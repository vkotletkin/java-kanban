package com.practicum.yandex.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.practicum.yandex.utils.DataFormats;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    private final DateTimeFormatter dateTimeFormatter = DataFormats.DATE_FORMAT;

    @Override
    public void write(final JsonWriter jsonWriter, final LocalDateTime localDateTime)
            throws IOException {
        jsonWriter.value(localDateTime.format(dateTimeFormatter));
    }

    @Override
    public LocalDateTime read(final JsonReader jsonReader) throws IOException {
        return LocalDateTime.parse(jsonReader.nextString(), dateTimeFormatter);
    }
}
