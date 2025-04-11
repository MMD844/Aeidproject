package db;

import db.exeption.EntityNotFoundExeption;
import db.exeption.InvalidEntityException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Database {
    private static final ArrayList<Entity> entities = new ArrayList<>();

    private static int nextId = 1;

    private static HashMap<Integer, Validator> validators = new HashMap<>();

    public static void add (Entity e) throws InvalidEntityException {
        Validator validator = validators.get(e.getEntityCode());
        if (validator != null) {
            validator.validate(e);
        }
        if (e instanceof Trackable) {
            Trackable trackable = (Trackable) e;
            Date now = new Date();
            trackable.setCreationDate(now);
            trackable.setLastModificationDate(now);
        }
        e.id = nextId ++;
        entities.add(e.copy());
        }

    public static Entity get (int id) throws EntityNotFoundExeption {
        for (Entity entity : entities) {
            if (entity.id == id){
                return entity.copy();
            }
        }
        throw new EntityNotFoundExeption(id);
    }

    public static void delete (int id) {
        Entity entity = get(id);
        entities.remove(entity);
    }

    public static void update (Entity e) throws EntityNotFoundExeption, InvalidEntityException {
        Validator validator = validators.get(e.getEntityCode());
        if (validator != null) {
            validator.validate(e);
        }
        if (e instanceof Trackable) {
            Trackable trackable = (Trackable) e;
            trackable.setLastModificationDate(new Date());
        }
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).id == e.id) {
                entities.set(i, e.copy());
                return;
            }
        }
        throw new EntityNotFoundExeption(e.id);
    }

    public static void registerValidator (int entitycode, Validator validator) {
        if (validators.containsKey(entitycode)) {
            throw new IllegalArgumentException("Validator for this entity code is exists");
        }
        validators.put(entitycode, validator);
    }

    public static List<Entity> getAll (int entitycode) {
        List<Entity> result = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.getEntityCode() == entitycode){
                result.add(entity.copy());
            }
        }
        return result;
    }

}
