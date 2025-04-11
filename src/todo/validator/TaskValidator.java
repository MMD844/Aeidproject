package todo.validator;

import db.Entity;
import db.Validator;
import db.exeption.InvalidEntityException;
import todo.entity.Task;

public class TaskValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Task)){
            throw new InvalidEntityException("Entity should be type of Task");
        }
        Task task = (Task) entity;
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()){
            throw new InvalidEntityException("Task Title vant be empty or null");
        }
    }
}
