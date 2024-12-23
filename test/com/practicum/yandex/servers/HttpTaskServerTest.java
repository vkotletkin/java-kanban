package test.com.practicum.yandex.servers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.google.gson.Gson;
import com.practicum.yandex.interfaces.TaskManager;
import com.practicum.yandex.servers.HttpTaskServer;
import com.practicum.yandex.services.Managers;
import com.practicum.yandex.tasks.EpicTask;
import com.practicum.yandex.tasks.SubTask;
import com.practicum.yandex.tasks.Task;
import com.practicum.yandex.tasks.statuses.TaskStatus;
import com.sun.net.httpserver.HttpServer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import test.com.practicum.yandex.utils.TasksDescriptionForTests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class HttpTaskServerTest {

    TaskManager manager = Managers.getDefault();
    HttpServer httpTaskServer = new HttpTaskServer(manager).getHttpServer();
    Gson gson = HttpTaskServer.getGson();
    HttpClient client = HttpClient.newHttpClient();

    public HttpTaskServerTest() throws IOException {}

    @BeforeEach
    public void startServer() {
        httpTaskServer.start();
    }

    @AfterEach
    public void stopServer() {
        httpTaskServer.stop(0);
    }

    @Test
    public void testTasks() throws IOException, InterruptedException {
        Task task =
                manager.createTask(
                        TasksDescriptionForTests.taskRefactoringCode.name(),
                        TasksDescriptionForTests.taskRefactoringCode.description(),
                        TaskStatus.NEW,
                        TasksDescriptionForTests.taskRefactoringCode.localDateTime(),
                        TasksDescriptionForTests.taskRefactoringCode.duration());

        String taskJson = gson.toJson(task);

        URI url = URI.create("http://localhost:8080/tasks");

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(url)
                        .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                        .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());

        List<Task> tasksFromManager = manager.getTasks();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals(
                TasksDescriptionForTests.taskRefactoringCode.name(),
                tasksFromManager.get(0).getName(),
                "Некорректное имя задачи");

        URI urlForDeleteTask =
                URI.create(String.format("http://localhost:8080/tasks/%s", task.getUUID()));

        HttpRequest requestDelete = HttpRequest.newBuilder().uri(urlForDeleteTask).DELETE().build();

        HttpResponse<String> responseDelete =
                client.send(requestDelete, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, responseDelete.statusCode());

        client.send(request, HttpResponse.BodyHandlers.ofString());

        URI urlForGetTasks = URI.create("http://localhost:8080/tasks");

        HttpRequest requestGetTasks = HttpRequest.newBuilder().uri(urlForGetTasks).GET().build();

        HttpResponse<String> responseGetTasks =
                client.send(requestGetTasks, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, responseGetTasks.statusCode());

        URI urlForGetCertainTask =
                URI.create(String.format("http://localhost:8080/tasks/%s", task.getUUID()));

        HttpRequest requestGetCertainTask =
                HttpRequest.newBuilder().uri(urlForGetCertainTask).GET().build();

        HttpResponse<String> responseGetCertainTask =
                client.send(requestGetCertainTask, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, responseGetCertainTask.statusCode());
    }

    @Test
    public void testSubtaskAndEpic() throws IOException, InterruptedException {

        UUID uuid = UUID.randomUUID();

        EpicTask epicTask =
                manager.createEpicTask(
                        TasksDescriptionForTests.epicPinguinProject.name(),
                        TasksDescriptionForTests.epicPinguinProject.description(),
                        uuid,
                        TaskStatus.NEW,
                        LocalDateTime.of(2024, 12, 5, 12, 0, 0),
                        LocalDateTime.of(2024, 12, 5, 12, 15, 0),
                        Duration.ZERO);

        String epictaskJson = gson.toJson(epicTask);

        URI url = URI.create("http://localhost:8080/epics");

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(url)
                        .POST(HttpRequest.BodyPublishers.ofString(epictaskJson))
                        .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());
        assertEquals(1, manager.getEpicTasks().size());

        SubTask subTask =
                manager.createSubTask(
                        TasksDescriptionForTests.subTaskDocumentationWriting.name(),
                        TasksDescriptionForTests.subTaskDocumentationWriting.description(),
                        TaskStatus.NEW,
                        uuid,
                        TasksDescriptionForTests.subTaskDocumentationWriting.localDateTime(),
                        TasksDescriptionForTests.subTaskDocumentationWriting.duration());

        String subtaskJson = gson.toJson(subTask);

        URI urlSubtask = URI.create("http://localhost:8080/subtasks");

        HttpRequest requestSubtask =
                HttpRequest.newBuilder()
                        .uri(urlSubtask)
                        .POST(HttpRequest.BodyPublishers.ofString(subtaskJson))
                        .build();

        HttpResponse<String> responseSubtask =
                client.send(requestSubtask, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, responseSubtask.statusCode());
        assertEquals(1, manager.getSubTasks().size());

        URI urlForGetCertainSubTask =
                URI.create(String.format("http://localhost:8080/subtasks/%s", subTask.getUUID()));

        HttpRequest requestGetCertainSubTask =
                HttpRequest.newBuilder().uri(urlForGetCertainSubTask).GET().build();

        HttpResponse<String> responseGetCertainSubTask =
                client.send(requestGetCertainSubTask, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, responseGetCertainSubTask.statusCode());

        URI urlForGetCertainEpic =
                URI.create(String.format("http://localhost:8080/epics/%s", epicTask.getUUID()));

        HttpRequest requestGetCertainEpic =
                HttpRequest.newBuilder().uri(urlForGetCertainEpic).GET().build();

        HttpResponse<String> responseGetCertainEpic =
                client.send(requestGetCertainEpic, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, responseGetCertainEpic.statusCode());
    }

    @Test
    public void checkHistoryAndPrioritized() throws IOException, InterruptedException {
        Task task =
                manager.createTask(
                        TasksDescriptionForTests.taskRefactoringCode.name(),
                        TasksDescriptionForTests.taskRefactoringCode.description(),
                        TaskStatus.NEW,
                        TasksDescriptionForTests.taskRefactoringCode.localDateTime(),
                        TasksDescriptionForTests.taskRefactoringCode.duration());

        String taskJson = gson.toJson(task);

        URI url = URI.create("http://localhost:8080/tasks");

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(url)
                        .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                        .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());

        List<Task> tasksFromManager = manager.getTasks();

        assertNotNull(tasksFromManager);
        assertEquals(1, tasksFromManager.size());

        assertEquals(
                TasksDescriptionForTests.taskRefactoringCode.name(),
                tasksFromManager.get(0).getName(),
                "Некорректное имя задачи");

        URI urlForGetCertainTask =
                URI.create(String.format("http://localhost:8080/tasks/%s", task.getUUID()));

        HttpRequest requestGetCertainTask =
                HttpRequest.newBuilder().uri(urlForGetCertainTask).GET().build();

        HttpResponse<String> responseGetCertainTask =
                client.send(requestGetCertainTask, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, responseGetCertainTask.statusCode());

        URI urlGetHistory = URI.create("http://localhost:8080/history");

        HttpRequest requestGetHistory = HttpRequest.newBuilder().uri(urlGetHistory).GET().build();

        HttpResponse<String> responseGetHistory =
                client.send(requestGetHistory, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, responseGetHistory.statusCode());

        URI urlGetPrioritizedTasks = URI.create("http://localhost:8080/prioritized");

        HttpRequest requestGetPrioritizedTasks =
                HttpRequest.newBuilder().uri(urlGetPrioritizedTasks).GET().build();

        HttpResponse<String> responseGetPrioritizedTasks =
                client.send(requestGetPrioritizedTasks, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, responseGetHistory.statusCode());
    }
}
