package com.practicum.yandex.servers.handlers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.practicum.yandex.interfaces.TaskManager;
import com.practicum.yandex.tasks.Task;
import com.practicum.yandex.tasks.statuses.TaskStatus;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

public class TaskHandler extends BaseHttpHandler implements HttpHandler {
    public TaskHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        TaskEndpoint endpoint = getEndpoint(httpExchange.getRequestURI().getPath(), method);
        switch (endpoint) {
            case GET_ALL_TASKS -> sendText(httpExchange, taskManager.getTasks().toString(), 200);
            case GET_CERTAIN_TASK -> handleGetCertainTask(httpExchange);
            case POST_TASK -> handlePostTask(httpExchange);
        }
    }

    public void handleGetCertainTask(HttpExchange httpExchange) throws IOException {
        String path = httpExchange.getRequestURI().getPath();
        String[] pathElements = path.split("/");

        UUID taskUUID = UUID.fromString(pathElements[2]);

        Optional<Task> task = Optional.ofNullable(taskManager.getTaskByUUID(taskUUID));

        if (task.isPresent()) {
            this.sendText(httpExchange, task.toString(), 200);
        } else {
            this.sendNotFound(httpExchange, 404);
        }
    }

    public void handlePostTask(HttpExchange httpExchange) throws IOException {
        String path = httpExchange.getRequestURI().getPath();
        String[] pathElements = path.split("/");
        InputStream inputStream = httpExchange.getRequestBody();

        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        JsonElement jsonElement = JsonParser.parseString(body);

        if (jsonElement.isJsonObject()) {

            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String name = jsonObject.get("name").getAsString();
            String description = jsonObject.get("description").getAsString();
            TaskStatus taskStatus = TaskStatus.valueOf(jsonObject.get("task_status").getAsString());


            if (pathElements.length == 2) {
                taskManager.addNewTask(taskManager.createTask(name, description, taskStatus));
            }
            UUID taskUUID = UUID.fromString(pathElements[3]);
        }
    }

    private TaskEndpoint getEndpoint(String requestPath, String requestMethod) {
        String[] pathParts = requestPath.split("/");

        if (pathParts.length == 2 && requestMethod.equals("GET")) {
            return TaskEndpoint.GET_ALL_TASKS;
        } else if (pathParts.length == 3 && requestMethod.equals("GET")) {
            return TaskEndpoint.GET_CERTAIN_TASK;
        } else if (pathParts.length == 2 && requestMethod.equals("POST")) {
            return TaskEndpoint.POST_TASK;
        }

        return TaskEndpoint.UNKNOWN;
    }

    enum TaskEndpoint {
        GET_ALL_TASKS,
        GET_CERTAIN_TASK,
        POST_TASK,
        UNKNOWN
    }
}
