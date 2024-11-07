package test.com.practicum.yandex.services;

import com.practicum.yandex.services.Managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ManagersTest {

    @Test
    public void shouldReturnInitializedManagers() {
        Assertions.assertNotNull(Managers.getDefault());
        Assertions.assertNotNull(Managers.getDefaultHistory());
        Assertions.assertNotNull(Managers.getFileBackedTaskManager());
    }
}
