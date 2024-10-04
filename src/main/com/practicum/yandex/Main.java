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

        // Тестирование по Т
        TaskManager inMemoryTaskManager = Managers.getDefault();

        // Классы созданы именно так, а не переданы сразу в inMemoryTaskManager, так как нужно
        // сохранить UUID
        // для дальнейших действий

        Task taskFirst =
                inMemoryTaskManager.createTask(
                        "Рефакторинг кода",
                        "Почистить код от всякого мусора",
                        UUID.randomUUID(),
                        TaskStatus.NEW);

        Task taskSecond =
                inMemoryTaskManager.createTask(
                        "Убрать комнату",
                        "Убраться перед приездом родителей",
                        UUID.randomUUID(),
                        TaskStatus.NEW);

        EpicTask epicTaskFirst =
                inMemoryTaskManager.createEpicTask(
                        "Pinguin Project",
                        "Написать бэк для сервиса полнотекстового поиска",
                        UUID.randomUUID());

        SubTask subTaskFirst =
                inMemoryTaskManager.createSubTask(
                        "Разработать API-обработки запросов",
                        "Пишем несколько методов для обработки JSON",
                        UUID.randomUUID(),
                        TaskStatus.NEW,
                        epicTaskFirst.getUUID());

        SubTask subTaskSecond =
                inMemoryTaskManager.createSubTask(
                        "Протестировать сервис",
                        "Навесить юнит-тесты и провести тестировочную нагрузку через Apache JMeter",
                        UUID.randomUUID(),
                        TaskStatus.NEW,
                        epicTaskFirst.getUUID());

        EpicTask epicTaskELK =
                inMemoryTaskManager.createEpicTask(
                        "Изучить систему индексации и полноготекстового поиска ElasticSearch",
                        "Изучаем ELK-стек",
                        UUID.randomUUID());

        SubTask subTaskELK =
                inMemoryTaskManager.createSubTask(
                        "Читаем ХАБР",
                        "Почитать статьи про ELK",
                        UUID.randomUUID(),
                        TaskStatus.NEW,
                        epicTaskELK.getUUID());

        // Создайте две задачи, а также эпик с двумя подзадачами и эпик с одной подзадачей.

//        inMemoryTaskManager.createNewTask(taskFirst);
//        inMemoryTaskManager.createNewTask(taskSecond);
//
//        inMemoryTaskManager.createNewEpicTask(epicTaskFirst);
//
//        inMemoryTaskManager.createNewSubTask(subTaskFirst);
//        inMemoryTaskManager.createNewSubTask(subTaskSecond);
//
//        inMemoryTaskManager.createNewEpicTask(epicTaskELK);
//
//        inMemoryTaskManager.createNewSubTask(subTaskELK);

        // Распечатайте списки эпиков, задач и подзадач через System.out.println()
        System.out.println(inMemoryTaskManager.getTasks());
        System.out.println(inMemoryTaskManager.getEpicTasks());
        System.out.println(inMemoryTaskManager.getSubTasks());

        // Измените статусы созданных объектов, распечатайте их. Проверьте, что статус задачи и
        // подзадачи сохранился, а статус эпика рассчитался по статусам подзадач.

        inMemoryTaskManager.updateTask(
                inMemoryTaskManager.createTask(
                        "Рефакторинг кода",
                        "Почистить код от всякого мусора",
                        taskFirst.getUUID(),
                        TaskStatus.DONE));

        inMemoryTaskManager.updateTask(
                inMemoryTaskManager.createTask(
                        "Убрать комнату",
                        "Убраться перед приездом родителей",
                        taskSecond.getUUID(),
                        TaskStatus.IN_PROGRESS));

        inMemoryTaskManager.updateSubTask(
                inMemoryTaskManager.createSubTask(
                        "Разработать API-обработки запросов",
                        "Пишем несколько методов для обработки JSON",
                        subTaskFirst.getUUID(),
                        TaskStatus.DONE,
                        epicTaskFirst.getUUID()));

        inMemoryTaskManager.updateSubTask(
                inMemoryTaskManager.createSubTask(
                        "Протестировать сервис",
                        "Навесить юнит-тесты и провести тестировочную нагрузку через Apache JMeter",
                        subTaskSecond.getUUID(),
                        TaskStatus.IN_PROGRESS,
                        epicTaskFirst.getUUID()));

        inMemoryTaskManager.updateSubTask(
                inMemoryTaskManager.createSubTask(
                        "Читаем ХАБР",
                        "Почитать статьи про ELK",
                        subTaskELK.getUUID(),
                        TaskStatus.DONE,
                        epicTaskELK.getUUID()));

        System.out.println(
                """


                После изменения статусов объектов:

                """);

        System.out.println(inMemoryTaskManager.getTasks());
        System.out.println(inMemoryTaskManager.getEpicTasks());
        System.out.println(inMemoryTaskManager.getSubTasks());

        // И, наконец, попробуйте удалить одну из задач и один из эпиков.

        //        inMemoryTaskManager.deleteTaskByUUID(taskFirst.getUUID());
        inMemoryTaskManager.deleteEpicTaskByUUID(epicTaskFirst.getUUID());

        System.out.println(
                """


                После удаления одной из задач и эпика:


                """);

        System.out.println(inMemoryTaskManager.getTasks());
        System.out.println(inMemoryTaskManager.getEpicTasks());
        System.out.println(inMemoryTaskManager.getSubTasks());
        System.out.println(inMemoryTaskManager.getHistory());
    }
}
