package com.taskmanager.Entity;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class Task {
    int id;
    String description;
    String status;
    String createdAt;
    String updatedAt;

    public Task(int id, String description, String status, String createdAt, String updatedAt) {
        this.id = id;
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
                ", createdAt: " + createdAt +
                ", updatedAt: " + updatedAt + "\n";
    }

    // Helper method to convert a single Task object to a JSON string
    private static String taskToJsonString(Task task) {
        // Manually format the string for one task object:
        return String.format(
                "{\n" +
                        "    \"id\": %d,\n" +
                        "    \"description\": \"%s\",\n" +
                        "    \"status\": \"%s\",\n" +
                        "    \"createdAt\": \"%s\",\n" +
                        "    \"updatedAt\": \"%s\"\n" +
                        "}",
                task.id, // Assuming getId() exists and returns an integer
                task.description,
                task.status,
                task.createdAt,
                task.updatedAt
        );
    }

    public static void writeFile(String content) {
        try (FileWriter fw = new FileWriter("data.json")) {
            fw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //Method is working
    public static String readFile() {
        String fileName = "data.json";
        File file = new File(fileName);
        if (!file.exists()) {
            return "[]";
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return "[]";
        }
    }


    //Method is working
    public static int loadLastId() {
        File file = new File("data.json");

        if (!file.exists()) {
            System.out.println("No file found, starting fresh.");
            return 0;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            // Read entire file into 1 string
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            String json = content.toString();

            int lastId = 0;
            int index = 0;

            // Search for every "id": number
            while ((index = json.indexOf("\"id\"", index)) != -1) {

                int colon = json.indexOf(":", index);
                int comma = json.indexOf(",", colon);

                // Extract text after colon until comma (or end)
                String rawId = (comma == -1)
                        ? json.substring(colon + 1).trim()
                        : json.substring(colon + 1, comma).trim();

                // Keep only numbers (remove quotes, spaces, etc.)
                rawId = rawId.replaceAll("[^0-9]", "");

                if (!rawId.isEmpty()) {
                    int id = Integer.parseInt(rawId);
                    if (id > lastId) lastId = id;
                }

                // Move forward to continue the search
                index = colon + 1;
            }

            return lastId;

        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public static void createTask(String description) { // Pass 'id' or generate it

        Path filePath = Path.of("data.json");

        int lastIndex = loadLastId();

        int id = lastIndex+1;
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH-mm-ss");
        String createdAt = dateTime.format(formatter);
        String status = "todo";
        String updatedAt = "";

        Task newTask = new Task(id, description, status, createdAt, updatedAt);

        List<Task> todoTask = new ArrayList<>();
        todoTask.add(newTask);

        // 2. Convert the new Task to a JSON string
        String newTaskJson = taskToJsonString(newTask);

        try {
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

    public static void deleteTask(int idToDelete) {
        String json = readFile().trim();

        // Remove [ and ]
        json = json.substring(1, json.length() - 1).trim();

        if (json.isEmpty()) {
            System.out.println("No tasks to delete.");
            return;
        }

        // Split objects by "},{"
        String[] objects = json.split("\\},\\{");

        List<String> updatedObjects = new ArrayList<>();

        for (String obj : objects) {
            // Re-add braces because split removed them
            String fixedObj = obj;

            if (!fixedObj.startsWith("{")) fixedObj = "{" + fixedObj;
            if (!fixedObj.endsWith("}")) fixedObj = fixedObj + "}";

            // Find id field
            int idIndex = fixedObj.indexOf("\"id\":");
            int colon = fixedObj.indexOf(":", idIndex);
            int comma = fixedObj.indexOf(",", colon);

            // If no comma, it's the last field
            if (comma == -1) comma = fixedObj.indexOf("}", colon);

            int idValue = Integer.parseInt(fixedObj.substring(colon + 1, comma).trim());

            if (idValue != idToDelete) {
                updatedObjects.add(fixedObj);
            }
        }

        // Convert back to JSON
        String finalJson = "[" + String.join(",", updatedObjects) + "]";

        writeFile(finalJson);

        System.out.println("Task deleted successfully!");
    }


}