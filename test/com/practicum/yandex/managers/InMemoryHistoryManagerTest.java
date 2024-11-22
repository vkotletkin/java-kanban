package test.com.practicum.yandex.managers;

import com.practicum.yandex.interfaces.TaskManager;
import com.practicum.yandex.services.Managers;
import com.practicum.yandex.tasks.EpicTask;
import com.practicum.yandex.tasks.SubTask;
import com.practicum.yandex.tasks.Task;
import com.practicum.yandex.tasks.statuses.TaskStatus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import test.com.practicum.yandex.utils.TasksDescriptionForTests;

import java.util.UUID;

public class InMemoryHistoryManagerTest {

    public static TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }

    @Test
    public void shouldManagerSaveLastRequestedTasks() {
        UUID epicTaskUUID = UUID.randomUUID();
        UUID subTaskUUID = UUID.randomUUID();
        UUID taskUUID = UUID.randomUUID();

        Task task =
                taskManager.createTask(
                        TasksDescriptionForTests.taskRefactoringCode.name(),
                        TasksDescriptionForTests.taskRefactoringCode.description(),
                        taskUUID,
                        TaskStatus.NEW,
                        TasksDescriptionForTests.taskRefactoringCode.localDateTime(),
                        TasksDescriptionForTests.taskRefactoringCode.duration());

        EpicTask epicTask =
                taskManager.createEpicTask(
                        TasksDescriptionForTests.epicPinguinProject.name(),
                        TasksDescriptionForTests.epicPinguinProject.description(),
                        epicTaskUUID);

        SubTask subTask =
                taskManager.createSubTask(
                        TasksDescriptionForTests.subTaskRequestsAPI.name(),
                        TasksDescriptionForTests.subTaskRequestsAPI.description(),
                        subTaskUUID,
                        TaskStatus.NEW,
                        epicTask.getUUID());

        taskManager.addNewTask(task);
        taskManager.addNewEpicTask(epicTask);
        taskManager.addNewSubTask(subTask);

        taskManager.getTaskByUUID(taskUUID);
        Assertions.assertEquals(task, taskManager.getHistory().get(0));

        taskManager.getSubTaskByUUID(subTaskUUID);
        Assertions.assertEquals(subTask, taskManager.getHistory().get(1));

        taskManager.getEpicTaskByUUID(epicTaskUUID);
        Assertions.assertEquals(epicTask, taskManager.getHistory().get(2));

        taskManager.getTaskByUUID(taskUUID);
        Assertions.assertEquals(task, taskManager.getHistory().get(2));
    }

    @Test
    public void checkIfRequestedTasksCountCorrespondsCorrectNumber() {
        UUID epicTaskUUID = UUID.randomUUID();
        UUID subTaskUUID = UUID.randomUUID();
        UUID taskUUID = UUID.randomUUID();

        Task task =
                taskManager.createTask(
                        TasksDescriptionForTests.taskRefactoringCode.name(),
                        TasksDescriptionForTests.taskRefactoringCode.description(),
                        taskUUID,
                        TaskStatus.NEW,
                        TasksDescriptionForTests.taskRefactoringCode.localDateTime(),
                        TasksDescriptionForTests.taskRefactoringCode.duration());

        EpicTask epicTask =
                taskManager.createEpicTask(
                        TasksDescriptionForTests.epicPinguinProject.name(),
                        TasksDescriptionForTests.epicPinguinProject.description(),
                        epicTaskUUID);

        SubTask subTask =
                taskManager.createSubTask(
                        TasksDescriptionForTests.subTaskRequestsAPI.name(),
                        TasksDescriptionForTests.subTaskRequestsAPI.description(),
                        subTaskUUID,
                        TaskStatus.NEW,
                        epicTask.getUUID(),
                        TasksDescriptionForTests.subTaskRequestsAPI.localDateTime(),
                        TasksDescriptionForTests.subTaskRequestsAPI.duration());

        taskManager.addNewTask(task);
        taskManager.addNewEpicTask(epicTask);
        taskManager.addNewSubTask(subTask);

        taskManager.getTaskByUUID(taskUUID);
        taskManager.getTaskByUUID(taskUUID);
        taskManager.getTaskByUUID(taskUUID);
        taskManager.getSubTaskByUUID(subTaskUUID);
        taskManager.getSubTaskByUUID(subTaskUUID);
        taskManager.getSubTaskByUUID(subTaskUUID);
        taskManager.getTaskByUUID(taskUUID);
        taskManager.getTaskByUUID(taskUUID);
        taskManager.getEpicTaskByUUID(epicTaskUUID);
        taskManager.getEpicTaskByUUID(epicTaskUUID);
        taskManager.getEpicTaskByUUID(epicTaskUUID);

        Assertions.assertEquals(3, taskManager.getHistory().size());
    }

    @Test
    public void checkHistoryAddTask() {
        UUID taskUUID = UUID.randomUUID();

        Task task =
                taskManager.createTask(
                        TasksDescriptionForTests.taskRefactoringCode.name(),
                        TasksDescriptionForTests.taskRefactoringCode.description(),
                        taskUUID,
                        TaskStatus.NEW);

        taskManager.addNewTask(task);

        taskManager.getTaskByUUID(taskUUID);

        Assertions.assertEquals(task, taskManager.getTasks().get(0));
    }

    @Test
    public void checkRequestLotOfSameTask() {
        UUID taskUUID = UUID.randomUUID();

        Task task =
                taskManager.createTask(
                        TasksDescriptionForTests.taskRefactoringCode.name(),
                        TasksDescriptionForTests.taskRefactoringCode.description(),
                        taskUUID,
                        TaskStatus.NEW);

        taskManager.addNewTask(task);

        taskManager.getTaskByUUID(taskUUID);
        taskManager.getTaskByUUID(taskUUID);
        taskManager.getTaskByUUID(taskUUID);
        taskManager.getTaskByUUID(taskUUID);
        taskManager.getTaskByUUID(taskUUID);

        Assertions.assertEquals(task, taskManager.getHistory().get(0));
        Assertions.assertEquals(1, taskManager.getHistory().size());
    }
}
