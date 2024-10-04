package test.com.practicum.yandex.tasks;

import com.practicum.yandex.interfaces.TaskManager;
import com.practicum.yandex.services.Managers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class SubTaskTest {

    public static TaskManager taskManager;

    @BeforeEach
    public static void beforeAll() {
        taskManager = Managers.getDefault();
    }

    // Рекомендуемый тест:
    // проверьте, что объект Subtask нельзя сделать своим же эпиком;
    // Эпик привязывается к объекту SubTask исключительно идентификатору, реализовать данный тест не
    // представляется возможным

    //    @Test
    //    public void notShouldEpicTaskBeAddedToItself() {}
}
