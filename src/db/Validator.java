package db;

import db.exeption.InvalidEntityException;

public interface Validator {
    void validate(Entity entity) throws InvalidEntityException;
}
