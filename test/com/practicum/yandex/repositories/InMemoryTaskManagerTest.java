package test.com.practicum.yandex.repositories;

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

public class InMemoryTaskManagerTest {
    public static TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }

    @Test
    public void shouldCreateTasksAndCanSearchHimOnId() {

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

        Assertions.assertEquals(taskManager.getTaskByUUID(taskUUID), task);
        Assertions.assertEquals(taskManager.getSubTaskByUUID(subTaskUUID), subTask);
        Assertions.assertEquals(taskManager.getEpicTaskByUUID(epicTaskUUID), epicTask);
    }

    @Test
    public void shouldNotConflitsDifferentTasksWithDifferentsUUID() {
        UUID uuid = UUID.randomUUID();

        Task taskWithGeneratedUUID =
                taskManager.createTask(
                        TasksDescriptionForTests.taskRefactoringCode.name(),
                        TasksDescriptionForTests.taskRefactoringCode.description(),
                        TaskStatus.NEW);

        Task taskWithoutGeneratedUUID =
                taskManager.createTask(
                        TasksDescriptionForTests.taskRefactoringCode.name(),
                        TasksDescriptionForTests.taskRefactoringCode.description(),
                        uuid,
                        TaskStatus.NEW);

        Assertions.assertNotEquals(taskWithGeneratedUUID, taskWithoutGeneratedUUID);
    }

    @Test
    public void shouldNotChangesObjectFieldsAfterAddToManager() {
        UUID uuid = UUID.randomUUID();

        Task task =
                taskManager.createTask(
                        TasksDescriptionForTests.taskRefactoringCode.name(),
                        TasksDescriptionForTests.taskRefactoringCode.description(),
                        uuid,
                        TaskStatus.NEW);

        taskManager.addNewTask(task);

        Task getTask = taskManager.getTaskByUUID(uuid);

        Assertions.assertEquals(getTask.getName(), task.getName());
        Assertions.assertEquals(getTask.getDescription(), task.getDescription());
        Assertions.assertEquals(getTask.getUUID(), task.getUUID());
        Assertions.assertEquals(getTask.getTaskStatus(), task.getTaskStatus());
    }

    @Test
    public void shouldBeEmptyListOfEpicTasks() {
        EpicTask epicTaskFirst =
                taskManager.createEpicTask(
                        TasksDescriptionForTests.epicPinguinProject.name(),
                        TasksDescriptionForTests.epicPinguinProject.description());

        EpicTask epicTaskSecond =
                taskManager.createEpicTask(
                        TasksDescriptionForTests.epicDevelopProject.name(),
                        TasksDescriptionForTests.epicDevelopProject.description());

        EpicTask epicTaskThird =
                taskManager.createEpicTask(
                        TasksDescriptionForTests.epicAndromedaProject.name(),
                        TasksDescriptionForTests.epicAndromedaProject.description());

        SubTask subTaskFirst =
                taskManager.createSubTask(
                        TasksDescriptionForTests.subTaskRequestsAPI.name(),
                        TasksDescriptionForTests.subTaskRequestsAPI.description(),
                        UUID.randomUUID(),
                        TaskStatus.NEW,
                        epicTaskFirst.getUUID());

        taskManager.addNewEpicTask(epicTaskFirst);
        taskManager.addNewEpicTask(epicTaskSecond);
        taskManager.addNewEpicTask(epicTaskThird);
        taskManager.addNewSubTask(subTaskFirst);
        taskManager.getEpicTaskByUUID(epicTaskFirst.getUUID());
        taskManager.getEpicTaskByUUID(epicTaskSecond.getUUID());
        taskManager.getSubTaskByUUID(subTaskFirst.getUUID());

        Assertions.assertNotEquals(0, taskManager.getEpicTasks().size());

        taskManager.deleteAllEpicTasks();

        Assertions.assertEquals(0, taskManager.getEpicTasks().size());
    }

    @Test
    public void shouldBeEmptyListOfTasks() {
        Task task =
                taskManager.createTask(
                        TasksDescriptionForTests.taskRefactoringCode.name(),
                        TasksDescriptionForTests.taskRefactoringCode.description(),
                        TaskStatus.NEW);

        taskManager.addNewTask(task);

        Assertions.assertNotEquals(0, taskManager.getTasks().size());

        taskManager.deleteAllTasks();

        Assertions.assertEquals(0, taskManager.getTasks().size());
    }

    @Test
    public void shouldBeEmptyListOfSubTasks() {
        EpicTask epicTask =
                taskManager.createEpicTask(
                        TasksDescriptionForTests.epicPinguinProject.name(),
                        TasksDescriptionForTests.epicPinguinProject.description());

        SubTask subTaskFirst =
                taskManager.createSubTask(
                        TasksDescriptionForTests.subTaskRequestsAPI.name(),
                        TasksDescriptionForTests.subTaskRequestsAPI.description(),
                        TaskStatus.NEW,
                        epicTask.getUUID());

        SubTask subTaskSecond =
                taskManager.createSubTask(
                        TasksDescriptionForTests.subTaskDocumentationWriting.name(),
                        TasksDescriptionForTests.subTaskDocumentationWriting.description(),
                        TaskStatus.NEW,
                        epicTask.getUUID());

        taskManager.addNewEpicTask(epicTask);
        taskManager.addNewSubTask(subTaskFirst);
        taskManager.addNewSubTask(subTaskSecond);

        Assertions.assertNotEquals(2, taskManager.getTasks().size());

        taskManager.deleteAllSubTasks();

        Assertions.assertEquals(0, taskManager.getSubTasks().size());
    }

    @Test
    public void shouldBeReturnNullOnDeletedEpicTask() {
        EpicTask epicTask =
                taskManager.createEpicTask(
                        TasksDescriptionForTests.epicPinguinProject.name(),
                        TasksDescriptionForTests.epicPinguinProject.description());

        SubTask subTaskFirst =
                taskManager.createSubTask(
                        TasksDescriptionForTests.subTaskRequestsAPI.name(),
                        TasksDescriptionForTests.subTaskRequestsAPI.description(),
                        TaskStatus.NEW,
                        epicTask.getUUID());

        taskManager.addNewEpicTask(epicTask);
        taskManager.addNewSubTask(subTaskFirst);

        Assertions.assertEquals(1, taskManager.getEpicTasks().size());

        taskManager.deleteEpicTaskByUUID(epicTask.getUUID());

        Assertions.assertNull(taskManager.getEpicTaskByUUID(epicTask.getUUID()));
    }

    @Test
    public void shouldBeReturnNullOnDeletedSubTask() {
        EpicTask epicTask =
                taskManager.createEpicTask(
                        TasksDescriptionForTests.epicPinguinProject.name(),
                        TasksDescriptionForTests.epicPinguinProject.description());

        SubTask subTaskFirst =
                taskManager.createSubTask(
                        TasksDescriptionForTests.subTaskRequestsAPI.name(),
                        TasksDescriptionForTests.subTaskRequestsAPI.description(),
                        TaskStatus.NEW,
                        epicTask.getUUID());

        taskManager.addNewEpicTask(epicTask);
        taskManager.addNewSubTask(subTaskFirst);

        taskManager.deleteSubTaskByUUID(subTaskFirst.getUUID());

        Assertions.assertNull(taskManager.getSubTaskByUUID(epicTask.getUUID()));
    }

    @Test
    public void shouldBeReturnEpicSubTaskList() {
        EpicTask epicTask =
                taskManager.createEpicTask(
                        TasksDescriptionForTests.epicPinguinProject.name(),
                        TasksDescriptionForTests.epicPinguinProject.description());

        SubTask subTaskFirst =
                taskManager.createSubTask(
                        TasksDescriptionForTests.subTaskRequestsAPI.name(),
                        TasksDescriptionForTests.subTaskRequestsAPI.description(),
                        TaskStatus.NEW,
                        epicTask.getUUID(),
                        TasksDescriptionForTests.subTaskRequestsAPI.localDateTime(),
                        TasksDescriptionForTests.subTaskRequestsAPI.duration());

        SubTask subTaskSecond =
                taskManager.createSubTask(
                        TasksDescriptionForTests.subTaskDocumentationWriting.name(),
                        TasksDescriptionForTests.subTaskDocumentationWriting.description(),
                        TaskStatus.NEW,
                        epicTask.getUUID(),
                        TasksDescriptionForTests.subTaskDocumentationWriting.localDateTime(),
                        TasksDescriptionForTests.subTaskDocumentationWriting.duration());

        taskManager.addNewEpicTask(epicTask);
        taskManager.addNewSubTask(subTaskFirst);
        taskManager.addNewSubTask(subTaskSecond);

        Assertions.assertEquals(2, taskManager.getEpicSubTasks(epicTask.getUUID()).size());
    }
}
