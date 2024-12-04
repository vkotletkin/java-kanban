package com.practicum.yandex.servers.handlers;

import com.google.gson.Gson;
import com.practicum.yandex.interfaces.TaskManager;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BaseHttpHandler {

    TaskManager taskManager;
    Gson gson;

    public BaseHttpHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
        gson = new Gson();
    }

    protected void sendText(HttpExchange h, String text, int statusCode) throws IOException {
        byte[] resp = gson.toJson(text).getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(statusCode, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    protected void sendNotFound(HttpExchange h, int statusCode) throws IOException {
        byte[] resp = gson.toJson("Объект не был найден").getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(statusCode, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    protected void sendHasInteractions(HttpExchange h, int statusCode) throws IOException {
        byte[] resp =
                gson.toJson(
                                "При создании или обновлении задачи произошло пересечение с другими задачами")
                        .getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(statusCode, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }
}
