package com.practicum.yandex.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public record TimeMetrics(
        LocalDateTime startDateTime, LocalDateTime endDateTime, Duration duration) {}
