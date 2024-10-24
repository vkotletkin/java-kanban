package com.practicum.yandex;

import com.practicum.yandex.interfaces.TaskManager;
import com.practicum.yandex.services.Managers;
import com.practicum.yandex.tasks.EpicTask;
import com.practicum.yandex.tasks.SubTask;
import com.practicum.yandex.tasks.Task;
import com.practicum.yandex.tasks.statuses.TaskStatus;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        Task taskFirst =
                taskManager.createTask(
                        "Рефакторинг кода",
                        "Почистить код от всякого мусора",
                        UUID.randomUUID(),
                        TaskStatus.NEW);

        Task taskSecond =
                taskManager.createTask(
                        "Убрать комнату",
                        "Убраться перед приездом родителей",
                        UUID.randomUUID(),
                        TaskStatus.NEW);

        EpicTask epicTaskFirst =
                taskManager.createEpicTask(
                        "Pinguin Project",
                        "Написать бэк для сервиса полнотекстового поиска",
                        UUID.randomUUID());

        SubTask subTaskFirst =
                taskManager.createSubTask(
                        "Разработать API-обработки запросов",
                        "Пишем несколько методов для обработки JSON",
                        UUID.randomUUID(),
                        TaskStatus.NEW,
                        epicTaskFirst.getUUID());

        SubTask subTaskSecond =
                taskManager.createSubTask(
                        "Протестировать сервис",
                        "Навесить юнит-тесты и провести тестировочную нагрузку через Apache JMeter",
                        UUID.randomUUID(),
                        TaskStatus.NEW,
                        epicTaskFirst.getUUID());

        SubTask subTaskThird =
                taskManager.createSubTask(
                        "Написать документацию",
                        "Описать основные методы и функционал",
                        UUID.randomUUID(),
                        TaskStatus.NEW,
                        epicTaskFirst.getUUID());

        EpicTask epicTaskElasticKibana =
                taskManager.createEpicTask(
                        "Изучить систему индексации и полноготекстового поиска ElasticSearch",
                        "Изучаем ELK-стек",
                        UUID.randomUUID());

        taskManager.addNewTask(taskFirst);
        taskManager.addNewTask(taskSecond);
        taskManager.addNewEpicTask(epicTaskFirst);
        taskManager.addNewEpicTask(epicTaskElasticKibana);
        taskManager.addNewSubTask(subTaskFirst);
        taskManager.addNewSubTask(subTaskSecond);
        taskManager.addNewSubTask(subTaskThird);

        taskManager.getTaskByUUID(taskFirst.getUUID());
        taskManager.getTaskByUUID(taskFirst.getUUID());
        taskManager.getTaskByUUID(taskFirst.getUUID());
        taskManager.getTaskByUUID(taskSecond.getUUID());
        taskManager.getTaskByUUID(taskFirst.getUUID());
        taskManager.getTaskByUUID(taskFirst.getUUID());
        taskManager.getTaskByUUID(taskFirst.getUUID());
        taskManager.getTaskByUUID(taskSecond.getUUID());
        taskManager.getEpicTaskByUUID(epicTaskFirst.getUUID());
        taskManager.getEpicTaskByUUID(epicTaskElasticKibana.getUUID());
        taskManager.getSubTaskByUUID(subTaskFirst.getUUID());
        taskManager.getSubTaskByUUID(subTaskThird.getUUID());
        taskManager.getSubTaskByUUID(subTaskFirst.getUUID());
        taskManager.getSubTaskByUUID(subTaskSecond.getUUID());
        taskManager.getEpicTaskByUUID(epicTaskFirst.getUUID());
        taskManager.getEpicTaskByUUID(epicTaskElasticKibana.getUUID());
        taskManager.getTaskByUUID(taskFirst.getUUID());

        System.out.println("--- After get tasks ---");
        System.out.println(taskManager.getHistory());
        System.out.println();

        System.out.println("--- After delete taskSecond and epicTaskFirst (with 3 subtasks)");
        taskManager.deleteTaskByUUID(taskSecond.getUUID());
        taskManager.deleteEpicTaskByUUID(epicTaskFirst.getUUID());

        System.out.println(taskManager.getHistory());
    }
}
