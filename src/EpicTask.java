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

    //    private void setTaskStatus(TaskStatus taskStatus) {
    //        this.taskStatus = taskStatus;
    //    }
    //
    //        private TaskStatus calculateEpicTaskStatus() {
    //            int newSubTaskCounter = 0;
    //            int inProgressTaskCounter = 0;
    //            int doneTaskCounter = 0;
    //
    //            for (SubTask subtask : subtaskStorage.values()) {
    //                switch (subtask.getTaskStatus()) {
    //                    case NEW -> newSubTaskCounter++;
    //                    case IN_PROGRESS -> inProgressTaskCounter++;
    //                    case DONE -> doneTaskCounter++;
    //                }
    //            }
    //
    //            if (doneTaskCounter == 0 && inProgressTaskCounter == 0) {
    //                return TaskStatus.NEW;
    //            } else if (newSubTaskCounter == 0 && inProgressTaskCounter == 0) {
    //                return TaskStatus.DONE;
    //            } else {
    //                return TaskStatus.IN_PROGRESS;
    //            }
    //        }
}
