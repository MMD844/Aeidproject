package todo.service;

import db.Database;
import db.Entity;
import db.exeption.EntityNotFoundExeption;
import db.exeption.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

import java.text.SimpleDateFormat;
import java.util.*;

import static todo.service.StepService.getStepsForTask;

public class TaskService {

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static final Scanner scanner = new Scanner(System.in);

    public static int createTask(String title, String discreption, Date dueDate) throws InvalidEntityException {
        Task task = new Task(title, discreption, dueDate);
        task.setStatus(Task.Status.Notstarted);
        task.setCreationDate(new Date());
        task.setLastModificationDate(new Date());
        Database.add(task);
        return task.id;
    }

    public static void addTask() throws InvalidEntityException {
        System.out.print("Title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Due date (yyyy-MM-dd): ");
        String dueDateStr = scanner.nextLine().trim();

        try {
            Date dueDate = dateFormat.parse(dueDateStr);
            TaskService.createTask(title, description, dueDate);
            System.out.println("Task saved successfully.");
        } catch (Exception e) {
            throw new InvalidEntityException("Invalid date format or data: " + e.getMessage());
        }
    }

    public static void updateTaskStatus(int taskId, Task.Status status) throws InvalidEntityException {
        Task task = (Task) Database.get(taskId);
        task.setStatus(status);
        Database.update(task);
        if (status == Task.Status.Copmpleted) {
            List<Step> steps = getStepsForTask(taskId);
            for (Step step : steps) {
                if (step.getStatus() != Step.Status.Completed) {
                    step.setStatus(Step.Status.Completed);
                    Database.update(step);
                }
            }
        }
    }

    public static void updateTask() throws EntityNotFoundExeption, InvalidEntityException {
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Field (title/description/dueDate/status): ");
        String field = scanner.nextLine().trim().toLowerCase();

        System.out.print("New Value: ");
        String value = scanner.nextLine().trim();

        Task task = (Task) Database.get(id);
        Task oldTask = task.copy();

        switch (field) {
            case "title":
                task.setTitle(value);
                break;
            case "description":
                task.setDescription(value);
                break;
            case "duedate":
                try {
                    task.dueDate = dateFormat.parse(value);
                } catch (Exception e) {
                    throw new InvalidEntityException("Invalid date format");
                }
                break;
            case "status":
                updateTaskStatus(task.id, task.getStatus());
                break;
            default:
                throw new InvalidEntityException("Invalid field name");
        }
        Database.update(task);
        System.out.println("Successfully updated the task.");
        System.out.println("Field: " + field);
        System.out.println("Old Value: " + getFieldValue(oldTask, field));
        System.out.println("New Value: " + value);
        System.out.println("Modification Date: " + task.getLastModificationDate());
        System.out.println("LastModificationDate: " + task.getLastModificationDate());
    }

    private static String getFieldValue(Task task, String field) {
        switch (field) {
            case "title":
                return task.getTitle();
            case "description":
                return task.getDescription();
            case "duedate":
                return dateFormat.format(task.dueDate);
            case "status":
                return task.getStatus().toString();
            default:
                return "";
        }
    }

    public static void printTaskDetails(Task task) {
        System.out.println("ID: " + task.id);
        System.out.println("Title: " + task.getTitle());
        System.out.println("Due Date: " + dateFormat.format(task.dueDate));
        System.out.println("Status: " + task.getStatus());

        List<Step> steps = StepService.getStepsForTask(task.id);
        if (!steps.isEmpty()) {
            System.out.println("Steps:");
            for (Step step : steps) {
                System.out.println("    + " + step.getTitle() + ":");
                System.out.println("        ID: " + step.id);
                System.out.println("        Status: " + step.getStatus());
            }
        }
    }

    public static void getIncompleteTasks() {
        List<Task> tasks = TaskService.getAllTasks();
        for (Task task : tasks) {
            if (task.getStatus() != Task.Status.Copmpleted) {
                printTaskDetails(task);
                System.out.println();
            }
        }
    }

    public static void getTaskById() throws EntityNotFoundExeption {
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        Task task = (Task) Database.get(id);
        printTaskDetails(task);
    }

    public static List<Task> getAllTasks () {
        List<Task> tasks = new ArrayList<>();
        for (Entity entity : Database.getAll(Task.Task_Entity_Code)) {
            tasks.add((Task) entity);
        }
        tasks.sort(Comparator.comparing(task -> task.dueDate));
        return tasks;
    }
}
