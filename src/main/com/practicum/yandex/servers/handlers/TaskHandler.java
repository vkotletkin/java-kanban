package com.practicum.yandex.servers.handlers;

import com.google.gson.*;
import com.practicum.yandex.exceptions.NotFoundException;
import com.practicum.yandex.exceptions.TimeIntersectionException;
import com.practicum.yandex.interfaces.TaskManager;
import com.practicum.yandex.tasks.Task;
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
            case GET_ALL_TASKS ->
                    sendText(httpExchange, gson.toJson(taskManager.getTasks().toString()), 200);
            case GET_CERTAIN_TASK -> handleGetCertainTask(httpExchange);
            case POST_TASK -> handlePostTask(httpExchange);
            case DELETE -> handleDeleteTask(httpExchange);
        }
    }

    public void handleGetCertainTask(HttpExchange httpExchange) throws IOException {
        try {
            String path = httpExchange.getRequestURI().getPath();
            String[] splitPath = splitPath(path);

            UUID taskUUID = UUID.fromString(splitPath[2]);

            Optional<Task> task = Optional.ofNullable(taskManager.getTaskByUUID(taskUUID));

            if (task.isPresent()) {
                this.sendText(httpExchange, gson.toJson(task.get()), 200);
            } else {
                this.sendNotFound(httpExchange);
            }
        } catch (Exception e) {
            this.sendErrorResponse(httpExchange);
        }
    }

    public void handlePostTask(HttpExchange httpExchange) throws IOException {
        InputStream inputStream = httpExchange.getRequestBody();

        try {
            String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            Task task = gson.fromJson(body, Task.class);

            if (taskManager.getTaskByUUID(task.getUUID()) == null) {
                Task taskToAdd =
                        taskManager.createTask(
                                task.getName(),
                                task.getDescription(),
                                task.getUUID(),
                                task.getTaskStatus(),
                                task.getStartTime(),
                                task.getDuration());
                taskManager.addNewTask(taskToAdd);

                this.sendText(
                        httpExchange,
                        String.format(
                                "{\"response\": \"Объект был создан с UUID: %s\"}",
                                taskToAdd.getUUID()),
                        201);
            } else {
                taskManager.updateTask(task);
                this.sendText(
                        httpExchange,
                        String.format(
                                "{\"response\": \"Объект с UUID: %s был обновлен\"}",
                                task.getUUID()),
                        201);
            }

        } catch (TimeIntersectionException e) {
            this.sendHasInteractions(httpExchange);
        } catch (Exception e) {
            this.sendErrorResponse(httpExchange);
        }
    }

    public void handleDeleteTask(HttpExchange httpExchange) throws IOException {
        try {
            String path = httpExchange.getRequestURI().getPath();
            String[] splitPath = splitPath(path);
            UUID taskUUID = UUID.fromString(splitPath[2]);

            taskManager.deleteTaskByUUID(taskUUID);
            this.sendText(
                    httpExchange,
                    String.format(
                            "{\"response\": \"Объект Task с UUID: %s успешно удален!\"}", taskUUID),
                    200);
        } catch (NotFoundException e) {
            this.sendNotFound(httpExchange);
        } catch (Exception e) {
            this.sendErrorResponse(httpExchange);
        }
    }

    protected TaskEndpoint getEndpoint(String requestPath, String requestMethod) {

        if (requestPath.equals("/tasks") && requestMethod.equals("GET")) {
            return TaskHandler.TaskEndpoint.GET_ALL_TASKS;
        } else if (requestPath.contains("/tasks/") && requestMethod.equals("GET")) {
            return TaskHandler.TaskEndpoint.GET_CERTAIN_TASK;
        } else if (requestPath.contains("/tasks") && requestMethod.equals("POST")) {
            return TaskHandler.TaskEndpoint.POST_TASK;
        } else if (requestPath.contains("/tasks/") && requestMethod.equals("DELETE")) {
            return TaskHandler.TaskEndpoint.DELETE;
        }

        return TaskHandler.TaskEndpoint.UNKNOWN;
    }

    enum TaskEndpoint {
        GET_ALL_TASKS,
        GET_CERTAIN_TASK,
        POST_TASK,
        DELETE,

        UNKNOWN
    }
}
