package com.practicum.yandex.servers;

import com.practicum.yandex.interfaces.TaskManager;
import com.practicum.yandex.servers.handlers.HistoryHandler;
import com.practicum.yandex.servers.handlers.PrioritizedTaskHandler;
import com.practicum.yandex.servers.handlers.SubtaskHandler;
import com.practicum.yandex.servers.handlers.TaskHandler;
import com.practicum.yandex.services.Managers;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class HttpTaskServer {
    public static void main(String[] args) {

        try {
            TaskManager taskManager = Managers.getDefault();

            HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
            httpServer.createContext("/tasks", new TaskHandler(taskManager));
            httpServer.createContext("/subtasks", new SubtaskHandler(taskManager));

            httpServer.createContext("/history", new HistoryHandler(taskManager));
            httpServer.createContext("/prioritized", new PrioritizedTaskHandler(taskManager));
            httpServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
