import java.util.UUID;

public class SubTask extends Task {
    private final UUID epicTaskUUID;

    SubTask(String name, String description, UUID uuid, TaskStatus taskStatus, UUID epicTaskUUID) {
        super(name, description, uuid, taskStatus);
        this.epicTaskUUID = epicTaskUUID;
    }

    public UUID getEpicTaskUUID() {
        return epicTaskUUID;
    }

    @Override
    public String toString() {
        return "SubTask{"
                + "name='"
                + getName()
                + '\''
                + ", description='"
                + getDescription()
                + '\''
                + ", uuid="
                + getUUID()
                + ", taskStatus="
                + getTaskStatus()
                + ", epicTaskUUID="
                + epicTaskUUID
                + '}';
    }
}
