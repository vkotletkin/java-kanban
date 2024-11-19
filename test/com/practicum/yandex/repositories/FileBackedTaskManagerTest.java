package test.com.practicum.yandex.repositories;

import static test.com.practicum.yandex.utils.FileBackedDescriptionForTests.fileForManagerData;

import com.practicum.yandex.exceptions.ManagerSaveException;
import com.practicum.yandex.interfaces.TaskManager;
import com.practicum.yandex.repositories.FileBackedTaskManager;
import com.practicum.yandex.services.Managers;
import com.practicum.yandex.tasks.Task;
import com.practicum.yandex.tasks.statuses.TaskStatus;
import com.practicum.yandex.utils.TasksDescription;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

public class FileBackedTaskManagerTest {

    public static TaskManager taskManager;
    public static File file;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getFileBackedTaskManager();
        try {
            file =
                    File.createTempFile(
                            fileForManagerData.getName(), fileForManagerData.getFormat());
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при работе с файлом.");
        }
    }

    @Test
    public void shouldBeLoadEmptyFile() {
        TaskManager taskManager = FileBackedTaskManager.loadFromFile(file.getPath());
    }

    @Test
    public void shouldBeSaveEmptyFile() {
        TaskManager taskManager = Managers.getFileBackedTaskManager();
        Task taskFirst =
                taskManager.createTask(
                        TasksDescription.taskRefactoringCode.name(),
                        TasksDescription.taskRefactoringCode.name(),
                        UUID.randomUUID(),
                        TaskStatus.NEW);

        taskManager.addNewTask(taskFirst);
        taskManager.deleteTaskByUUID(taskFirst.getUUID());

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            int lineCounter = 0;
            while (bufferedReader.ready()) {
                bufferedReader.readLine();
                lineCounter += 1;
            }
            Assertions.assertEquals(0, lineCounter);
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при работе с файлом.");
        }
    }

    @Test
    public void shouldSaveFewTasks() {
        TaskManager taskManager = Managers.getFileBackedTaskManager();

        Task taskFirst =
                taskManager.createTask(
                        TasksDescription.taskRefactoringCode.name(),
                        TasksDescription.taskRefactoringCode.name(),
                        UUID.randomUUID(),
                        TaskStatus.NEW,
                        TasksDescription.taskRefactoringCode.localDateTime(),
                        TasksDescription.taskRefactoringCode.duration());

        Task taskSecond =
                taskManager.createTask(
                        TasksDescription.taskRoomClearing.name(),
                        TasksDescription.taskRoomClearing.description(),
                        UUID.randomUUID(),
                        TaskStatus.NEW,
                        TasksDescription.taskRoomClearing.localDateTime(),
                        TasksDescription.taskRoomClearing.duration());

        taskManager.addNewTask(taskFirst);
        taskManager.addNewTask(taskSecond);

        Assertions.assertEquals(2, taskManager.getTasks().size());
    }

    @Test
    public void shouldLoadOneTasks() {
        TaskManager taskManager = Managers.getFileBackedTaskManager(file.getPath());

        Task taskFirst =
                taskManager.createTask(
                        TasksDescription.taskRefactoringCode.name(),
                        TasksDescription.taskRefactoringCode.name(),
                        UUID.randomUUID(),
                        TaskStatus.NEW,
                        TasksDescription.taskRefactoringCode.localDateTime(),
                        TasksDescription.taskRefactoringCode.duration());

        Task taskSecond =
                taskManager.createTask(
                        TasksDescription.taskRoomClearing.name(),
                        TasksDescription.taskRoomClearing.description(),
                        UUID.randomUUID(),
                        TaskStatus.NEW,
                        TasksDescription.taskRoomClearing.localDateTime(),
                        TasksDescription.taskRoomClearing.duration());

        taskManager.addNewTask(taskFirst);
        taskManager.addNewTask(taskSecond);

        TaskManager taskManagerLoader = FileBackedTaskManager.loadFromFile(file.getPath());

        Assertions.assertEquals(2, taskManagerLoader.getTasks().size());
    }
}
