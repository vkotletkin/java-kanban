package com.practicum.yandex.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public record TaskConstruct(
        String name, String description, LocalDateTime localDateTime, Duration duration) {}
