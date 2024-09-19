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

    @Override
    public String toString() {
        return super.toString();
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<Task>(tasks.values());
    }

    public ArrayList<SubTask> getSubtasks() {
        return new ArrayList<SubTask>(subtasks.values());
    }

    public ArrayList<EpicTask> getEpicTasks() {
        return new ArrayList<EpicTask>(epicTasks.values());
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllSubTasks() {
        subtasks.clear();
    }

    public void deleteAllEpicTasks() {
        epicTasks.clear();
    }

    public void getTaskByUUID(UUID uuid) {}

    public void getSubTaskByUUID(UUID uuid) {}

    public void getEpicTaskByUUID(UUID uuid) {}

    public void createNewTask(Task task) {
        tasks.put(task.getUUID(), task);
    }

    public void createNewSubTask(SubTask subtask) {
        subtasks.put(subtask.getUUID(), subtask);
    }

    public void createNewEpicTask(EpicTask epicTask) {
        epicTasks.put(epicTask.getUUID(), epicTask);
    }

    public void updateTask(Task task) {}

    public void updateSubTask(SubTask task) {}

    public void updateEpicTask(EpicTask epicTask) {}

    public void delTaskByUUID(UUID uuid) {}

    public void delSubTaskByUUID(UUID uuid) {}

    public void delEpicTaskByUUID(UUID uuid) {}

    public ArrayList<SubTask> getEpicSubtasks(UUID uuid) {
        return new ArrayList<SubTask>(epicTasks.get(uuid).getSubtaskStorage().values());
    }
}
