package test.com.practicum.yandex.tasks;

import com.practicum.yandex.interfaces.TaskManager;
import com.practicum.yandex.services.Managers;
import com.practicum.yandex.tasks.EpicTask;
import com.practicum.yandex.tasks.SubTask;
import com.practicum.yandex.tasks.Task;
import com.practicum.yandex.tasks.statuses.TaskStatus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class TaskTest {

    // Рекомендуемые тесты: проверьте, что экземпляры класса Task равны друг другу, если равен их
    // id;
    // проверьте, что наследники класса Task равны друг другу, если равен их id;
    public static TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }

    @Test
    public void shouldTasksBeEqualsWithEqualUUID() {

        UUID uuid = UUID.randomUUID();

        Task taskFirst =
                taskManager.createTask(
                        "Рефакторинг кода",
                        "Почистить код от всякого мусора",
                        uuid,
                        TaskStatus.NEW);

        Task taskSecond =
                taskManager.createTask(
                        "Убрать комнату",
                        "Убраться перед приездом родителей",
                        uuid,
                        TaskStatus.NEW);

        Assertions.assertEquals(taskFirst, taskSecond);
    }

    @Test
    public void shouldInheritsBeEqualsWithEqualUUID() {
        UUID uuid = UUID.randomUUID();

        EpicTask epicTaskFirst =
                taskManager.createEpicTask(
                        "Pinguin Project", "Написать бэк для сервиса полнотекстового поиска", uuid);

        EpicTask epicTaskELK =
                taskManager.createEpicTask(
                        "Изучить систему индексации и полноготекстового поиска ElasticSearch",
                        "Изучаем ELK-стек",
                        uuid);

        Assertions.assertEquals(epicTaskFirst, epicTaskELK);

        SubTask subTaskFirst =
                taskManager.createSubTask(
                        "Разработать API-обработки запросов",
                        "Пишем несколько методов для обработки JSON",
                        uuid,
                        TaskStatus.NEW,
                        epicTaskFirst.getUUID());

        SubTask subTaskSecond =
                taskManager.createSubTask(
                        "Протестировать сервис",
                        "Навесить юнит-тесты и провести тестировочную нагрузку через Apache JMeter",
                        uuid,
                        TaskStatus.NEW,
                        epicTaskFirst.getUUID());

        Assertions.assertEquals(subTaskFirst, subTaskSecond);

        SubTask subTaskELK =
                taskManager.createSubTask(
                        "Читаем ХАБР",
                        "Почитать статьи про ELK",
                        uuid,
                        TaskStatus.NEW,
                        epicTaskELK.getUUID());

        Assertions.assertEquals(subTaskFirst, subTaskELK);
    }

    @Test
    public void shouldReturnCorrectToString() {
        UUID uuid = UUID.randomUUID();

        Task taskFirst =
                taskManager.createTask(
                        "Рефакторинг кода",
                        "Почистить код от всякого мусора",
                        uuid,
                        TaskStatus.NEW);

        Assertions.assertEquals(
                String.format(
                        "Task{name='Рефакторинг кода', description='Почистить код от всякого мусора', uuid=%s, taskStatus=NEW}",
                        uuid),
                taskFirst.toString());
    }
}
