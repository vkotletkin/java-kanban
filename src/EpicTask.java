import java.util.UUID;

public class EpicTask extends Task {

    EpicTask(String name, String description, UUID uuid) {
        super(name, description, uuid, TaskStatus.NEW);
    }

    EpicTask(String name, String description, UUID uuid, TaskStatus taskStatus) {
        super(name, description, uuid, taskStatus);
    }

    @Override
    public String toString() {
        return "EpicTask{"
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
                + '}';
    }
}
