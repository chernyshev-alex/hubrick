package hubrick.entity;

import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;

public class Department {

    //public final Integer   id;
    public final String    name;

    public Department(String name) {
        //this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringJoiner(":", "[", "]").add(getClass().getTypeName())
                //.add(id.toString())
                .add(name).toString();
    }
   
    public static final Function<List<String>, Department> getFactory() {
        return ls -> new Department(ls.get(0).trim().toLowerCase());
    }
    
}
