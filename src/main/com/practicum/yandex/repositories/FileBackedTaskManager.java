package com.practicum.yandex.repositories;

import com.practicum.yandex.exceptions.ManagerSaveException;
import com.practicum.yandex.tasks.EpicTask;
import com.practicum.yandex.tasks.SubTask;
import com.practicum.yandex.tasks.Task;
import com.practicum.yandex.tasks.statuses.TaskStatus;
import com.practicum.yandex.tasks.types.Tasks;

import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private static final String DEFAULT_PATH_TO_FILE_MANAGER_STATE = "manager-state-file.csv";

    private final String pathToFile;
    private static final boolean IS_DEBUG = true;

    public FileBackedTaskManager(String pathToFile) {
        this.pathToFile = String.format("%s-%s", UUID.randomUUID(), pathToFile);
    }

    public FileBackedTaskManager() {
        pathToFile = DEFAULT_PATH_TO_FILE_MANAGER_STATE;
    }

    public static void loadFromFile(String pathToFile) {}

    // TODO merge methods!!!!!

    private Task loadTaskFromString(String line) {
        String[] data = line.split(",");

        // Indexes of CSV-line elements for correct task creation.
        // name - 2, description - 4, id - 0, status - 3
        return new Task(data[2], data[4], UUID.fromString(data[0]), TaskStatus.valueOf(data[3]));
    }

    private Task loadSubTaskFromString(String line) {
        String[] data = line.split(",");

        // Indexes of CSV-line elements for correct task creation.
        // name - 2, description - 4, id - 0, status - 3
        return new SubTask(
                data[2],
                data[4],
                UUID.fromString(data[0]),
                TaskStatus.valueOf(data[3]),
                UUID.fromString(data[5]));
    }

    private Task loadEpicTaskFromString(String line) {
        String[] data = line.split(",");

        // Indexes of CSV-line elements for correct task creation.
        // name - 2, description - 4, id - 0, status - 3
        return new EpicTask(
                data[2], data[4], UUID.fromString(data[0]), TaskStatus.valueOf(data[3]));
    }

    private void save() {
        try (FileWriter fileWriter = new FileWriter(pathToFile)) {
            String csvHeader = String.format("id,type,name,status,description,epic%n");
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

        } catch (IOException exp) {
            if (IS_DEBUG) {
                exp.printStackTrace();
            }
            throw new ManagerSaveException("Ошибка при сохранении данных в файл!");
        }
    }

    private String convertTaskToString(Task task) {
        return String.format(
                "%s,%s,%s,%s,%s,%s%n",
                task.getUUID(),
                Tasks.TASK,
                task.getName(),
                task.getTaskStatus(),
                task.getDescription(),
                "");
    }

    private String convertSubTaskToString(SubTask task) {
        return String.format(
                "%s,%s,%s,%s,%s,%s%n",
                task.getUUID(),
                Tasks.SUBTASK,
                task.getName(),
                task.getTaskStatus(),
                task.getDescription(),
                task.getEpicTaskUUID());
    }

    private String convertEpicTaskToString(EpicTask task) {
        return String.format(
                "%s,%s,%s,%s,%s,%s%n",
                task.getUUID(),
                Tasks.EPICTASK,
                task.getName(),
                task.getTaskStatus(),
                task.getDescription(),
                "");
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
}
