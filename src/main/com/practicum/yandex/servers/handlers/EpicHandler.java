package com.practicum.yandex.servers.handlers;

import com.practicum.yandex.exceptions.NotFoundException;
import com.practicum.yandex.exceptions.TimeIntersectionException;
import com.practicum.yandex.interfaces.TaskManager;
import com.practicum.yandex.tasks.EpicTask;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

public class EpicHandler extends BaseHttpHandler implements HttpHandler {

    public EpicHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String method = httpExchange.getRequestMethod();
        EpicTaskEndpoint endpoint = getEndpoint(httpExchange.getRequestURI().getPath(), method);

        switch (endpoint) {
            case GET_ALL_EPICS ->
                    sendText(httpExchange, gson.toJson(taskManager.getEpicTasks().toString()), 200);
            case GET_CERTAIN_EPIC -> handleGetCertainEpic(httpExchange);
            case POST_EPIC -> handlePostEpic(httpExchange);
            case DELETE_EPIC -> handleDeleteEpic(httpExchange);
            case GET_EPIC_SUBTASKS -> handleGetEpicSubtasks(httpExchange);
        }
    }

    private void handleGetEpicSubtasks(HttpExchange httpExchange) throws IOException {
        String path = httpExchange.getRequestURI().getPath();
        String[] splitPath = splitPath(path);

        UUID epictaskUUID = UUID.fromString(splitPath[2]);

        Optional<EpicTask> epictask =
                Optional.ofNullable(taskManager.getEpicTaskByUUID(epictaskUUID));

        if (epictask.isPresent()) {
            this.sendText(
                    httpExchange, gson.toJson(taskManager.getEpicSubTasks(epictaskUUID)), 200);
        } else {
            this.sendNotFound(httpExchange);
        }
    }

    private void handleDeleteEpic(HttpExchange httpExchange) throws IOException {
        try {
            String path = httpExchange.getRequestURI().getPath();
            String[] splitPath = splitPath(path);

            UUID epictaskUUID = UUID.fromString(splitPath[2]);

            taskManager.deleteEpicTaskByUUID(epictaskUUID);
            this.sendText(
                    httpExchange,
                    String.format(
                            "{\"response\": \"Объект Task с UUID: %s успешно удален!\"}",
                            epictaskUUID),
                    201);
        } catch (NotFoundException e) {
            this.sendNotFound(httpExchange);
        }
    }

    private void handlePostEpic(HttpExchange httpExchange) throws IOException {
        InputStream inputStream = httpExchange.getRequestBody();

        try {
            String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            EpicTask epictask = gson.fromJson(body, EpicTask.class);

            if (epictask.getUUID() == null
                    || taskManager.getEpicTaskByUUID(epictask.getUUID()) == null) {
                EpicTask epicTaskToAdd =
                        taskManager.createEpicTask(
                                epictask.getName(),
                                epictask.getDescription(),
                                epictask.getTaskStatus(),
                                epictask.getStartTime(),
                                epictask.getEndTime(),
                                epictask.getDuration());
                taskManager.addNewEpicTask(epicTaskToAdd);

                this.sendText(
                        httpExchange,
                        String.format(
                                "{\"response\": \"Объект был создан с UUID: %s\"}",
                                epicTaskToAdd.getUUID()),
                        201);
            } else {
                taskManager.updateEpicTask(epictask);
                this.sendText(
                        httpExchange,
                        String.format(
                                "{\"response\": \"Объект с UUID: %s был обновлен\"}",
                                epictask.getUUID()),
                        201);
            }

        } catch (TimeIntersectionException e) {
            this.sendHasInteractions(httpExchange);
        }
    }

    private void handleGetCertainEpic(HttpExchange httpExchange) throws IOException {
        String path = httpExchange.getRequestURI().getPath();
        String[] splitPath = splitPath(path);

        UUID epicTaskUUID = UUID.fromString(splitPath[2]);

        Optional<EpicTask> epicTask =
                Optional.ofNullable(taskManager.getEpicTaskByUUID(epicTaskUUID));

        if (epicTask.isPresent()) {
            this.sendText(httpExchange, gson.toJson(epicTask.get()), 200);
        } else {
            this.sendNotFound(httpExchange);
        }
    }

    protected EpicTaskEndpoint getEndpoint(String requestPath, String requestMethod) {

        if (requestPath.equals("/epics") && requestMethod.equals("GET")) {
            return EpicTaskEndpoint.GET_ALL_EPICS;
        } else if (requestPath.contains("/epics/")
                && requestMethod.equals("GET")
                && !requestPath.contains("/subtasks")) {
            return EpicTaskEndpoint.GET_CERTAIN_EPIC;
        } else if (requestPath.contains("/epics") && requestMethod.equals("POST")) {
            return EpicTaskEndpoint.POST_EPIC;
        } else if (requestPath.contains("/epics/") && requestMethod.equals("DELETE")) {
            return EpicTaskEndpoint.DELETE_EPIC;
        } else if (requestPath.contains("/epics/")
                && requestPath.contains("/subtasks")
                && requestMethod.equals("GET")) {
            return EpicTaskEndpoint.GET_EPIC_SUBTASKS;
        }

        return EpicTaskEndpoint.UNKNOWN;
    }

    enum EpicTaskEndpoint {
        GET_ALL_EPICS,
        GET_CERTAIN_EPIC,
        GET_EPIC_SUBTASKS,
        POST_EPIC,
        DELETE_EPIC,
        UNKNOWN
    }
}
