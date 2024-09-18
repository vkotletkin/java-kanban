import javax.sound.midi.Soundbank;
import java.util.Map;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        EpicTask epicTask =
                new EpicTask(
                        "Mark Epic",
                        "Mark Classifier Epic Kanban",
                        UUID.randomUUID(),
                        TaskStatus.NEW);

        SubTask subtask1 =
                new SubTask(
                        "Mark Entities Api",
                        "Added features to entities api",
                        UUID.randomUUID(),
                        TaskStatus.NEW,
                        epicTask);

        Task task1 =
                new Task(
                        "Clear psrv01",
                        "Sort folders",
                        UUID.randomUUID(),
                        TaskStatus.NEW);

        for (Map.Entry<UUID, SubTask> a : epicTask.getSubtaskStorage().entrySet()) {
            System.out.println(a.getKey() + " " + a.getValue().getName() + " " + a.getValue().getDescription());
        }

        System.out.println(subtask1.getEpicTask().getName());

        System.out.println(task1.getName() + " " + task1.getDescription());
    }
}
