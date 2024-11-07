package test.com.practicum.yandex.exceptions;

import com.practicum.yandex.exceptions.ManagerSaveException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ManagerSaveExceptionTest {

    @Test
    public void shouldReturnException() {
        Assertions.assertThrows(
                ManagerSaveException.class,
                () -> {
                    throw new ManagerSaveException("Ошибка при работе с файлом");
                });
    }
}
