import db.Database;
import db.exeption.EntityNotFoundExeption;
import example.Human;

public class Main {
    public static void main(String[] args) throws EntityNotFoundExeption {
                Human ali = new Human("Ali");
                Database.add(ali);

                ali.name = "Ali Hosseini";

                Human aliFromTheDatabase = (Human) Database.get(ali.id);

                System.out.println("ali's name in the database: " + aliFromTheDatabase.name);
            }
        }