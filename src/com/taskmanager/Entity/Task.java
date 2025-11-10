package com.taskmanager.Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class Task {
    static int id = 1;
    String description;
    String status;
    String createdAt;
    String updatedAt;

    public Task(int id, String description, String status, String createdAt) {
        id = id++;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Task: Id: " + id + ", Description: " + description + ", status: " + status + ", createdAt: " + createdAt + "\n";
    }



    public static void createTask(String description) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH-mm-ss");
        String createdAt = dateTime.format(formatter);
        String status = "todo";

        Task task = new Task(id, description, status, createdAt);

        List<Task> tasks = new ArrayList<>();
        tasks.add(task);

        System.out.println("Your task was created! " + tasks);
    }

}