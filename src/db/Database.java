package db;

import db.exeption.EntityNotFoundExeption;

import java.util.ArrayList;

public class Database {
    private static final ArrayList<Entity> entities = new ArrayList<>();
    private static int nextId = 1;

    private Database (){}

    public static void add (Entity e){
        e.id = nextId ++;
        entities.add(e);
    }

    public static Entity get (int id) throws EntityNotFoundExeption {
        for (Entity entity : entities) {
            if (entity.id == id){
                return entity;
            }
        }
        throw new EntityNotFoundExeption(id);
    }

    public static void delete (int id) {
        Entity entity = get(id);
        entities.remove(entity);
    }

    public static void update (Entity e) throws EntityNotFoundExeption {
        Entity existing = get(e.id);
        int index = entities.indexOf(existing);
        entities.set(index, e);
    }
}
