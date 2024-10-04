package test.com.practicum.yandex.repositories;

import com.practicum.yandex.interfaces.TaskManager;
import com.practicum.yandex.services.Managers;
import com.practicum.yandex.tasks.EpicTask;
import com.practicum.yandex.tasks.SubTask;
import com.practicum.yandex.tasks.Task;
import com.practicum.yandex.tasks.statuses.TaskStatus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class InMemoryHistoryManagerTest {
    public static TaskManager taskManager;

    @BeforeAll
    public static void beforeAll() {
        taskManager = Managers.getDefault();
    }

    // Рекомендуемый тест:
    // проверьте, что InMemoryTaskManager действительно добавляет задачи разного типа и может найти
    // их по id;
    @Test
    public void shouldCreateTasksAndCanSearchHimOnId() {

        UUID epicTaskUUID = UUID.randomUUID();
        UUID subTaskUUID = UUID.randomUUID();
        UUID taskUUID = UUID.randomUUID();

        Task task =
                taskManager.createTask(
                        "Рефакторинг кода",
                        "Почистить код от всякого мусора",
                        taskUUID,
                        TaskStatus.NEW);

        EpicTask epicTask =
                taskManager.createEpicTask(
                        "Pinguin Project",
                        "Написать бэк для сервиса полнотекстового поиска",
                        epicTaskUUID);

        SubTask subTask =
                taskManager.createSubTask(
                        "Разработать API-обработки запросов",
                        "Пишем несколько методов для обработки JSON",
                        subTaskUUID,
                        TaskStatus.NEW,
                        epicTask.getUUID());

        taskManager.addNewTask(task);
        taskManager.addNewEpicTask(epicTask);
        taskManager.addNewSubTask(subTask);

        Assertions.assertEquals(taskManager.getTaskByUUID(taskUUID), task);
        Assertions.assertEquals(taskManager.getSubTaskByUUID(subTaskUUID), subTask);
        Assertions.assertEquals(taskManager.getEpicTaskByUUID(epicTaskUUID), epicTask);
    }

    // Рекомендуемый тест: проверьте, что задачи с заданным id и сгенерированным id не конфликтуют
    // внутри менеджера;
    @Test
    public void shouldNotConflitsDifferentTasksWithDifferentsUUID() {
        UUID uuid = UUID.randomUUID();

        Task taskWithGeneratedUUID =
                taskManager.createTask(
                        "Рефакторинг кода", "Почистить код от всякого мусора", TaskStatus.NEW);

        Task taskWithoutGeneratedUUID =
                taskManager.createTask(
                        "Рефакторинг кода",
                        "Почистить код от всякого мусора",
                        uuid,
                        TaskStatus.NEW);

        Assertions.assertNotEquals(taskWithGeneratedUUID, taskWithoutGeneratedUUID);
    }

    @Test
    public void shouldNotChangesObjectFieldsAfterAddToManager() {
        UUID uuid = UUID.randomUUID();

        Task task =
                taskManager.createTask(
                        "Рефакторинг кода",
                        "Почистить код от всякого мусора",
                        uuid,
                        TaskStatus.NEW);

        taskManager.addNewTask(task);

        Task getTask = taskManager.getTaskByUUID(uuid);

        Assertions.assertEquals(getTask.getName(), task.getName());
        Assertions.assertEquals(getTask.getDescription(), task.getDescription());
        Assertions.assertEquals(getTask.getUUID(), task.getUUID());
        Assertions.assertEquals(getTask.getTaskStatus(), task.getTaskStatus());
    }
}
