import java.util.UUID;

public class SubTask extends Task {
    private final EpicTask epicTask;

    SubTask(String name, String description, UUID uuid, TaskStatus taskStatus, EpicTask epicTask) {
        super(name, description, uuid, taskStatus);
        this.epicTask = epicTask;
        this.epicTask.addSubtaskToStorage(this);
    }

    public EpicTask getEpicTask() {
        return epicTask;
    }
}
