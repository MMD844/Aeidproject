package todo.service;

import db.Database;
import db.Entity;
import db.exeption.EntityNotFoundExeption;
import db.exeption.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;
import todo.validator.StepValidator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class StepService {

    private static final Scanner scanner = new Scanner(System.in);

    static {
        Database.registerValidator(Step.Step_Entity_Code, new StepValidator());
    }

    public static void saveStep (int taskRef, String title) throws InvalidEntityException , EntityNotFoundExeption {
        Step step = new Step(taskRef, title);
        step.setStatus(Step.Status.NotStarted);
        Task task = (Task) Database.get(taskRef);
        if (task.getStatus() == Task.Status.Notstarted) {
            task.setStatus(Task.Status.Inprogress);
            Database.update(task);
        }
        try {
            Database.add(step);
        } catch (Exception e) {
            throw new RuntimeException("Cannot save step: " + e.getMessage());
        }
    }

    public static void updateStep() throws EntityNotFoundExeption, InvalidEntityException {
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Field (title/status): ");
        String field = scanner.nextLine().trim().toLowerCase();

        System.out.print("New Value: ");
        String value = scanner.nextLine().trim();

        Step step = (Step) Database.get(id);
        Step oldStep = step.copy();

        switch (field) {
            case "title":
                step.setTitle(value);
                break;
            case "status":
                updateStepStatus(step.id, step.getStatus());
                break;
            default:
                throw new InvalidEntityException("Invalid field name");
        }

        Database.update(step);
        System.out.println("Successfully updated the step.");
        System.out.println("Field: " + field);
        System.out.println("Old Value: " + getFieldValue(oldStep, field));
        System.out.println("New Value: " + value);
        System.out.println("Modification Date: " + new Date());
        System.out.println("LastModification Date: " + new Date());
    }

    public static void updateStepStatus (int stepId, Step.Status status) throws EntityNotFoundExeption, InvalidEntityException{
        Step step = (Step) Database.get(stepId);
        step.setStatus(status);
        Database.update(step);
        updateParentTaskStatus(step.getTaskRef());
    }

    private static void updateParentTaskStatus (int taskId) throws EntityNotFoundExeption, InvalidEntityException {
        Task task = (Task) Database.get(taskId);
        List<Step> steps = getStepsForTask(taskId);
        boolean allCompleted = true;
        boolean anyInProgress = false;
        for (Step step : steps) {
            if (step.getStatus() != Step.Status.Completed) {
                allCompleted = false;
            }
            else {
                anyInProgress = true;
            }
        }
        if (allCompleted) {
            task.setStatus(Task.Status.Copmpleted);
        }
        else if (anyInProgress) {
            task.setStatus(Task.Status.Inprogress);
        }
        else {
            task.setStatus(Task.Status.Notstarted);
        }
        Database.update(task);
    }

    public static List<Step> getStepsForTask (int taskId) {
        List<Step> steps = new ArrayList<>();
        for (Entity entity : Database.getAll(Step.Step_Entity_Code)){
            Step step = (Step) entity;
            if (step.getTaskRef() == taskId) {
                steps.add(step);
            }
        }
        return steps;
    }

    private static String getFieldValue(Step step, String field) {
        switch (field) {
            case "title":
                return step.getTitle();
            case "status":
                return step.getStatus().toString();
            default:
                return "";
        }
    }

    public static void addStep() throws EntityNotFoundExeption, InvalidEntityException {
        System.out.print("TaskID: ");
        int taskId = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Title: ");
        String title = scanner.nextLine().trim();

        StepService.saveStep(taskId, title);
        System.out.println("Step saved successfully.");
    }

    public static void deleteStepsForTask (int taskId) throws EntityNotFoundExeption {
        List<Step> steps = new ArrayList<>();
        for (Step step : steps) {
            Database.delete(step.id);
        }
    }

}
