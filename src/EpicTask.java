import java.util.HashMap;
import java.util.UUID;

public class EpicTask extends Task {
    private final HashMap<UUID, SubTask> subtaskStorage;
    private TaskStatus taskStatus;

    EpicTask(String name, String description, UUID uuid) {
        super(name, description, uuid, TaskStatus.NEW);
        this.taskStatus = TaskStatus.NEW;
        subtaskStorage = new HashMap<>();
    }

    public HashMap<UUID, SubTask> getSubtaskStorage() {
        return subtaskStorage;
    }

    public void addSubtaskToStorage(SubTask subtask) {
        subtaskStorage.put(subtask.getUUID(), subtask);
        System.out.println(
                "Subtask with UUID: " + subtask.getUUID() + " successfully added to storage");
        setTaskStatus(calculateEpicTaskStatus());
        System.out.println("Currently status of Epic: " + taskStatus);
    }

    private void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    private TaskStatus calculateEpicTaskStatus() {
        int newSubTaskCounter = 0;
        int inProgressTaskCounter = 0;
        int doneTaskCounter = 0;

        for (SubTask subtask : subtaskStorage.values()) {
            switch (subtask.getTaskStatus()) {
                case NEW -> newSubTaskCounter++;
                case IN_PROGRESS -> inProgressTaskCounter++;
                case DONE -> doneTaskCounter++;
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
