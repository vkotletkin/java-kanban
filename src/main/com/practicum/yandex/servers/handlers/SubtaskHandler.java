package com.practicum.yandex.servers.handlers;

import com.practicum.yandex.exceptions.NotFoundException;
import com.practicum.yandex.exceptions.TimeIntersectionException;
import com.practicum.yandex.interfaces.TaskManager;
import com.practicum.yandex.tasks.SubTask;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

public class SubtaskHandler extends BaseHttpHandler implements HttpHandler {
    public SubtaskHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String method = httpExchange.getRequestMethod();
        SubtaskEndpoint endpoint = getEndpoint(httpExchange.getRequestURI().getPath(), method);

        switch (endpoint) {
            case GET_ALL_SUBTASKS ->
                    sendText(httpExchange, gson.toJson(taskManager.getSubTasks().toString()), 200);
            case GET_CERTAIN_SUBTASK -> handleGetCertainTask(httpExchange);
            case POST_SUBTASK -> handlePostTask(httpExchange);
            case DELETE_SUBTASK -> handleDeleteTask(httpExchange);
        }
    }

    private void handleGetCertainTask(HttpExchange httpExchange) throws IOException {
        String path = httpExchange.getRequestURI().getPath();
        String[] splitPath = splitPath(path);

        UUID subtaskUUID = UUID.fromString(splitPath[2]);

        Optional<SubTask> subTask = Optional.ofNullable(taskManager.getSubTaskByUUID(subtaskUUID));

        if (subTask.isPresent()) {
            this.sendText(httpExchange, gson.toJson(subTask.get()), 200);
        } else {
            this.sendNotFound(httpExchange);
        }
    }

    public void handlePostTask(HttpExchange httpExchange) throws IOException {
        InputStream inputStream = httpExchange.getRequestBody();

        try {
            String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            SubTask subtask = gson.fromJson(body, SubTask.class);

            if (subtask.getUUID() == null) {
                SubTask subTaskToAdd =
                        taskManager.createSubTask(
                                subtask.getName(),
                                subtask.getDescription(),
                                subtask.getTaskStatus(),
                                subtask.getEpicTaskUUID(),
                                subtask.getStartTime(),
                                subtask.getDuration());
                taskManager.addNewSubTask(subTaskToAdd);

                this.sendText(
                        httpExchange,
                        String.format(
                                "{\"response\": \"Объект был создан с UUID: %s\"}",
                                subTaskToAdd.getUUID()),
                        201);
            } else {
                taskManager.updateSubTask(subtask);
                this.sendText(
                        httpExchange,
                        String.format(
                                "{\"response\": \"Объект с UUID: %s был обновлен\"}",
                                subtask.getUUID()),
                        201);
            }

        } catch (TimeIntersectionException e) {
            this.sendHasInteractions(httpExchange);
        }
    }

    public void handleDeleteTask(HttpExchange httpExchange) throws IOException {
        try {
            String path = httpExchange.getRequestURI().getPath();
            String[] splitPath = splitPath(path);
            UUID taskUUID = UUID.fromString(splitPath[2]);

            taskManager.deleteSubTaskByUUID(taskUUID);
            this.sendText(
                    httpExchange,
                    String.format(
                            "{\"response\": \"Объект Task с UUID: %s успешно удален!\"}", taskUUID),
                    201);
        } catch (NotFoundException e) {
            this.sendNotFound(httpExchange);
        }
    }

    protected SubtaskEndpoint getEndpoint(String requestPath, String requestMethod) {

        if (requestPath.equals("/subtasks") && requestMethod.equals("GET")) {
            return SubtaskEndpoint.GET_ALL_SUBTASKS;
        } else if (requestPath.contains("/subtasks/") && requestMethod.equals("GET")) {
            return SubtaskEndpoint.GET_CERTAIN_SUBTASK;
        } else if (requestPath.contains("/subtasks") && requestMethod.equals("POST")) {
            return SubtaskEndpoint.POST_SUBTASK;
        } else if (requestPath.contains("/subtasks/") && requestMethod.equals("DELETE")) {
            return SubtaskEndpoint.DELETE_SUBTASK;
        }

        return SubtaskEndpoint.UNKNOWN;
    }

    enum SubtaskEndpoint {
        GET_ALL_SUBTASKS,
        GET_CERTAIN_SUBTASK,
        POST_SUBTASK,
        DELETE_SUBTASK,
        UNKNOWN
    }
}
