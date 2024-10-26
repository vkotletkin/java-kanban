package com.practicum.yandex;

import com.practicum.yandex.interfaces.TaskManager;
import com.practicum.yandex.services.Managers;
import com.practicum.yandex.tasks.EpicTask;
import com.practicum.yandex.tasks.SubTask;
import com.practicum.yandex.tasks.Task;
import com.practicum.yandex.tasks.statuses.TaskStatus;
import com.practicum.yandex.utils.TasksDescription;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        Task taskFirst =
                taskManager.createTask(
                        TasksDescription.taskRefactoringCode.getName(),
                        TasksDescription.taskRefactoringCode.getName(),
                        UUID.randomUUID(),
                        TaskStatus.NEW);

        Task taskSecond =
                taskManager.createTask(
                        TasksDescription.taskRoomClearing.getName(),
                        TasksDescription.taskRoomClearing.getDescription(),
                        UUID.randomUUID(),
                        TaskStatus.NEW);

        EpicTask epicTaskFirst =
                taskManager.createEpicTask(
                        TasksDescription.epicPinguinProject.getName(),
                        TasksDescription.epicPinguinProject.getDescription(),
                        UUID.randomUUID());

        SubTask subTaskFirst =
                taskManager.createSubTask(
                        TasksDescription.subTaskRequestsAPI.getName(),
                        TasksDescription.subTaskRequestsAPI.getDescription(),
                        UUID.randomUUID(),
                        TaskStatus.NEW,
                        epicTaskFirst.getUUID());

        SubTask subTaskSecond =
                taskManager.createSubTask(
                        TasksDescription.subTaskServiceTesting.getName(),
                        TasksDescription.subTaskServiceTesting.getDescription(),
                        UUID.randomUUID(),
                        TaskStatus.NEW,
                        epicTaskFirst.getUUID());

        SubTask subTaskThird =
                taskManager.createSubTask(
                        TasksDescription.subTaskDocumentationWriting.getName(),
                        TasksDescription.subTaskDocumentationWriting.getDescription(),
                        UUID.randomUUID(),
                        TaskStatus.NEW,
                        epicTaskFirst.getUUID());

        EpicTask epicTaskElasticKibana =
                taskManager.createEpicTask(
                        TasksDescription.epicELKStack.getName(),
                        TasksDescription.epicELKStack.getDescription(),
                        UUID.randomUUID());

        taskManager.addNewTask(taskFirst);
        taskManager.addNewTask(taskSecond);
        taskManager.addNewEpicTask(epicTaskFirst);
        taskManager.addNewEpicTask(epicTaskElasticKibana);
        taskManager.addNewSubTask(subTaskFirst);
        taskManager.addNewSubTask(subTaskSecond);
        taskManager.addNewSubTask(subTaskThird);

        taskManager.getTaskByUUID(taskFirst.getUUID());
        taskManager.getTaskByUUID(taskFirst.getUUID());
        taskManager.getTaskByUUID(taskFirst.getUUID());
        taskManager.getTaskByUUID(taskSecond.getUUID());
        taskManager.getTaskByUUID(taskFirst.getUUID());
        taskManager.getTaskByUUID(taskFirst.getUUID());
        taskManager.getTaskByUUID(taskFirst.getUUID());
        taskManager.getTaskByUUID(taskSecond.getUUID());
        taskManager.getEpicTaskByUUID(epicTaskFirst.getUUID());
        taskManager.getEpicTaskByUUID(epicTaskElasticKibana.getUUID());
        taskManager.getSubTaskByUUID(subTaskFirst.getUUID());
        taskManager.getSubTaskByUUID(subTaskThird.getUUID());
        taskManager.getSubTaskByUUID(subTaskFirst.getUUID());
        taskManager.getSubTaskByUUID(subTaskSecond.getUUID());
        taskManager.getEpicTaskByUUID(epicTaskFirst.getUUID());
        taskManager.getEpicTaskByUUID(epicTaskElasticKibana.getUUID());
        taskManager.getTaskByUUID(taskFirst.getUUID());

        System.out.println("--- After get tasks ---");
        System.out.println(taskManager.getHistory());
        System.out.println();

        System.out.println("--- After delete taskFirst (was been lasted in previous stdout");
        taskManager.deleteTaskByUUID(taskFirst.getUUID());
        System.out.println(taskManager.getHistory());
        System.out.println();

        System.out.println("--- After delete epicTask with 3 subTask");
        taskManager.deleteEpicTaskByUUID(epicTaskFirst.getUUID());
        System.out.println(taskManager.getHistory());
        System.out.println();
    }
}
