package todo.entity;

import db.Database;
import db.Entity;
import db.Trackable;

import java.util.Date;

public class Task extends Entity implements Trackable {

    public enum Status{
        Notstarted, Inprogress, Copmpleted
    }

    private String title;
    private String description;
    public Date dueDate;
    private Status status;
    private Date creationDate;
    private Date lastModificationDate;
    public static final int Task_Entity_Code = 12;


    public Task (String title, String description, Date dueDate){
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = Status.Notstarted;
        this.creationDate = new Date();
        this.lastModificationDate = new Date();
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
        this.lastModificationDate = new Date();
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
        this.lastModificationDate = new Date();
    }

    public Date getDueDate(){
        return dueDate;
    }

    public void setDueDate(Date dueDate){
        this.dueDate = dueDate;
        this.lastModificationDate = new Date();
    }

    public Status getStatus(){
        return status;
    }

    public void setId(int id){
        this.id = id;
        this.lastModificationDate = new Date();
    }

    public void setStatus(Status status){
        this.status = status;
        this.lastModificationDate = new Date();
    }

    @Override
    public Task copy() {
        Task copyTask = new Task(title, description, dueDate);
        copyTask.id = id;
        copyTask.status = status;
        lastModificationDate = new Date(lastModificationDate.getTime());
        creationDate = new Date(creationDate.getTime());
        return copyTask;
    }

    @Override
    public int getEntityCode() {
        return Task_Entity_Code;
    }

    @Override
    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setLastModificationDate(Date date) {
        this.lastModificationDate = date;
    }

    @Override
    public Date getLastModificationDate() {
        return lastModificationDate;
    }
}
