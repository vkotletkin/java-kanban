package test.com.practicum.yandex.utils;

import com.practicum.yandex.utils.TaskConstruct;

public class TasksDescriptionForTests {
    public static TaskConstruct taskRefactoringCode =
            new TaskConstruct("Рефакторинг кода", "Почистить код от всякого мусора");

    public static TaskConstruct taskRoomClearing =
            new TaskConstruct("Убрать комнату", "Убраться перед приездом родителей");

    public static TaskConstruct epicPinguinProject =
            new TaskConstruct("Pinguin Project", "Написать бэк для сервиса полнотекстового поиска");

    public static TaskConstruct epicELKStack =
            new TaskConstruct(
                    "Изучаем ELK-стек",
                    "Изучить систему индексации и полноготекстового поиска ElasticSearch");

    public static TaskConstruct epicDevelopProject =
            new TaskConstruct("Develop Project", "Реализовать голосовой проигрыватель");

    public static TaskConstruct epicAndromedaProject =
            new TaskConstruct("Andromeda Project", "Написать документацию к проекту Andromeda");

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
}
