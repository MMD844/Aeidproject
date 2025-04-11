package todo.entity;

import db.Entity;

public class Step extends Entity {

    public enum Status {
    NotStarted, Completed
    }

    private String title;
    private Status status;
    private int taskRef;
    public static final int Step_Entity_Code = 20;

    public Step (int taskRef, String title){
        this.taskRef = taskRef;
        this.title = title;
        this.status = Status.NotStarted;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public Status getStatus(){
        return status;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public int getTaskRef(){
        return taskRef;
    }

    public void setTaskRef(int taskRef){
        this.taskRef = taskRef;
    }

    @Override
    public Step copy() {
        Step copyStep = new Step(taskRef, title);
        copyStep.taskRef = taskRef;
        copyStep.title = title;
        copyStep.status = status;
        copyStep.id = id;
        return copyStep;
    }

    @Override
    public int getEntityCode() {
        return Step_Entity_Code;
    }
}
