package lab5;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miguel on 10/03/2016.
 */
public class Database {
    public class Person{
        String name;
        String password;

        public Person(String name, String password){
            this.name = name;
            this.password = password;
        }
    }
    List<Person> personList;

    public Database(){
        personList = new ArrayList<>();
    }

    public synchronized String handleMessage(String message){
        return "";
    }
}
