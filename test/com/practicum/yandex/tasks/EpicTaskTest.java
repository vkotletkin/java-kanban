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
    public void beforeAll() {
        taskManager = Managers.getDefault();
    }

    /*

    Рекомендуемый тест: проверьте, что объект Epic нельзя добавить в самого себя в виде подзадачи;

     Реализовать невозможно, так как не позволяется его передавать в виде подзадачи, при создании
     SubTask он автоматически привязывается к своему EpicTask по UUID EpicTask.

        @Test
        public void notShouldEpicTaskBeAddedToItself() {
        }

     */

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
