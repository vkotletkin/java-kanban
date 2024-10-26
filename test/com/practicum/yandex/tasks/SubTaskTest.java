package test.com.practicum.yandex.tasks;

import com.practicum.yandex.interfaces.TaskManager;
import com.practicum.yandex.services.Managers;
import com.practicum.yandex.tasks.EpicTask;
import com.practicum.yandex.tasks.SubTask;
import com.practicum.yandex.tasks.statuses.TaskStatus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import test.com.practicum.yandex.utils.TasksDescriptionForTests;

import java.util.UUID;

public class SubTaskTest {

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
                        TasksDescriptionForTests.epicPinguinProject.getName(),
                        TasksDescriptionForTests.epicPinguinProject.getDescription(),
                        uuid);

        SubTask subTask =
                taskManager.createSubTask(
                        TasksDescriptionForTests.subTaskRequestsAPI.getName(),
                        TasksDescriptionForTests.subTaskRequestsAPI.getDescription(),
                        uuid,
                        TaskStatus.NEW,
                        epicTask.getUUID());

        Assertions.assertEquals(
                String.format(
                        "SubTask{name='%s', "
                                + "description='%s',"
                                + " uuid=%s, taskStatus=NEW, epicTaskUUID=%s}",
                        TasksDescriptionForTests.subTaskRequestsAPI.getName(),
                        TasksDescriptionForTests.subTaskRequestsAPI.getDescription(),
                        uuid,
                        uuid),
                subTask.toString());
    }
}
