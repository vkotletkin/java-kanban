import com.yandex.practicum.services.Manager;
import com.yandex.practicum.tasks.EpicTask;
import com.yandex.practicum.tasks.SubTask;
import com.yandex.practicum.tasks.Task;
import com.yandex.practicum.tasks.statuses.TaskStatus;

import java.util.UUID;

public class Main {

    public static void main(String[] args) {

        // Тестирование по Т

        Manager manager = new Manager();

        // Классы созданы именно так, а не переданы сразу в manager, так как нужно сохранить UUID
        // для дальнейших действий

        Task taskFirst =
                manager.createTask(
                        "Рефакторинг кода",
                        "Почистить код от всякого мусора",
                        UUID.randomUUID(),
                        TaskStatus.NEW);

        Task taskSecond =
                manager.createTask(
                        "Убрать комнату",
                        "Убраться перед приездом родителей",
                        UUID.randomUUID(),
                        TaskStatus.NEW);

        EpicTask epicTaskFirst =
                manager.createEpicTask(
                        "Pinguin Project",
                        "Написать бэк для сервиса полнотекстового поиска",
                        UUID.randomUUID());

        SubTask subTaskFirst =
                manager.createSubTask(
                        "Разработать API-обработки запросов",
                        "Пишем несколько методов для обработки JSON",
                        UUID.randomUUID(),
                        TaskStatus.NEW,
                        epicTaskFirst.getUUID());

        SubTask subTaskSecond =
                manager.createSubTask(
                        "Протестировать сервис",
                        "Навесить юнит-тесты и провести тестировочную нагрузку через Apache JMeter",
                        UUID.randomUUID(),
                        TaskStatus.NEW,
                        epicTaskFirst.getUUID());

        EpicTask epicTaskELK =
                manager.createEpicTask(
                        "Изучить систему индексации и полноготекстового поиска ElasticSearch",
                        "Изучаем ELK-стек",
                        UUID.randomUUID());

        SubTask subTaskELK =
                manager.createSubTask(
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

        manager.updateTask(
                manager.createTask(
                        "Рефакторинг кода",
                        "Почистить код от всякого мусора",
                        taskFirst.getUUID(),
                        TaskStatus.DONE));

        manager.updateTask(
                manager.createTask(
                        "Убрать комнату",
                        "Убраться перед приездом родителей",
                        taskSecond.getUUID(),
                        TaskStatus.IN_PROGRESS));

        manager.updateSubTask(
                manager.createSubTask(
                        "Разработать API-обработки запросов",
                        "Пишем несколько методов для обработки JSON",
                        subTaskFirst.getUUID(),
                        TaskStatus.DONE,
                        epicTaskFirst.getUUID()));

        manager.updateSubTask(
                manager.createSubTask(
                        "Протестировать сервис",
                        "Навесить юнит-тесты и провести тестировочную нагрузку через Apache JMeter",
                        subTaskSecond.getUUID(),
                        TaskStatus.IN_PROGRESS,
                        epicTaskFirst.getUUID()));

        manager.updateSubTask(
                manager.createSubTask(
                        "Читаем ХАБР",
                        "Почитать статьи про ELK",
                        subTaskELK.getUUID(),
                        TaskStatus.DONE,
                        epicTaskELK.getUUID()));

        System.out.println(
                """


                После изменения статусов объектов:

                """);

        System.out.println(manager.getTasks());
        System.out.println(manager.getEpicTasks());
        System.out.println(manager.getSubTasks());

        // И, наконец, попробуйте удалить одну из задач и один из эпиков.

        manager.deleteTaskByUUID(taskFirst.getUUID());
        manager.deleteEpicTaskByUUID(epicTaskFirst.getUUID());

        System.out.println(
                """


                После удаления одной из задач и эпика:


                """);

        System.out.println(manager.getTasks());
        System.out.println(manager.getEpicTasks());
        System.out.println(manager.getSubTasks());
    }
}
