package com.gfa.todo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static final String filePath = "pathToFile";

    public static void main(String[] args) throws IOException {

        if (args.length == 0) {
            System.out.println();
            System.out.println("Command Line Todo application");
            System.out.println("=============================");
            System.out.println();
            System.out.println("Command line arguments:");
            System.out.println();
            System.out.println("-l   Lists all the tasks");
            System.out.println("-a   Adds a new task");
            System.out.println("-r   Removes a task");
            System.out.println("-c   Completes a task");
            System.out.println();
        } else {

            //PRINT OUT LIST OF TASKS
            if (args[0].equals("-l")) {
                List<Task> myTaskList = createListFromFile(filePath);
                if (myTaskList.size() == 0) {
                    System.out.println("No todos for today! :)");
                } else {
                    for (int i = 0; i < myTaskList.size(); i++) {
                        System.out.println(i + 1 + ". " + myTaskList.get(i).description);
                    }
                }
            }

            //ADDING TASK
            if (args[0].equals("-a")) {
                if (args.length == 1) {
                    System.out.println("Unable to add: no task provided.");
                } else {

                    List<Task> myTaskList = createListFromFile(filePath);
                    Task addedTask = new Task(args[1]);
                    myTaskList.add(addedTask);

                    writeTaskListToFile(filePath, myTaskList);
                }

            }

            //REMOVING TASKS
            if (args[0].equals("-r")) {
                List<Task> myTaskList = createListFromFile(filePath);
                if (args.length == 1) {
                    System.out.println("Unable to remove: no index provided");
                } else {
                    int indexToRemove = 0;
                    try {
                        indexToRemove = Integer.parseInt(args[1]);
                        if ((indexToRemove > myTaskList.size()) || (indexToRemove <= 0)) {

                            System.out.println("Unable to remove: index is out of bound");

                        } else {

                            myTaskList.remove(indexToRemove - 1);
                        }

                    } catch (Exception e) {
                        System.out.println("Unable to remove: index is not a number");
                    }



                }

                writeTaskListToFile(filePath, myTaskList);
            }
        }


    }


//    public static void printListOfTasks() throws IOException {
//
//        String pathToTaskFile = "tasks2.txt";
//        Path pathToFile = Path.of(pathToTaskFile);
//        List<String> lines = Files.readAllLines(pathToFile);
//        System.out.println();
//        if (lines.size() == 0) {
//            System.out.println("No todos for today! :)");
//        } else {
//            for (int i = 0; i < lines.size(); i++) {
//                System.out.println(i + 1 + ". " + lines.get(i));
//            }
//        }
//    }

    private static List<Task> createListFromFile(String pathToFile) throws IOException {

        Path path = Path.of(pathToFile);
        List<String> lines = Files.readAllLines(path);
        List<Task> toDoList = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            Task task = new Task(lines.get(i));
            toDoList.add(task);
        }
        return toDoList;
    }

    private static void addTaskToFile(String pathToFile, Task task) throws IOException {

        Path path = Path.of(pathToFile);
        Files.writeString(path, "\n" + task.description, StandardOpenOption.APPEND);

    }

    private static void writeTaskListToFile(String pathToFile, List<Task> task) throws IOException {

        Path path = Path.of(pathToFile);
        List<String> listOfDescriptions = new ArrayList<>();
        for (Task task1 : task) {
            listOfDescriptions.add(task1.description);
        }

        Files.write(path, listOfDescriptions);

    }

}
