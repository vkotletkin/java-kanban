package com.practicum.yandex.servers.handlers;

import com.practicum.yandex.interfaces.TaskManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class EpicHandler extends BaseHttpHandler implements HttpHandler {

    public EpicHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String method = httpExchange.getRequestMethod();
        EpicTaskEndpoint endpoint = getEndpoint(httpExchange.getRequestURI().getPath(), method);

        switch (endpoint) {
            case GET_ALL_SUBTASKS ->
                    sendText(httpExchange, gson.toJson(taskManager.getSubTasks().toString()), 200);
            case GET_CERTAIN_SUBTASK -> handleGetCertainTask(httpExchange);
            case POST_SUBTASK -> handlePostTask(httpExchange);
            case DELETE_SUBTASK -> handleDeleteTask(httpExchange);
        }
    }

    protected EpicTaskEndpoint getEndpoint(String requestPath, String requestMethod) {

        if (requestPath.equals("/epics") && requestMethod.equals("GET")) {
            return EpicTaskEndpoint.GET_ALL_EPICS;
        } else if (requestPath.contains("/epics/") && requestMethod.equals("GET")) {
            return EpicTaskEndpoint.GET_CERTAIN_EPIC;
        } else if (requestPath.contains("/epics") && requestMethod.equals("POST")) {
            return EpicTaskEndpoint.POST_EPIC.POST_TASK;
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
