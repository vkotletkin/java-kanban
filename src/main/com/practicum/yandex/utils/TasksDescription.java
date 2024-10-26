package com.practicum.yandex.utils;

public class TasksDescription {
    public static TaskConstruct taskRefactoringCode =
            new TaskConstruct("Рефакторинг кода", "Почистить код от всякого мусора");

    public static TaskConstruct taskRoomClearing =
            new TaskConstruct("Убрать комнату", "Убраться перед приездом родителей");

    public static TaskConstruct epicPinguinProject =
            new TaskConstruct("Pinguin Project", "Написать бэк для сервиса полнотекстового поиска");

    public static TaskConstruct subTaskRequestsAPI =
            new TaskConstruct(
                    "Разработать API-обработки запросов",
                    "Пишем несколько методов для обработки JSON");

    public static TaskConstruct subTaskServiceTesting =
            new TaskConstruct(
                    "Протестировать сервис",
                    "Навесить юнит-тесты и провести тестировочную нагрузку через Apache JMeter");

    public static TaskConstruct subTaskDocumentationWriting =
            new TaskConstruct("Написать документацию", "Описать основные методы и функционал");

    public static TaskConstruct epicELKStack =
            new TaskConstruct(
                    "Изучаем ELK-стек",
                    "Изучить систему индексации и полноготекстового поиска ElasticSearch");
}
