package test.com.practicum.yandex.repositories;

import com.practicum.yandex.interfaces.TaskManager;
import com.practicum.yandex.services.Managers;
import com.practicum.yandex.tasks.EpicTask;
import com.practicum.yandex.tasks.SubTask;
import com.practicum.yandex.tasks.Task;
import com.practicum.yandex.tasks.statuses.TaskStatus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class InMemoryHistoryManagerTest {

    // Рекомендуемый тест: убедитесь, что задачи, добавляемые в HistoryManager, сохраняют предыдущую
    // версию задачи и её данных.

    @Test
    public void shouldManagerSaveLastRequestedTasks() {
        TaskManager taskManager = Managers.getDefault();

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

        taskManager.getTaskByUUID(taskUUID);
        taskManager.getSubTaskByUUID(subTaskUUID);
        taskManager.getEpicTaskByUUID(epicTaskUUID);

        task =
                taskManager.createTask(
                        "Меняем объект", "Изменено описание", taskUUID, TaskStatus.NEW);

        taskManager.addNewTask(task);

        taskManager.getTaskByUUID(taskUUID);

        Assertions.assertEquals(
                taskManager.getHistory().get(0).getUUID(),
                taskManager.getHistory().get(3).getUUID());

        Assertions.assertNotEquals(
                taskManager.getHistory().get(0).getDescription(),
                taskManager.getHistory().get(3).getDescription());
    }
}
