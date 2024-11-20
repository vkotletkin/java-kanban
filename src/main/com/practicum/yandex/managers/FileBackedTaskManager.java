package com.practicum.yandex.managers;

import static com.practicum.yandex.utils.DataFormats.DATE_FORMAT;

import com.practicum.yandex.exceptions.ManagerSaveException;
import com.practicum.yandex.interfaces.TaskManager;
import com.practicum.yandex.services.Managers;
import com.practicum.yandex.tasks.EpicTask;
import com.practicum.yandex.tasks.SubTask;
import com.practicum.yandex.tasks.Task;
import com.practicum.yandex.tasks.statuses.TaskStatus;
import com.practicum.yandex.tasks.types.Tasks;
import com.practicum.yandex.utils.TasksDescription;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private static final String DEFAULT_PATH_TO_FILE_MANAGER_STATE = "manager-state-file.csv";

    private final String pathToFileSave;

    public FileBackedTaskManager(String pathToFile) {
        this.pathToFileSave = pathToFile;
    }

    public FileBackedTaskManager() {
        pathToFileSave = DEFAULT_PATH_TO_FILE_MANAGER_STATE;
    }

    public static TaskManager loadFromFile(String pathToFile) {
        TaskManager fileBackedTaskManager = Managers.getFileBackedTaskManager();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathToFile))) {

            bufferedReader.readLine();

            while (bufferedReader.ready()) {
                String csvLine = bufferedReader.readLine();
                String[] csvLineData = csvLine.split(",");

                // 1 - task type index
                if (Tasks.valueOf(csvLineData[1]) == Tasks.TASK) {
                    fileBackedTaskManager.addNewTask(
                            new Task(
                                    csvLineData[2],
                                    csvLineData[4],
                                    UUID.fromString(csvLineData[0]),
                                    TaskStatus.valueOf(csvLineData[3]),
                                    LocalDateTime.parse(csvLineData[6], DATE_FORMAT),
                                    Duration.parse(csvLineData[8])));
                } else if (Tasks.valueOf(csvLineData[1]) == Tasks.SUBTASK) {
                    fileBackedTaskManager.addNewSubTask(
                            new SubTask(
                                    csvLineData[2],
                                    csvLineData[4],
                                    UUID.fromString(csvLineData[0]),
                                    TaskStatus.valueOf(csvLineData[3]),
                                    UUID.fromString(csvLineData[5]),
                                    LocalDateTime.parse(csvLineData[6], DATE_FORMAT),
                                    Duration.parse(csvLineData[8])));
                } else if (Tasks.valueOf(csvLineData[1]) == Tasks.EPICTASK) {
                    fileBackedTaskManager.addNewEpicTask(
                            new EpicTask(
                                    csvLineData[2],
                                    csvLineData[4],
                                    UUID.fromString(csvLineData[0]),
                                    TaskStatus.valueOf(csvLineData[3]),
                                    LocalDateTime.parse(csvLineData[6], DATE_FORMAT),
                                    LocalDateTime.parse(csvLineData[7], DATE_FORMAT),
                                    Duration.parse(csvLineData[8])));
                }
            }
            return fileBackedTaskManager;
        } catch (IOException e) {
            throw new ManagerSaveException("Файл по указанному пути не существует!");
        }
    }

    private void save() {
        try (FileWriter fileWriter = new FileWriter(pathToFileSave)) {
            String csvHeader =
                    String.format(
                            "id,type,name,status,description,epic,start_time,end_time,duration%n");
            fileWriter.write(csvHeader);

            for (Task task : super.getTasks()) {
                fileWriter.write(convertTaskToString(task));
            }

            for (EpicTask epictask : super.getEpicTasks()) {
                fileWriter.write(convertEpicTaskToString(epictask));
            }

            for (SubTask subtask : super.getSubTasks()) {
                fileWriter.write(convertSubTaskToString(subtask));
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении данных в файл!");
        }
    }

    private String convertTaskToString(Task task) {
        return String.format(
                "%s,%s,%s,%s,%s,%s,%s,%s,%s%n",
                task.getUUID(),
                Tasks.TASK,
                task.getName(),
                task.getTaskStatus(),
                task.getDescription(),
                "",
                task.getStartTime().format(DATE_FORMAT),
                task.getEndTime().format(DATE_FORMAT),
                task.getDuration());
    }

    private String convertSubTaskToString(SubTask task) {
        return String.format(
                "%s,%s,%s,%s,%s,%s,%s,%s,%s%n",
                task.getUUID(),
                Tasks.SUBTASK,
                task.getName(),
                task.getTaskStatus(),
                task.getDescription(),
                task.getEpicTaskUUID(),
                task.getStartTime().format(DATE_FORMAT),
                task.getEndTime().format(DATE_FORMAT),
                task.getDuration());
    }

    private String convertEpicTaskToString(EpicTask task) {
        return String.format(
                "%s,%s,%s,%s,%s,%s,%s,%s,%s%n",
                task.getUUID(),
                Tasks.EPICTASK,
                task.getName(),
                task.getTaskStatus(),
                task.getDescription(),
                "",
                task.getStartTime().format(DATE_FORMAT),
                task.getEndTime().format(DATE_FORMAT),
                task.getDuration());
    }

    @Override
    public void addNewTask(Task task) {
        super.addNewTask(task);
        save();
    }

    @Override
    public void addNewSubTask(SubTask subTask) {
        super.addNewSubTask(subTask);
        save();
    }

    @Override
    public void addNewEpicTask(EpicTask epicTask) {
        super.addNewEpicTask(epicTask);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public void updateEpicTask(EpicTask epicTask) {
        super.updateEpicTask(epicTask);
        save();
    }

    @Override
    public void deleteTaskByUUID(UUID uuid) {
        super.deleteTaskByUUID(uuid);
        save();
    }

    @Override
    public void deleteSubTaskByUUID(UUID uuid) {
        super.deleteSubTaskByUUID(uuid);
        save();
    }

    @Override
    public void deleteEpicTaskByUUID(UUID uuid) {
        super.deleteEpicTaskByUUID(uuid);
        save();
    }

    public static void main(String[] args) {

        String pathToTestFile = "manager-state-test.csv";

        TaskManager fileBackedTaskManagerFirst = Managers.getFileBackedTaskManager(pathToTestFile);

        Task taskFirst =
                fileBackedTaskManagerFirst.createTask(
                        TasksDescription.taskRefactoringCode.name(),
                        TasksDescription.taskRefactoringCode.name(),
                        UUID.randomUUID(),
                        TaskStatus.NEW);

        Task taskSecond =
                fileBackedTaskManagerFirst.createTask(
                        TasksDescription.taskRoomClearing.name(),
                        TasksDescription.taskRoomClearing.description(),
                        UUID.randomUUID(),
                        TaskStatus.NEW);

        EpicTask epicTaskFirst =
                fileBackedTaskManagerFirst.createEpicTask(
                        TasksDescription.epicPinguinProject.name(),
                        TasksDescription.epicPinguinProject.description(),
                        UUID.randomUUID());

        SubTask subTaskFirst =
                fileBackedTaskManagerFirst.createSubTask(
                        TasksDescription.subTaskRequestsAPI.name(),
                        TasksDescription.subTaskRequestsAPI.description(),
                        UUID.randomUUID(),
                        TaskStatus.NEW,
                        epicTaskFirst.getUUID());

        SubTask subTaskSecond =
                fileBackedTaskManagerFirst.createSubTask(
                        TasksDescription.subTaskServiceTesting.name(),
                        TasksDescription.subTaskServiceTesting.description(),
                        UUID.randomUUID(),
                        TaskStatus.NEW,
                        epicTaskFirst.getUUID());

        SubTask subTaskThird =
                fileBackedTaskManagerFirst.createSubTask(
                        TasksDescription.subTaskDocumentationWriting.name(),
                        TasksDescription.subTaskDocumentationWriting.description(),
                        UUID.randomUUID(),
                        TaskStatus.NEW,
                        epicTaskFirst.getUUID());

        EpicTask epicTaskElasticKibana =
                fileBackedTaskManagerFirst.createEpicTask(
                        TasksDescription.epicELKStack.name(),
                        TasksDescription.epicELKStack.description(),
                        UUID.randomUUID());

        fileBackedTaskManagerFirst.addNewTask(taskFirst);
        fileBackedTaskManagerFirst.addNewTask(taskSecond);
        fileBackedTaskManagerFirst.addNewEpicTask(epicTaskFirst);
        fileBackedTaskManagerFirst.addNewEpicTask(epicTaskElasticKibana);
        fileBackedTaskManagerFirst.addNewSubTask(subTaskFirst);
        fileBackedTaskManagerFirst.addNewSubTask(subTaskSecond);
        fileBackedTaskManagerFirst.addNewSubTask(subTaskThird);

        TaskManager fileBackedTaskManagerSecond =
                FileBackedTaskManager.loadFromFile(pathToTestFile);

        System.out.println("Сравнение Tasks:");
        System.out.println("Изначальный менеджер:");
        System.out.println(fileBackedTaskManagerFirst.getTasks());
        System.out.println("Менеджер с данными, загруженными из CSV-файла:");
        System.out.println(fileBackedTaskManagerSecond.getTasks());
        System.out.println();

        System.out.println("Сравнение SubTasks:");
        System.out.println("Изначальный менеджер:");
        System.out.println(fileBackedTaskManagerFirst.getSubTasks());
        System.out.println("Менеджер с данными, загруженными из CSV-файла:");
        System.out.println(fileBackedTaskManagerSecond.getSubTasks());
        System.out.println();

        System.out.println("Сравнение EpicTasks:");
        System.out.println("Изначальный менеджер:");
        System.out.println(fileBackedTaskManagerFirst.getEpicTasks());
        System.out.println("Менеджер с данными, загруженными из CSV-файла:");
        System.out.println(fileBackedTaskManagerSecond.getEpicTasks());
        System.out.println();
    }
}
