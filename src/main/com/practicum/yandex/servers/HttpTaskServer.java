package com.practicum.yandex.servers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.practicum.yandex.adapters.DurationAdapter;
import com.practicum.yandex.adapters.LocalDateTimeAdapter;
import com.practicum.yandex.interfaces.TaskManager;
import com.practicum.yandex.servers.handlers.*;
import com.practicum.yandex.services.Managers;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTaskServer {

    TaskManager taskManager;
    HttpServer httpServer;

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.taskManager = taskManager;
        httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
        httpServer.createContext("/tasks", new TaskHandler(taskManager));
        httpServer.createContext("/subtasks", new SubtaskHandler(taskManager));
        httpServer.createContext("/epics", new EpicHandler(taskManager));
        httpServer.createContext("/history", new HistoryHandler(taskManager));
        httpServer.createContext("/prioritized", new PrioritizedTaskHandler(taskManager));
    }

    public HttpServer getHttpServer() {
        return httpServer;
    }

    public static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .setPrettyPrinting()
                .create();
    }

    public static void main(String[] args) throws IOException {

        TaskManager taskManager = Managers.getDefault();

        HttpTaskServer httpTaskServer = new HttpTaskServer(taskManager);
        httpTaskServer.getHttpServer().start();
    }
}
