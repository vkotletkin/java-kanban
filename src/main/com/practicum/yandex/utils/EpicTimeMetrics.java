package com.practicum.yandex.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class EpicTimeMetrics {
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final Duration duration;

    public EpicTimeMetrics(
            LocalDateTime startDateTime, LocalDateTime endDateTime, Duration duration) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.duration = duration;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public Duration getDuration() {
        return duration;
    }
}
