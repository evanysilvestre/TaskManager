package com.taskmanager.Entity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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

    public Task(int id, String description, String status, String createdAt, String updatedAt) {
        id = id++;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Task: Id: " + id +
                ", Description: " + description +
                ", status: " + status +
                ", createdAt: " + createdAt + "\n";
    }

    // Helper method to convert a single Task object to a JSON string
    private static String taskToJsonString(Task task) {
        // Manually format the string for one task object:
        return String.format(
                "{\n" +
                        "    \"id\": %d,\n" +
                        "    \"description\": \"%s\",\n" +
                        "    \"status\": \"%s\",\n" +
                        "    \"createdAt\": \"%s\"\n" +
                        "    \"updatedAt\": \"%s\"\n" +
                        "}",
                id, // Assuming getId() exists and returns an integer
                task.description,
                task.status,
                task.createdAt,
                task.updatedAt
        );
    }

    public static void createTask(String description) { // Pass 'id' or generate it

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH-mm-ss");
        String createdAt = dateTime.format(formatter);
        String status = "todo";
        String updatedAt = "";

        // 1. Create the new Task object
        Task newTask = new Task(id, description, status, createdAt, updatedAt);

        // 2. Convert the new Task to a JSON string
        String newTaskJson = taskToJsonString(newTask);

        Path filePath = Path.of("data.json");

        try {
            // --- READ EXISTING DATA ---
            String existingContent = "";

            // If the file exists, read all its content
            if (Files.exists(filePath)) {
                existingContent = Files.readString(filePath);
            }

            // --- MANIPULATE AND WRITE ---
            String outputContent;

            if (existingContent.trim().isEmpty() || existingContent.trim().equals("[]")) {
                // Case 1: File is empty or contains "[]" -> Start a new array
                // Format: [ newTaskJson ]
                outputContent = "[" + newTaskJson + "\n]";

            } else {
                // Case 2: Array already exists -> Insert the new task before the closing bracket ']'
                // 1. Remove the last character (which should be ']')
                String contentWithoutClosingBracket = existingContent.trim().substring(0, existingContent.trim().length() - 1);

                // 2. Add a comma, the new JSON object, and the closing bracket ']'
                // Format: existingContentWithoutClosingBracket , newTaskJson ]
                outputContent = contentWithoutClosingBracket + ",\n" + newTaskJson + "\n]";
            }

            // Write the complete, updated content back to the file, overwriting the old content
            Files.writeString(filePath, outputContent, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("Successfully wrote new task to the file.");

        } catch (IOException e) {
            System.out.println("An error occurred during file operations.");
            e.printStackTrace();
        }

        System.out.println("Your task was created: " + newTaskJson);
    }

    /*public static void createTask(String description) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH-mm-ss");
        String createdAt = dateTime.format(formatter);
        String status = "todo";
        String updatedAt = "";

        Task task = new Task(id, description, status, createdAt, updatedAt);

        List<Task> tasks = new ArrayList<>();
        tasks.add(task);

        try {
            FileWriter myDataJsonWriter = new FileWriter("data.json");
            myDataJsonWriter.write(tasks.toString());
            myDataJsonWriter.close();  // must close manually
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println("Your task was created! " + tasks);
    }
*/

}