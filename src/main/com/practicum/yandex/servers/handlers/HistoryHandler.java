package com.practicum.yandex.servers.handlers;

import com.practicum.yandex.interfaces.TaskManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class HistoryHandler extends BaseHttpHandler implements HttpHandler {

    public HistoryHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            String method = httpExchange.getRequestMethod();

            if (method.equals("GET")) {
                sendText(httpExchange, gson.toJson(taskManager.getHistory()), 200);
            }
        } catch (Exception e) {
            this.sendErrorResponse(httpExchange);
        }
    }
}
