package com.practicum.yandex.repositories;

import com.practicum.yandex.exceptions.ManagerSaveException;
import com.practicum.yandex.tasks.EpicTask;
import com.practicum.yandex.tasks.SubTask;
import com.practicum.yandex.tasks.Task;
import com.practicum.yandex.tasks.types.Tasks;

import java.io.FileWriter;
import java.io.IOException;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private static final String PATH_TO_FILE_MANAGER_STATE = "./data/manager-state-file.csv";
    private static final boolean IS_DEBUG = true;

    private void save() {
        try (FileWriter fileWriter = new FileWriter(PATH_TO_FILE_MANAGER_STATE)) {
            for (Task task : super.getTasks()) {
                fileWriter.write(convertTaskToString(task));
            }

            for (Task task : super.getEpicTasks()) {
                fileWriter.write(convertTaskToString(task));
            }

            for (Task task : super.getSubTasks()) {
                fileWriter.write(convertTaskToString(task));
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
                "%s,%s,%s,%s,%s,%s",
                task.getUUID(),
                Tasks.TASK,
                task.getName(),
                task.getTaskStatus(),
                task.getDescription(),
                null);
    }

    private String convertSubTaskToString(SubTask task) {
        return String.format(
                "%s,%s,%s,%s,%s,%s",
                task.getUUID(),
                Tasks.SUBTASK,
                task.getName(),
                task.getTaskStatus(),
                task.getDescription(),
                task.getEpicTaskUUID());
    }

    private String convertEpicTaskToString(EpicTask task) {
        return String.format(
                "%s,%s,%s,%s,%s,%s",
                task.getUUID(),
                Tasks.EPICTASK,
                task.getName(),
                task.getTaskStatus(),
                task.getDescription(),
                null);
    }

    @Override
    public void addNewTask(Task task) {
        super.addNewTask(task);
        save();
    }
}
