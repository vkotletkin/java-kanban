import java.util.UUID;

public class Main {

    public static void main(String[] args) {

        // Тестирование по ТЗ

        Manager manager = new Manager();

        // Классы созданы именно так, а не переданы сразу в manager, так как нужно сохранить UUID
        // для дальнейших действий

        Task taskFirst =
                new Task(
                        "Рефакторинг кода",
                        "Почистить код от всякого мусора",
                        UUID.randomUUID(),
                        TaskStatus.NEW);

        Task taskSecond =
                new Task(
                        "Убрать комнату",
                        "Убраться перед приездом родителей",
                        UUID.randomUUID(),
                        TaskStatus.NEW);

        EpicTask epicTaskFirst =
                new EpicTask(
                        "Pinguin Project",
                        "Написать бэк для сервиса полнотекстового поиска",
                        UUID.randomUUID());

        SubTask subTaskFirst =
                new SubTask(
                        "Разработать API-обработки запросов",
                        "Пишем несколько методов для обработки JSON",
                        UUID.randomUUID(),
                        TaskStatus.NEW,
                        epicTaskFirst.getUUID());

        SubTask subTaskSecond =
                new SubTask(
                        "Протестировать сервис",
                        "Навесить юнит-тесты и провести тестировочную нагрузку через Apache JMeter",
                        UUID.randomUUID(),
                        TaskStatus.NEW,
                        epicTaskFirst.getUUID());

        EpicTask epicTaskELK =
                new EpicTask(
                        "Изучить систему индексации и полноготекстового поиска ElasticSearch",
                        "Изучаем ELK-стек",
                        UUID.randomUUID());

        SubTask subTaskELK =
                new SubTask(
                        "Читаем ХАБР",
                        "Почитать статьи про ELK",
                        UUID.randomUUID(),
                        TaskStatus.NEW,
                        epicTaskELK.getUUID());

        // Создайте две задачи, а также эпик с двумя подзадачами и эпик с одной подзадачей.

        manager.createNewTask(taskFirst);
        manager.createNewTask(taskSecond);

        manager.createNewEpicTask(epicTaskFirst);

        manager.createNewSubTask(subTaskFirst);
        manager.createNewSubTask(subTaskSecond);

        manager.createNewEpicTask(epicTaskELK);

        manager.createNewSubTask(subTaskELK);

        // Распечатайте списки эпиков, задач и подзадач через System.out.println()
        System.out.println(manager.getTasks());
        System.out.println(manager.getEpicTasks());
        System.out.println(manager.getSubTasks());

        // Измените статусы созданных объектов, распечатайте их. Проверьте, что статус задачи и
        // подзадачи сохранился, а статус эпика рассчитался по статусам подзадач.

        taskFirst =
                new Task(
                        "Рефакторинг кода",
                        "Почистить код от всякого мусора",
                        taskFirst.getUUID(),
                        TaskStatus.DONE);

        taskSecond =
                new Task(
                        "Убрать комнату",
                        "Убраться перед приездом родителей",
                        taskSecond.getUUID(),
                        TaskStatus.IN_PROGRESS);

        subTaskFirst =
                new SubTask(
                        "Разработать API-обработки запросов",
                        "Пишем несколько методов для обработки JSON",
                        subTaskFirst.getUUID(),
                        TaskStatus.DONE,
                        epicTaskFirst.getUUID());

        subTaskSecond =
                new SubTask(
                        "Протестировать сервис",
                        "Навесить юнит-тесты и провести тестировочную нагрузку через Apache JMeter",
                        subTaskSecond.getUUID(),
                        TaskStatus.IN_PROGRESS,
                        epicTaskFirst.getUUID());

        subTaskELK =
                new SubTask(
                        "Читаем ХАБР",
                        "Почитать статьи про ELK",
                        subTaskELK.getUUID(),
                        TaskStatus.DONE,
                        epicTaskELK.getUUID());

        manager.updateTask(taskFirst);
        manager.updateTask(taskSecond);
        manager.updateSubTask(subTaskFirst);
        manager.updateSubTask(subTaskSecond);
        manager.updateSubTask(subTaskELK);

        System.out.println(
                """


                После изменения статусов объектов:


                """);

        System.out.println(manager.getTasks());
        System.out.println(manager.getEpicTasks());
        System.out.println(manager.getSubTasks());

        // И, наконец, попробуйте удалить одну из задач и один из эпиков.

        manager.delTaskByUUID(taskFirst.getUUID());
        manager.delEpicTaskByUUID(epicTaskFirst.getUUID());

        System.out.println(
                """


                После удаления одной из задач и эпика:


                """);

        System.out.println(manager.getTasks());
        System.out.println(manager.getEpicTasks());
        System.out.println(manager.getSubTasks());
    }
}
