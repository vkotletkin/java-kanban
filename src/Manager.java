import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Manager {
    private final HashMap<UUID, Task> tasks;
    private final HashMap<UUID, SubTask> subtasks;
    private final HashMap<UUID, EpicTask> epicTasks;

    Manager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epicTasks = new HashMap<>();
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<Task>(tasks.values());
    }

    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<SubTask>(subtasks.values());
    }

    public ArrayList<EpicTask> getEpicTasks() {
        return new ArrayList<EpicTask>(epicTasks.values());
    }

    public void delAllTasks() {
        tasks.clear();
    }

    public void delAllSubTasks() {
        subtasks.clear();
        for (EpicTask epicTask : epicTasks.values()) {
            epicTasks.put(
                    epicTask.getUUID(),
                    new EpicTask(
                            epicTask.getName(),
                            epicTask.getDescription(),
                            epicTask.getUUID(),
                            TaskStatus.NEW));
        }
    }

    public void delAllEpicTasks() {
        epicTasks.clear();
        subtasks.clear();
    }

    public Task getTaskByUUID(UUID uuid) {
        return tasks.get(uuid);
    }

    public SubTask getSubTaskByUUID(UUID uuid) {
        return subtasks.get(uuid);
    }

    public EpicTask getEpicTaskByUUID(UUID uuid) {
        return epicTasks.get(uuid);
    }

    public void createNewTask(Task task) {
        tasks.put(task.getUUID(), task);
    }

    public void createNewSubTask(SubTask subtask) {
        subtasks.put(subtask.getUUID(), subtask);
        EpicTask changedEpicTask = epicTasks.get(subtask.getEpicTaskUUID());
        epicTasks.put(
                subtask.getEpicTaskUUID(),
                new EpicTask(
                        changedEpicTask.getName(),
                        changedEpicTask.getDescription(),
                        changedEpicTask.getUUID(),
                        calculateEpicTaskStatus(changedEpicTask.getUUID())));
    }

    public void createNewEpicTask(EpicTask epicTask) {
        epicTasks.put(epicTask.getUUID(), epicTask);
    }

    public void updateTask(Task task) {
        tasks.put(task.getUUID(), task);
    }

    public void updateSubTask(SubTask subtask) {
        subtasks.put(subtask.getUUID(), subtask);
        EpicTask changedEpicTask = epicTasks.get(subtask.getEpicTaskUUID());
        epicTasks.put(
                subtask.getEpicTaskUUID(),
                new EpicTask(
                        changedEpicTask.getName(),
                        changedEpicTask.getDescription(),
                        changedEpicTask.getUUID(),
                        calculateEpicTaskStatus(changedEpicTask.getUUID())));
    }

    public void updateEpicTask(EpicTask epicTask) {
        epicTasks.put(epicTask.getUUID(), epicTask);
    }

    public void delTaskByUUID(UUID uuid) {
        tasks.remove(uuid);
    }

    public void delSubTaskByUUID(UUID uuid) {
        subtasks.remove(uuid);
    }

    public void delEpicTaskByUUID(UUID uuid) {
        epicTasks.remove(uuid);
        // del all subtask with this uuid in epic or update
    }

    public ArrayList<SubTask> getEpicSubTasks(UUID epicUUID) {
        ArrayList<SubTask> epicSubtasks = new ArrayList<>();
        for (SubTask subtask : subtasks.values()) {
            if (subtask.getEpicTaskUUID().equals(epicUUID)) {
                epicSubtasks.add(subtask);
            }
        }
        return epicSubtasks;
    }

    private TaskStatus calculateEpicTaskStatus(UUID uuid) {
        int newSubTaskCounter = 0;
        int inProgressTaskCounter = 0;
        int doneTaskCounter = 0;

        for (SubTask subtask : subtasks.values()) {
            if (subtask.getEpicTaskUUID().equals(uuid)) {
                switch (subtask.getTaskStatus()) {
                    case NEW -> newSubTaskCounter++;
                    case IN_PROGRESS -> inProgressTaskCounter++;
                    case DONE -> doneTaskCounter++;
                }
            }
        }

        if (doneTaskCounter == 0 && inProgressTaskCounter == 0) {
            return TaskStatus.NEW;
        } else if (newSubTaskCounter == 0 && inProgressTaskCounter == 0) {
            return TaskStatus.DONE;
        } else {
            return TaskStatus.IN_PROGRESS;
        }
    }
}
