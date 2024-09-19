import java.util.UUID;

public class Main {

    public static void main(String[] args) {

        /* Никита, я оставил тут инстансы task1, task и т.д, чтобы была возможность передать UUID в дальнейшем,
        ибо, если я передам через new в методы Manager, отдебажить другие функции я не смогу */

        Manager manager = new Manager();

        Task task1 =
                new Task(
                        "Рефакторинг кода",
                        "Почистить код от всякого мусора",
                        UUID.randomUUID(),
                        TaskStatus.IN_PROGRESS);

        Task task2 =
                new Task(
                        "Убрать комнату",
                        "Убраться перед приездом родителей",
                        UUID.randomUUID(),
                        TaskStatus.NEW);

        manager.createNewTask(task1);
        manager.createNewTask(task2);

        EpicTask epicTask1 =
                new EpicTask(
                        "Pinguin Project",
                        "Написать бэк для сервиса полнотекстового поиска",
                        UUID.randomUUID());

        manager.createNewEpicTask(epicTask1);

        SubTask subtask1 =
                new SubTask(
                        "Разработать API-обработки запросов",
                        "Пишем несколько методов для обработки JSON",
                        UUID.randomUUID(),
                        TaskStatus.NEW,
                        epicTask1.getUUID());

        SubTask subtask2 =
                new SubTask(
                        "Протестировать сервис",
                        "Навесить юнит-тесты и провести тестировочную нагрузку через Apache JMeter",
                        UUID.randomUUID(),
                        TaskStatus.IN_PROGRESS,
                        epicTask1.getUUID());

        manager.createNewSubTask(subtask1, epicTask1.getUUID());
        manager.createNewSubTask(subtask2, epicTask1.getUUID());

        EpicTask epicTaskELK =
                new EpicTask(
                        "Изучить систему индексации и полноготекстового поиска ElasticSearch",
                        "Изучаем ELK-стек",
                        UUID.randomUUID());

        manager.createNewEpicTask(epicTaskELK);

        SubTask subtaskELK =
                new SubTask(
                        "Читаем ХАБР",
                        "Почитать статьи про ELK",
                        UUID.randomUUID(),
                        TaskStatus.NEW,
                        epicTaskELK.getUUID());

        manager.createNewSubTask(subtaskELK, epicTaskELK.getUUID());

       // System.out.println(manager.getTasks());
        System.out.println(manager.getEpicTasks());
        System.out.println(manager.getSubtasks());
        System.out.println(manager.getEpicTasks());

        //        task1 =
        //                new Task(
        //                        "Рефакторинг кода",
        //                        "Почистить код от всякого мусора",
        //                        task1.getUUID(),
        //                        TaskStatus.DONE);
        //
        //        task2 =
        //                new Task(
        //                        "Убрать комнату",
        //                        "Убраться перед приездом родителей",
        //                        task2.getUUID(),
        //                        TaskStatus.IN_PROGRESS);
        //
        //        manager.createNewTask(task1);
        //        manager.createNewTask(task2);
        //        System.out.println(manager.getTasks());
        //
        //        manager.delTaskByUUID(task1.getUUID());
        //        System.out.println(manager.getTasks());

    }
}
