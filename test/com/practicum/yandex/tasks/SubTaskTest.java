package test.com.practicum.yandex.tasks;

import com.practicum.yandex.interfaces.TaskManager;
import com.practicum.yandex.services.Managers;
import com.practicum.yandex.tasks.EpicTask;
import com.practicum.yandex.tasks.SubTask;
import com.practicum.yandex.tasks.statuses.TaskStatus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class SubTaskTest {

    public static TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }

    // Рекомендуемый тест:
    // проверьте, что объект Subtask нельзя сделать своим же эпиком;
    // Эпик привязывается к объекту SubTask исключительно идентификатору, реализовать данный тест не
    // представляется возможным

    //    @Test
    //    public void notShouldEpicTaskBeAddedToItself() {}

    @Test
    public void shouldReturnCorrectToString() {
        UUID uuid = UUID.randomUUID();

        EpicTask epicTask =
                taskManager.createEpicTask(
                        "Pinguin Project", "Написать бэк для сервиса полнотекстового поиска", uuid);

        SubTask subTask =
                taskManager.createSubTask(
                        "Разработать API-обработки запросов",
                        "Пишем несколько методов для обработки JSON",
                        uuid,
                        TaskStatus.NEW,
                        epicTask.getUUID());

        Assertions.assertEquals(
                String.format(
                        "SubTask{name='Разработать API-обработки запросов', "
                                + "description='Пишем несколько методов для обработки JSON',"
                                + " uuid=%s, taskStatus=NEW, epicTaskUUID=%s}",
                        uuid, uuid),
                subTask.toString());
    }

    @Test
    public void shouldReturnEqualSubTask() {

    }
}
