import java.util.Objects;
import java.util.UUID;

public class Task {

    private String name;
    private String description;
    private UUID uuid;
    private TaskStatus taskStatus;

    Task(String name, String description, UUID uuid, TaskStatus taskStatus) {
        this.name = name;
        this.description = description;
        this.uuid = uuid;
        this.taskStatus = taskStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name)
                && Objects.equals(description, task.description)
                && Objects.equals(uuid, task.uuid)
                && taskStatus == task.taskStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, uuid, taskStatus);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    // TODO vrode kak obnovlyaetsya polnosty, ya tak ponimay eto change in hashmap
//    public void setTaskStatus(TaskStatus taskStatus) {
//        this.taskStatus = taskStatus;
//    }
}
