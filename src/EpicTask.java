import java.util.HashMap;
import java.util.UUID;

public class EpicTask extends Task {
//Завершение всех подзадач эпика считается завершением эпика. TODO
    private final HashMap<UUID, SubTask> subtaskStorage;


    EpicTask(String name, String description, UUID uuid, TaskStatus taskStatus) {
        super(name, description, uuid, taskStatus);
        subtaskStorage = new HashMap<>();
    }

    public HashMap<UUID, SubTask> getSubtaskStorage() {
        return subtaskStorage;
    }

    public void addSubtaskToStorage(SubTask subtask) {
        subtaskStorage.put(subtask.getUUID(), subtask);
        System.out.println(
                "Subtask with UUID: " + subtask.getUUID() + " successfully added to storage");
    }
}
