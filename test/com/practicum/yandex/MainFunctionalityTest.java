package test.com.practicum.yandex;

import com.practicum.yandex.interfaces.TaskManager;
import com.practicum.yandex.services.Managers;
import com.practicum.yandex.tasks.EpicTask;
import com.practicum.yandex.tasks.SubTask;
import com.practicum.yandex.tasks.Task;
import com.practicum.yandex.tasks.statuses.TaskStatus;
import com.practicum.yandex.utils.TasksDescription;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import test.com.practicum.yandex.utils.TasksDescriptionForTests;

import java.util.UUID;

public class MainFunctionalityTest {
    public static TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }

    @Test
    public void shouldWorksFullEpicFunctionality() {

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

        taskManager.addNewEpicTask(epicTask);
        taskManager.addNewSubTask(subTaskFirst);
        taskManager.addNewSubTask(subTaskSecond);
        taskManager.addNewTask(taskFirst);
        taskManager.addNewTask(taskSecond);

        Assertions.assertEquals(epicTask.getTaskStatus(), TaskStatus.NEW);

        taskManager.updateSubTask(
                new SubTask(
                        TasksDescriptionForTests.subTaskRequestsAPI.name(),
                        TasksDescriptionForTests.subTaskRequestsAPI.description(),
                        subTaskFirst.getUUID(),
                        TaskStatus.DONE,
                        epicTask.getUUID(),
                        TasksDescriptionForTests.subTaskRequestsAPI.localDateTime(),
                        TasksDescriptionForTests.subTaskRequestsAPI.duration()));

        taskManager.updateSubTask(
                new SubTask(
                        TasksDescription.taskRoomClearing.name(),
                        TasksDescription.taskRoomClearing.description(),
                        subTaskSecond.getUUID(),
                        TaskStatus.DONE,
                        epicTask.getUUID(),
                        TasksDescription.taskRoomClearing.localDateTime(),
                        TasksDescription.taskRoomClearing.duration()));

        Assertions.assertEquals(
                taskManager.getEpicTaskByUUID(epicTask.getUUID()).getTaskStatus(), TaskStatus.DONE);

        taskManager.updateSubTask(
                new SubTask(
                        TasksDescriptionForTests.subTaskRequestsAPI.name(),
                        TasksDescriptionForTests.subTaskRequestsAPI.description(),
                        subTaskFirst.getUUID(),
                        TaskStatus.NEW,
                        epicTask.getUUID(),
                        TasksDescriptionForTests.subTaskRequestsAPI.localDateTime(),
                        TasksDescriptionForTests.subTaskRequestsAPI.duration()));

        Assertions.assertEquals(
                taskManager.getEpicTaskByUUID(epicTask.getUUID()).getTaskStatus(),
                TaskStatus.IN_PROGRESS);

        taskManager.updateSubTask(
                new SubTask(
                        TasksDescriptionForTests.subTaskRequestsAPI.name(),
                        TasksDescriptionForTests.subTaskRequestsAPI.description(),
                        subTaskFirst.getUUID(),
                        TaskStatus.IN_PROGRESS,
                        epicTask.getUUID(),
                        TasksDescriptionForTests.subTaskRequestsAPI.localDateTime(),
                        TasksDescriptionForTests.subTaskRequestsAPI.duration()));

        taskManager.updateSubTask(
                new SubTask(
                        TasksDescription.taskRoomClearing.name(),
                        TasksDescription.taskRoomClearing.description(),
                        subTaskSecond.getUUID(),
                        TaskStatus.IN_PROGRESS,
                        epicTask.getUUID(),
                        TasksDescription.taskRoomClearing.localDateTime(),
                        TasksDescription.taskRoomClearing.duration()));

        Assertions.assertEquals(
                taskManager.getEpicTaskByUUID(epicTask.getUUID()).getTaskStatus(),
                TaskStatus.IN_PROGRESS);
    }
}
