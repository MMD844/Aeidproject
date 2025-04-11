import db.Database;
import todo.service.StepService;
import db.exeption.EntityNotFoundExeption;
import db.exeption.InvalidEntityException;
import java.util.Scanner;

import static todo.service.StepService.addStep;
import static todo.service.StepService.updateStep;
import static todo.service.TaskService.*;


public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to To-Do List Manager!");
        System.out.println("Available commands: add, delete, update, get, exit");

        while (true) {
            System.out.print("\nEnter command: ");
            String command = scanner.nextLine().trim().toLowerCase();

            try {
                switch (command) {
                    case "add":
                        handleAddCommand();
                        break;
                    case "delete":
                        handleDeleteCommand();
                        break;
                    case "update":
                        handleUpdateCommand();
                        break;
                    case "get":
                        handleGetCommand();
                        break;
                    case "exit":
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid command. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void handleAddCommand() throws InvalidEntityException, EntityNotFoundExeption {
        System.out.print("Add what? (task/step): ");
        String type = scanner.nextLine().trim().toLowerCase();

        if (type.equals("task")) {
            addTask();
        } else if (type.equals("step")) {
            addStep();
        } else {
            System.out.println("Invalid type ; type Must be 'task' or 'step'");
        }
    }

    private static void handleDeleteCommand() throws EntityNotFoundExeption {
        System.out.print("Enter ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        try {
            StepService.deleteStepsForTask(id);
            Database.delete(id);
            System.out.println("Task and its steps deleted successfully.");
        } catch (EntityNotFoundExeption e) {
            Database.delete(id);
            System.out.println("Entity with ID=" + id + " deleted successfully.");
        }
    }

    private static void handleUpdateCommand() throws EntityNotFoundExeption, InvalidEntityException {
        System.out.print("Update what? (task/step): ");
        String type = scanner.nextLine().trim().toLowerCase();
        if (type.equals("task")) {
            updateTask();
        } else if (type.equals("step")) {
            updateStep();
        } else {
            System.out.println("Invalid type. Must be 'task' or 'step'");
        }
    }

    private static void handleGetCommand() throws EntityNotFoundExeption {
        System.out.print("Get what? (task-by-id/all-tasks/incomplete-tasks): ");
        String type = scanner.nextLine().trim().toLowerCase();

        switch (type) {
            case "task-by-id":
                getTaskById();
                break;
            case "all-tasks":
                getAllTasks();
                break;
            case "incomplete-tasks":
                getIncompleteTasks();
                break;
            default:
                System.out.println("Invalid type");
        }
    }
}