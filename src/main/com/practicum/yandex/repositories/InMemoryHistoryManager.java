package com.practicum.yandex.repositories;

import com.practicum.yandex.interfaces.HistoryManager;
import com.practicum.yandex.tasks.Task;
import com.practicum.yandex.utils.Node;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<UUID, Node> lastTasks;
    private Node head;
    private Node tail;

    public InMemoryHistoryManager() {
        lastTasks = new HashMap<>();
    }

    public List<Task> getHistory() {
        return getTasks();
    }

    public void remove(UUID uuid) {
        removeNode(lastTasks.get(uuid));
        lastTasks.remove(uuid);
    }

    private void removeNode(Node node) {
        if (node == null) {
            return;
        }

        final Node prev = node.getPrev();
        final Node next = node.getNext();

        if (node == head && next != null) {
            next.setPrev(null);
            head = next;
        } else if (node == tail && prev != null) {
            prev.setNext(null);
            tail = prev;
        } else if (prev == null && next == null) {
            node = null;
            return;
        } else {
            prev.setNext(next);
            next.setPrev(prev);
        }
    }

    public void linkLast(Task task) {
        final Node oldLast = tail;
        final Node newNode = new Node(oldLast, task, null);
        tail = newNode;
        if (oldLast == null) {
            head = newNode;
        } else {
            oldLast.setNext(newNode);
        }
    }

    public List<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        Node node = head;

        while (node != null) {
            tasks.add(node.getTask());
            node = node.getNext();
        }
        return tasks;
    }

    public void add(Task task) {
        if (task == null) {
            return;
        }
        linkLast(task);
        remove(task.getUUID());
        lastTasks.put(task.getUUID(), tail);
    }
}
