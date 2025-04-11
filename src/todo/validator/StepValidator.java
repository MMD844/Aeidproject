package todo.validator;

import db.Database;
import db.Entity;
import db.Validator;
import db.exeption.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

public class StepValidator implements Validator {

    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Step)){
            throw new IllegalArgumentException("Entity should be type of Step");
        }
        Step step = (Step) entity;
        if (step.getTitle() == null || step.getTitle().trim().isEmpty()){
            throw new InvalidEntityException("Step title cant be empty or null");
        }
        if (Database.get(step.getTaskRef()) != null) {
            throw new InvalidEntityException("Cant find the task with ID :" + step.getTaskRef());
        }
        if (!(Database.get(step.getTaskRef()) instanceof Task)){
            throw new  IllegalArgumentException("The ID is not for this type");
        }
    }
}
