package example;

import db.Entity;
import db.Validator;
import db.exeption.InvalidEntityException;

public class HumanValidator implements Validator {

    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Human)){
            throw new IllegalArgumentException("Entity must be type of human");
        }
        Human human = (Human) entity;
        if (human.name == null || human.name.trim().isEmpty()){
            throw new InvalidEntityException("Name cant be null or empty");
        }
        if (human.age <= 0){
            throw new InvalidEntityException("Age cant be zero or negative");
        }
    }
}
