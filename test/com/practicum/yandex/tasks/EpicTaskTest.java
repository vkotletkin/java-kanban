package test.com.practicum.yandex.tasks;

import com.practicum.yandex.interfaces.TaskManager;
import com.practicum.yandex.services.Managers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class EpicTaskTest {

    public static TaskManager taskManager;

    @BeforeEach
    public void beforeAll() {
        taskManager = Managers.getDefault();
    }

    /*

   Рекомендуемый тест: проверьте, что объект Epic нельзя добавить в самого себя в виде подзадачи;

    Реализовать невозможно, так как не позволяется его передавать в виде подзадачи, при создании
    SubTask он автоматически привязывается к своему EpicTask по UUID EpicTask.

       @Test
       public void notShouldEpicTaskBeAddedToItself() {
       }

    */

}
