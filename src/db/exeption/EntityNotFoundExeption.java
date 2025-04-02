package db.exeption;

public class EntityNotFoundExeption extends RuntimeException {
    public EntityNotFoundExeption() {
        super("Cant find Entity!");
    }
    public EntityNotFoundExeption (String massage){
        super(massage);
    }
    public EntityNotFoundExeption (int id){
        super("Cant find Entity with id = " + id);
    }
}
