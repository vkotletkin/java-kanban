package test.com.practicum.yandex.tasks;

import com.practicum.yandex.interfaces.TaskManager;
import com.practicum.yandex.services.Managers;
import com.practicum.yandex.tasks.EpicTask;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class EpicTaskTest {

    public static TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }

    @Test
    public void shouldReturnCorrectToString() {
        UUID uuid = UUID.randomUUID();

        EpicTask epicTask =
                taskManager.createEpicTask(
                        "Pinguin Project", "Написать бэк для сервиса полнотекстового поиска", uuid);

        Assertions.assertEquals(
                String.format(
                        "EpicTask{name='Pinguin Project', "
                                + "description='Написать бэк для сервиса полнотекстового поиска',"
                                + " uuid=%s, taskStatus=NEW}",
                        uuid),
                epicTask.toString());
    }
}
