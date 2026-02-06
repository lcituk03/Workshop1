package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    public static void main(String[] args) {
        String[][] tasks = tasksArray();
        printArray(tasks);
        String selectedOption = selectedOption();
        while (!selectedOption.equals("exit")){
            switch (selectedOption) {
                case "add":
                    tasks = addTask(tasks);
                    selectedOption=selectedOption();
                    break;
                case "remove":
                    tasks = removeTask(tasks);
                    selectedOption=selectedOption();
                    break;
                case "list":
                    printArray(tasks);
                    selectedOption=selectedOption();
                    break;
                default:
                    System.out.println("Please select a correct option.");
                    selectedOption=selectedOption();
            }
        }
        exit(tasks);
    }

    public static String selectedOption() {
        System.out.println("Please select an option:\nadd\nremove\nlist\nexit");
        Scanner sc = new Scanner(System.in);
        String selectedOption = sc.next();
        return selectedOption;
    }

    public static String[][] tasksArray() {
        Path tasksPath = Paths.get("src/main/resources/tasks.csv");
        try {
            List<String> lines = Files.readAllLines(tasksPath);
            String[][] tasks = new String[lines.size()][];
            for (int i = 0; i < lines.size(); i++) {
                tasks[i] = lines.get(i).split(",");
            }
            return tasks;
        } catch (IOException e) {
            System.out.println("Error with file!");
        }
        return new String[0][0];
    }

    public static void printArray(String[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println();
            System.out.print(i+ ": ");
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
        }
        System.out.println();
    }

    public static String[][] addTask(String[][] tasks) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please add task description");
        String taskDescription = sc.nextLine();
        System.out.println("Please add due date");
        String dueDate = sc.nextLine();
        System.out.println("Is your task important?: true/false");
        String importance = sc.nextLine();

        String[][] newTasks = new String[tasks.length + 1][3];
        for (int i = 0; i < tasks.length; i++) {
            //for (int j = 0; j<3; j++){
            newTasks[i] = tasks[i];
        }
        newTasks[tasks.length][0] = taskDescription;
        newTasks[tasks.length][1] = dueDate;
        newTasks[tasks.length][2] = importance;
        tasks = newTasks;
        return tasks;
    }

    public static String[][] removeTask(String[][] tasks) {
        System.out.println("Please select number to remove:");
        printArray(tasks);
        Scanner sc = new Scanner(System.in);
        int numberToRemove = sc.nextInt();
        if (numberToRemove < 0 || numberToRemove >= tasks.length) {
            System.out.println("Please provide a number within expected range.");
            numberToRemove = sc.nextInt();
        }
        String[][] newTasks = ArrayUtils.remove(tasks, numberToRemove);
        tasks = newTasks;
        return tasks;
    }

    public static void exit(String[][] tasks) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.length; i++) {
            sb.append(String.join(",", tasks[i])).append("\n");
        }

        Path tasksPath = Paths.get("src/main/resources/tasks.csv");
        try {
            Files.writeString(tasksPath, sb);
        } catch (IOException ex) {
            System.out.println("Changes cannot be saved.");
        }
        System.out.println("bye bye");

    }


}
