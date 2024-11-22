package test.com.practicum.yandex.utils;

import com.practicum.yandex.utils.TaskConstruct;

import java.time.Duration;
import java.time.LocalDateTime;

public class TasksDescriptionForTests {
    public static TaskConstruct taskRefactoringCode =
            new TaskConstruct(
                    "Рефакторинг кода",
                    "Почистить код от всякого мусора",
                    LocalDateTime.of(2024, 9, 5, 12, 0, 0),
                    Duration.ofMinutes(30));

    public static TaskConstruct taskRoomClearing =
            new TaskConstruct("Убрать комнату", "Убраться перед приездом родителей",
                    LocalDateTime.of(2024, 9, 9, 9, 9, 9),
                    Duration.ofMinutes(50));

    public static TaskConstruct epicPinguinProject =
            new TaskConstruct("Pinguin Project", "Написать бэк для сервиса полнотекстового поиска",
                    LocalDateTime.of(2024, 11, 11, 11, 11, 0),
                    Duration.ofMinutes(45));

    public static TaskConstruct epicELKStack =
            new TaskConstruct(
                    "Изучаем ELK-стек",
                    "Изучить систему индексации и полноготекстового поиска ElasticSearch",
                    LocalDateTime.of(2023, 3, 2, 12, 0, 0),
                    Duration.ofMinutes(25));

    public static TaskConstruct epicDevelopProject =
            new TaskConstruct("Develop Project", "Реализовать голосовой проигрыватель",
                    LocalDateTime.of(2024, 9, 17, 14, 0, 0),
                    Duration.ofMinutes(35));

    public static TaskConstruct epicAndromedaProject =
            new TaskConstruct("Andromeda Project", "Написать документацию к проекту Andromeda",
                    LocalDateTime.of(2024, 12, 5, 12, 0, 0),
                    Duration.ofMinutes(15));

    public static TaskConstruct subTaskRequestsAPI =
            new TaskConstruct(
                    "Разработать API-обработки запросов",
                    "Пишем несколько методов для обработки JSON",
                    LocalDateTime.of(2024, 5, 13, 14, 15, 40),
                    Duration.ofMinutes(10));

    public static TaskConstruct subTaskServiceTesting =
            new TaskConstruct(
                    "Протестировать сервис",
                    "Навесить юнит-тесты и провести тестировочную нагрузку через Apache JMeter",
                    LocalDateTime.of(2024, 11, 2, 5, 23, 0),
                    Duration.ofMinutes(20));

    public static TaskConstruct subTaskDocumentationWriting =
            new TaskConstruct("Написать документацию", "Описать основные методы и функционал",
                    LocalDateTime.of(2024, 10, 4, 12, 0, 0),
                    Duration.ofMinutes(30));
}
